package jp.whisper.common.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import jp.whisper.common.dao.BaseQuery;
import jp.whisper.common.model.BaseModel;
import jp.whisper.common.util.CollectionUtil;
import jp.whisper.common.util.DevLog;

abstract class IBatisGenericDAO extends SqlSessionDaoSupport {

	private static final int METADATA_PK_INDEX = 4;
	public static final String POSTFIX_INSERT = ".insert";
	public static final String POSTFIX_UPDATE = ".update";
	public static final String POSTFIX_DELETE = ".delete";
	public static final String POSTFIX_DELETE_PRIAMARYKEY = ".deleteByPrimaryKey";
	public static final String POSTFIX_SELECT = ".select";
	public static final String POSTFIX_SELECTMAP = ".selectByMap";
	public static final String POSTFIX_COUNT = ".count";
	public static final String POSTFIX_QUERY = ".query";
	/**
	 * プライマリキーを持つのをインサートする
	 */
	public static final String POSTFIX_INSERT_WITHKEY = ".insertWithObjectKey";
	/**
	 * 論理的にデータを削除する
	 */
	public static final String POSTFIX_LOGIC_DELETE = ".logicDelete";
	/**
	 * プライマリキーをもとに、 論理的にデータを削除する
	 */
	public static final String POSTFIX_LOGIC_DELETE_PRIAMARYKEY = ".logicDeleteByPrimaryKey";
	/**
	 * 部分的にデータを更新する
	 */
	public static final String POSTFIX_UPDATE_PARTIAL_MODEL = ".updateMapModel";

	abstract String getSqlMapNamespace();

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) getSqlSession().selectOne(getSqlMapNamespace() + POSTFIX_SELECT, id);
	}

	public <T> List<T> getAll(Class<T> entityClass) {
		return getSqlSession().selectList(getSqlMapNamespace() + POSTFIX_SELECT, null);
	}

	public void insert(Object o) {

		getSqlSession().insert(getSqlMapNamespace() + POSTFIX_INSERT, o);
	}

	public void insertWithPrimaryKey(Object o) {

		getSqlSession().insert(getSqlMapNamespace() + POSTFIX_INSERT_WITHKEY, o);
	}

	public void update(Object o) {
		this.update(o, Boolean.FALSE);
	}

	public void update(Object o, boolean isupdateMC) {
		getSqlSession().update(getSqlMapNamespace() + POSTFIX_UPDATE, o);
	}

	public void updatePartialModel(Object o) {
		getSqlSession().update(getSqlMapNamespace() + POSTFIX_UPDATE_PARTIAL_MODEL, CollectionUtil.model2HashMap(o));
	}

	public void updatePartialModel(Object o, String[] aryNullFields) {
		getSqlSession().update(getSqlMapNamespace() + POSTFIX_UPDATE_PARTIAL_MODEL,
				CollectionUtil.model2HashMap(o, aryNullFields));
	}

	public void remove(Object o) {
		// getSqlMapClientTemplate().delete(getSqlMapNamespace() +
		// POSTFIX_DELETE, o);
		// logic delete
		getSqlSession().update(getSqlMapNamespace() + POSTFIX_LOGIC_DELETE, o);

	}

	public <T> void removeById(Class<T> entityClass, Serializable id) {
		// getSqlMapClientTemplate().delete(getSqlMapNamespace() +
		// POSTFIX_DELETE_PRIAMARYKEY, id);
		// logic delete
		if (id == null) {
			throw new RuntimeException("id must not be null");
		}
		T obj = null;
		try {
			obj = entityClass.newInstance();
			((BaseModel) obj).setId((Long) id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// getSqlMapClientTemplate().update(getSqlMapNamespace()+POSTFIX_LOGIC_DELETE_PRIAMARYKEY,
		// id);
		getSqlSession().update(getSqlMapNamespace() + POSTFIX_LOGIC_DELETE_PRIAMARYKEY, id);
	}

	public <T> List<T> find(Class<T> entityClass, Map<String, Object> map) {
		if (map == null)
			return getAll(entityClass);
		DevLog.trace(map);
		map.put("findBy", "True");
		return this.getSqlSession().selectList(getSqlMapNamespace() + POSTFIX_SELECTMAP, map);
	}

	public <T> List<T> findByLike(Class<T> entityClass, Map<String, Object> map) {
		if (map == null)
			return getAll(entityClass);
		DevLog.trace(map);
		map.put("findLikeBy", "True");
		return this.getSqlSession().selectList(getSqlMapNamespace() + POSTFIX_SELECTMAP, map);
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueBy(Class<T> entityClass, Map<String, Object> map) {
		try {
			map.put("findBy", "True");
			DevLog.trace(map);
			return (T) getSqlSession().selectOne(getSqlMapNamespace() + POSTFIX_SELECTMAP, map);
		} catch (Exception e) {
			logger.error("Error when propertie on entity," + e.getMessage(), e.getCause());
			throw new RuntimeException(e);
		}
	}

	public boolean isNotUnique(Object entity, String TABLE_NAME, String names) {
		try {
			String primarykey;
			Connection con = getSqlSession().getConnection();
			ResultSet dbMetaData = con.getMetaData().getPrimaryKeys(con.getCatalog(), null, TABLE_NAME);
			dbMetaData.next();
			if (dbMetaData.getRow() > 0) {
				primarykey = dbMetaData.getString(METADATA_PK_INDEX);
				if (names.indexOf(primarykey) > -1)
					return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return false;
	}

	public <T> List<T> query(Class<T> entityClass, BaseQuery query) {
		List<T> list = new ArrayList<T>();
		Integer num = null;

		num = (Integer) getSqlSession().selectOne(getSqlMapNamespace() + POSTFIX_COUNT, query);

		if (num != null && num.intValue() != 0) {
			query.setTotal(num);
			list = getSqlSession().selectList(getSqlMapNamespace() + POSTFIX_QUERY, query);
		}
		return list;
	}

	public <T> Map<String, T> findMapBy(Class<T> entityClass, String key, Map<String, Object> map) {
		DevLog.trace(map);
		map.put("findBy", "True");
		return this.getSqlSession().selectMap(getSqlMapNamespace() + POSTFIX_SELECTMAP, map, key);
	}

	public <T> Map<String, T> findMapByLike(Class<T> entityClass, String key, Map<String, Object> map) {
		DevLog.trace(map);
		map.put("findLikeBy", "True");
		return this.getSqlSession().selectMap(getSqlMapNamespace() + POSTFIX_SELECTMAP, map, key);
	}

	// /**
	// * get the db connection
	// */
	// public Connection getCurrentConnection() {
	// SqlMapSession session = getSqlSession().getSqlMapClient().openSession();
	// Connection ibatisCon = null;
	// try {
	// DataSource dataSource = getDataSource();
	// boolean transactionAware = (dataSource instanceof
	// TransactionAwareDataSourceProxy);
	// ibatisCon = session.getCurrentConnection();
	// if (ibatisCon == null) {
	// ibatisCon = (transactionAware ? dataSource.getConnection()
	// : DataSourceUtils.doGetConnection(dataSource));
	// session.setUserConnection(ibatisCon);
	// if (logger.isDebugEnabled()) {
	// logger.debug("Obtained JDBC Connection [" + ibatisCon + "] for iBATIS
	// operation");
	// }
	// } else {
	// if (logger.isDebugEnabled()) {
	// logger.debug("Reusing JDBC Connection [" + ibatisCon + "] for iBATIS
	// operation");
	// }
	// }
	// } catch (Exception e) {
	// logger.debug("get current Connection failure", e);
	// throw new RuntimeException(e);
	// }
	// return ibatisCon;
	// }

}
