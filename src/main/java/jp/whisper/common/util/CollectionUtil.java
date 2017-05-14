package jp.whisper.common.util;

import java.lang.reflect.Method;
import java.util.HashMap;

public class CollectionUtil {

	private CollectionUtil() {

	}

	public final static String SET_METHOD = "set";
	public final static String GET_METHOD = "get";

	/**
	 * modelをマップに変換する。空でしたら、対象外とする
	 *
	 * @param objModel
	 * @return hashMap
	 */
	public static HashMap<String, Object> model2HashMap(Object objModel) {
		return model2HashMap(objModel, null);
	}

	/**
	 * modelをマップに変換する。空でしたら、対象外とする
	 *
	 * @param objModel
	 * @param aryNullFields
	 * @return HashMap
	 */
	public static HashMap<String, Object> model2HashMap(Object objModel, String[] aryNullFields) {
		String methodName = "";
		Method method = null;
		HashMap<String, Object> hsResult = new HashMap<String, Object>(10);
		HashMap<String, Object> mapNullFields = null;

		if (aryNullFields != null) {
			mapNullFields = new HashMap<String, Object>(aryNullFields.length);
			for (int i = 0; i < aryNullFields.length; i++) {
				mapNullFields.put(aryNullFields[i], null);
			}
		}

		try {
			for (int i = 0; i < objModel.getClass().getMethods().length; i++) {
				method = objModel.getClass().getMethods()[i];
				methodName = method.getName();
				if (methodName.indexOf(GET_METHOD) == 0) {
					Object objResult = method.invoke(objModel, null);
					String strName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
					if (objResult != null) {
						if ("getClass".equals(methodName) == true)
							continue;

						hsResult.put(strName, objResult);
					} else if (aryNullFields != null && mapNullFields.containsKey(strName))
						hsResult.put(strName, null);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return hsResult;
	}

	public static boolean CheckInField(String strPropertiesName, String[] aryMethodName) {
		for (int i = 0; i < aryMethodName.length; i++) {
			if (strPropertiesName.equals(aryMethodName[i]))
				return true;
		}
		return false;
	}

}
