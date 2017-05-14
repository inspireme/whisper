package jp.whisper.common.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * *********************************
 *
 * @ClassName: JsonUtil.java
 * @Description: JSONシリアライズ
 * @author: bieby
 * @createdAt: 2013-12-11
 **********************************
 */
public class JsonUtil {
	private final static SerializerFeature[] DEFAULT_FEATURES = {
			SerializerFeature.WriteMapNullValue,
			SerializerFeature.WriteNullListAsEmpty,
			// SerializerFeature.WriteNullNumberAsZero,
			SerializerFeature.WriteNullBooleanAsFalse,
			SerializerFeature.WriteNullStringAsEmpty,
			SerializerFeature.WriteDateUseDateFormat	};

	private static SerializeConfig DEFALULT_MAPPING = new SerializeConfig();

	static {
		DEFALULT_MAPPING.put(Date.class, new SimpleDateFormatSerializer(
				"yyyy-MM-dd"));
		DEFALULT_MAPPING.put(Timestamp.class, new SimpleDateFormatSerializer(
				"yyyy-MM-dd"));
	}

	/**
	 *
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	private JsonUtil() {

	}

	/**
	 *
	 * @Title: toJSONString
	 * @Description: javabean to JSON
	 * @param obj
	 *            obj
	 * @param features
	 *            features
	 * @return String
	 * @Author: bieby
	 * @Date: 2013-12-7
	 */
	public static String toJSONString(Object obj, SerializerFeature... features) {
		return JSON.toJSONString(obj, DEFALULT_MAPPING,
				(SerializerFeature[]) ArrayUtils.addAll(DEFAULT_FEATURES,
						features));
	}

	/**
	 *
	 * @Title: toJSONString
	 * @Description: javabean to JSON
	 * @param obj
	 *            obj
	 * @param features
	 *            features
	 * @param mapping
	 *            mapping
	 * @return value
	 * @Author: bieby
	 * @Date: 2013-12-7
	 */
	public static String toJSONString(Object obj, SerializeConfig mapping,
			SerializerFeature... features) {
		return JSON.toJSONString(obj, mapping, (SerializerFeature[]) ArrayUtils
				.addAll(DEFAULT_FEATURES, features));
	}

	/**
	 *
	 * @Title: parseArray
	 * @Description: json to objectlist
	 * @param batchParams
	 *            batchParams
	 * @param cls
	 *            cls
	 * @return objectlist
	 * @Author: bieby
	 * @Date: 2013-12-11
	 */
	public static <T> List<T> parseArray(String batchParams, Class<T> cls) {
		if (StringUtils.isNotEmpty(batchParams) && !"[]".equals(batchParams)) {
			return JSON.parseArray(batchParams, cls);
		} else {
			return new ArrayList<T>();
		}
	}

	/**
	 *
	 * @Title: getMapFromJson
	 * @Description: json to map for only level 1  {'TABLENAME':'PROVIDER','TYPE':'provider'}
	 * @param jsonString
	 *            jsonString
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public static final Map<String, Object> getMapFromJson(String jsonString) {
		if (StringUtil.isBlank(jsonString)) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> jsonMap = (Map<String, Object>) JSONObject
				.parseObject(jsonString);
		return jsonMap;
	}

	public static String javaObject2JsonObject(Object obj) {
		return JSON.toJSONString(obj);
	}
}
