package jp.whisper.common.tool.scaffold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.whisper.common.util.DevLog;
import jp.whisper.common.util.StringUtil;

public class ScaffoldBuilder {
	protected final Log logger = LogFactory.getLog(getClass());

	protected final static String PKG_PREFIX = "jp.whisper.";
	protected final static String PKG_SUFFIX_MODEL = ".model.";
	protected final static String PKG_SUFFIX_DAO = ".dao.";
	protected final static String PKG_SUFFIX_MANAGER = ".service.";
	protected final static String PKG_IMPL = "impl";

	protected String pkgName;
	protected String clzName;
	protected TableInfo tableInfo;
	private final Map<String, String> mapping;
	private GeneratorType[] generatorType;

	public enum GeneratorType {
		MODEL, SQLMAP, CONTROLLER, SERVICE, TEST, VALIDATOR, NONE, ALL
	}

	public ScaffoldBuilder(String pkgName, String clzName, TableInfo tableInfo, GeneratorType... generatorType) {
		this.pkgName = PKG_PREFIX + pkgName;
		this.clzName = clzName;
		this.tableInfo = tableInfo;
		this.generatorType = generatorType;

		mapping = new HashMap<String, String>();

		mapping.put("clzName", clzName);
		mapping.put("clzNameToLowerCase", clzName.toLowerCase());
		mapping.put("clzNameLC", StringUtils.uncapitalize(clzName));
		mapping.put("tblName", tableInfo.getName());
		mapping.put("modelPath", getModelPath());
		mapping.put("modelQueryPath", getModelQueryPath());
		mapping.put("daoPath", getDaoPath());
		mapping.put("daoImplPath", getDaoImplPath());
		mapping.put("managerPath", getManagerPath());
		mapping.put("managerImplPath", getManagerImplPath());
		// for Model java
		mapping.put("fieldsDeclareInfo", tableInfo.getFieldsDeclareInfo());
		// for model sqlmapping
		mapping.put("resultMap", tableInfo.getResultMap());
		mapping.put("otherCondition", tableInfo.getOtherCondition());

		DevLog.debug(tableInfo.getPrimaryKey());
		mapping.put("primaryKey", tableInfo.getPrimaryKey());
		DevLog.debug(tableInfo.getFindByLike());
		mapping.put("findLikeBy", tableInfo.getFindByLike());
		DevLog.debug(tableInfo.getSelectStatement());
		mapping.put("selectStatement", tableInfo.getSelectStatement());
		DevLog.debug(tableInfo.getInsertStatement());
		mapping.put("insertStatement", tableInfo.getInsertStatement());
		DevLog.debug(tableInfo.getUpdateStatement());
		mapping.put("updateStatement", tableInfo.getUpdateStatement());
		// ダイナミック項目更新
		mapping.put("updateMapModel", tableInfo.getUpdateMapModel());
		// トータルレコード数
		mapping.put("countSelectStatement", tableInfo.getSelectCountStatement());
	}

	public String getModelPath() {
		return pkgName + PKG_SUFFIX_MODEL + clzName;
	}

	public String getModelQueryPath() {
		return pkgName + PKG_SUFFIX_MODEL + clzName + "Query";
	}

	public String getSqlMapFile() {
		return pkgName + PKG_SUFFIX_MODEL + clzName + ".xml";
	}

	public String getDaoPath() {
		return pkgName + PKG_SUFFIX_DAO + clzName + "DAO";
	}

	public String getDaoImplPath() {
		return pkgName + PKG_SUFFIX_DAO + PKG_IMPL + StringUtil.DOT + clzName + "DAO"
				+ StringUtils.capitalize(PKG_IMPL);
	}

	public String getManagerPath() {
		return pkgName + PKG_SUFFIX_MANAGER + clzName + "Service";
	}

	public String getManagerImplPath() {
		return pkgName + PKG_SUFFIX_MANAGER + PKG_IMPL + StringUtil.DOT + clzName + "Service"
				+ StringUtils.capitalize(PKG_IMPL);
	}

	public List<FileGenerator> buildGenerators() {
		List<FileGenerator> list = new ArrayList<FileGenerator>();
		switch (this.generatorType[0]) {
		case MODEL:
			// model
			list.add(new FileGenerator(pkgName + ".model", clzName, "Model.txt", mapping));
			list.add(new FileGenerator(pkgName + ".model", clzName + "Query", "QueryModel.txt", mapping));

			list.add(new FileGenerator(pkgName + ".model", clzName + "Mapper", "SqlMap.txt", mapping, "xml"));
			list.add(new FileGenerator(pkgName + ".model.validator", clzName + "Validator", "Validator.txt", mapping));
			break;
		case SQLMAP:
			list.add(new FileGenerator(pkgName + ".model", clzName + "Mapper", "SqlMap.txt", mapping, "xml"));
			break;
		case CONTROLLER:
			// controller
			list.add(new FileGenerator(pkgName + ".web", clzName + "Controller", "Controller.txt", mapping));
			break;
		case SERVICE:
			// manager
			list.add(new FileGenerator(pkgName + ".service", clzName + "Service", "Service.txt", mapping));
			list.add(new FileGenerator(pkgName + ".service.impl", clzName + "ServiceImpl", "ServiceImpl.txt", mapping));
			list.add(new FileGenerator(pkgName + ".service", clzName + "ServiceTest", "ServiceTest.txt", mapping));
			break;
		case TEST:
			list.add(new FileGenerator(pkgName + ".service", clzName + "ServiceTest", "ServiceTest.txt", mapping));
			break;
		case VALIDATOR:
			list.add(new FileGenerator(pkgName + ".model.validator", clzName + "Validator", "Validator.txt", mapping));
			break;
		case NONE:
		case ALL:
			// model
			list.add(new FileGenerator(pkgName + ".model", clzName, "Model.txt", mapping));
			list.add(new FileGenerator(pkgName + ".model", clzName + "Query", "QueryModel.txt", mapping));

			list.add(new FileGenerator(pkgName + ".model", clzName + "Mapper", "SqlMap.txt", mapping, "xml"));
			list.add(new FileGenerator(pkgName + ".model.validator", clzName + "Validator", "Validator.txt", mapping));

			// dao
			list.add(new FileGenerator(pkgName + ".dao", clzName + "DAO", "DAO.txt", mapping));
			list.add(new FileGenerator(pkgName + ".dao.impl", clzName + "DAOImpl", "DAOImpl.txt", mapping));

			// manager
			list.add(new FileGenerator(pkgName + ".service", clzName + "Service", "Service.txt", mapping));
			list.add(new FileGenerator(pkgName + ".service.impl", clzName + "ServiceImpl", "ServiceImpl.txt", mapping));
			list.add(new FileGenerator(pkgName + ".service", clzName + "ServiceTest", "ServiceTest.txt", mapping));
			// controller
			list.add(new FileGenerator(pkgName + ".web", clzName + "Controller", "Controller.txt", mapping));
		default:
			DevLog.error("generatorTypeを指定してください");
			return list;
		}

		return list;
	}

}
