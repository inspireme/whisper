package jp.whisper.common.tool.scaffold;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.whisper.common.tool.scaffold.ScaffoldBuilder.GeneratorType;
import jp.whisper.common.util.DevLog;
import jp.whisper.common.util.StringUtil;

public class ScaffoldGen {

	private static final String NULLABLE = "NULLABLE";
	private static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";
	private static final String COLUMN_SIZE = "COLUMN_SIZE";
	private static final String TYPE_NAME = "TYPE_NAME";
	private static final String SQLSERVER = "sqlserver";
	private static final String COLUMN_NAME = "COLUMN_NAME";
	private static final String JDBC_PASSWORD = "jdbc.password";
	private static final String JDBC_USER = "jdbc.user";
	private static final String JDBC_URL = "jdbc.url";
	private static final String JDBC_DRIVER = "jdbc.driver";
	private static final String JDBC_SCHEMA = "jdbc.schema";
	private static final String CONFIG_PROPERTIES = "src/main/java/whisper-config.properties";
	private final Log log = LogFactory.getLog(getClass());
	private Connection conn;
	private String schema;
	private DatabaseMetaData metaData;
	private final String pkgName;
	private final String clzName;
	private final String tblName;
	private Map<String, String> columnExplains;

	/**
	 * @param pkgName
	 * @param clzName
	 *
	 */
	public ScaffoldGen(String pkgName, String clzName) {
		this(pkgName, clzName, clzName + "s");
	}

	/**
	 * @param pkgName
	 * @param clzName
	 * @param tblName
	 */
	public ScaffoldGen(String pkgName, String clzName, String tblName) {
		this.pkgName = pkgName;
		this.clzName = StringUtils.capitalize(clzName);
		this.tblName = tblName.toUpperCase();
	}

	public void execute() throws Exception {
		execute(false, GeneratorType.NONE);
	}

	/**
	 *
	 * @param debug
	 *            デバッグモード
	 * @param isOverride
	 *            上書きするか
	 */
	public void execute(boolean debug, GeneratorType... generatorType) throws Exception {
		if (!debug && (generatorType == null || generatorType.length == 0)) {
			DevLog.debug("generatorTypeを指定してください");
			return;
		}

		// DB情報を初期
		if (!initConnection()) {
			DevLog.debug("ClassPathの配下にプロパティファイル「" + CONFIG_PROPERTIES + "」を検証してください。");
			return;
		}
		// テーブル情報を初期
		TableInfo tableInfo = parseDbTable(this.tblName);
		if (tableInfo == null) {
			return;
		}
		// ソースを生成
		ScaffoldBuilder sf = new ScaffoldBuilder(this.pkgName, this.clzName, tableInfo, generatorType);
		List<FileGenerator> list = sf.buildGenerators();
		for (FileGenerator gen : list) {
			gen.execute(debug);
		}
	}

	/**
	 * DBコネクションを初期
	 *
	 * @return
	 */
	private boolean initConnection() {
		Properties config = new Properties();
		String driver = null;
		String url = StringUtils.EMPTY;
		String user = StringUtils.EMPTY;
		String password = StringUtils.EMPTY;
		String schema = StringUtils.EMPTY;
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(CONFIG_PROPERTIES);
			config.load(inputStream);

			driver = config.getProperty(JDBC_DRIVER);
			url = config.getProperty(JDBC_URL);
			user = config.getProperty(JDBC_USER);
			password = config.getProperty(JDBC_PASSWORD);
			schema = config.getProperty(JDBC_SCHEMA);
			if (StringUtil.isNotBlank(schema)) {
				this.schema = schema;
			}
			if (driver.toLowerCase().contains(SQLSERVER)) {
				this.schema = "dbo";
			}
			if (StringUtil.isBlank(this.schema)) {
				this.schema = user.toUpperCase();
			}
			Class.forName(driver);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			log.fatal("Jdbc connection config file not found - " + CONFIG_PROPERTIES);
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.fatal("Jdbc driver not found - " + driver);
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		}

		try {
			conn = DriverManager.getConnection(url, user, password);
			if (conn == null) {
				log.fatal("Database connection is null");
				return false;
			}
			// メタデータを取得
			metaData = conn.getMetaData();
			if (metaData == null) {
				log.fatal("Database MetaData is null");
				return false;
			}

		} catch (SQLException e) {
			log.fatal("Database connect failed");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * テーブル情報を初期
	 *
	 * @param TABLE_NAME
	 * @return
	 * @throws Exception
	 */
	private TableInfo parseDbTable(String TABLE_NAME) throws Exception {
		TableInfo tableInfo = new TableInfo(TABLE_NAME);
		columnExplains = new HashMap<String, String>();
		ResultSet rs = null;
		log.trace("parseDbTable begin");
		try {
			// プライマリキーを取得
			rs = metaData.getPrimaryKeys(null, schema, TABLE_NAME);
			if (rs.next()) {
				tableInfo.setPrimaryKey(rs.getString(COLUMN_NAME));
			}
			if (rs.next()) {
				DevLog.debug("複合主キーなの為，対応されていない");
				return null;
			}
		} catch (SQLException e) {
			log.error("Table " + TABLE_NAME + " parse error.");
			e.printStackTrace();
			return null;
		}
		log.info("PrimaryKey : " + tableInfo.getPrimaryKey());

		try {
			rs = metaData.getColumns(conn.getCatalog(), schema, TABLE_NAME, null);
			if (!rs.next()) {
				log.fatal("Table " + schema + "." + TABLE_NAME + " not found.");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 列の注釈を取得
		try {
			String sql = " SELECT T.TABLE_NAME,T.COLUMN_NAME,T.COMMENTS FROM USER_COL_COMMENTS  T WHERE T.TABLE_NAME=upper('"
					+ TABLE_NAME + "')  ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs1 = ps.executeQuery();
			while (rs1.next()) {
				columnExplains.put(rs1.getString("COLUMN_NAME"), rs1.getString("COMMENTS"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			while (rs.next()) {

				String columnName = rs.getString(COLUMN_NAME);
				String columnType = rs.getString(TYPE_NAME);
				int datasize = rs.getInt(COLUMN_SIZE);
				int digits = rs.getInt(DECIMAL_DIGITS);
				int nullable = rs.getInt(NULLABLE);
				// DevLog.debug(rs.getString("REMARKS"));

				String columnExplain = columnExplains.get(columnName);
				ColumnInfo colInfo = new ColumnInfo(columnName, columnType, datasize, digits, nullable, columnExplain);

				log.info("DB column : " + colInfo);
				log.info("Java field : " + colInfo.parseFieldName() + " / " + colInfo.parseJavaType() + " / "
						+ colInfo.getColumnExplain());
				tableInfo.addColumn(colInfo);
				if (ColumnInfo.DEFAULT_COLUMNS.containsKey(columnName)) {
					FieldInfo field = ColumnInfo.DEFAULT_COLUMNS.get(columnName);
					if (!columnType.contains(field.getColumnType())) {
						throw new Exception("「" + columnName + "」列の型は「" + columnType + "」であり，基盤の要望「"
								+ ColumnInfo.DEFAULT_COLUMNS.get(columnName).getColumnType() + "」と一致しない");
					}
					ColumnInfo.DEFAULT_COLUMNS.remove(columnName);
				}
			}
			// ColumnInfo.DEFAULT_COLUMNS.remove("YEAR");
			// ColumnInfo.DEFAULT_COLUMNS.remove("PROVINCE_CODE");
			if (!ColumnInfo.DEFAULT_COLUMNS.isEmpty()) {
				String filed = "必須項目チェックエラー：";
				for (Map.Entry<String, FieldInfo> entry : ColumnInfo.DEFAULT_COLUMNS.entrySet()) {
					filed = filed + "[" + entry.getKey() + ":" + entry.getValue().getColumnType() + "] ";
				}
				throw new Exception(filed);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Table " + TABLE_NAME + " parse error.");
		}
		log.trace("parseDbTable end");
		return tableInfo;
	}

}
