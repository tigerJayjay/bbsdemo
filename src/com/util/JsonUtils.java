package com.util;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class JsonUtils {
	/**
	 * 
	 * @Description 把JsonArray转换成List<Map>
	 */
	public static List getList4Json(String JsonArray) {
		List list = new ArrayList();
		Map map = new HashMap();
		if (JsonArray != null && !JsonArray.equals("")) {
			Object obj = JSONValue.parse(JsonArray);
			JSONArray jsonArray = (JSONArray) obj;
			for (int i = 0; i < jsonArray.size(); i++) {
				map = getMap4Json(jsonArray.get(i).toString());
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description ��jsonObjec把jsonObject转换成map
	 */
	public static Map getMap4Json(String jsonString) {
		Object obj = JSONValue.parse(jsonString);
		JSONObject jsonObject = (JSONObject) obj;
		Iterator keyIter = jsonObject.keySet().iterator();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}
	/**
	 * @Description 把map格式的数据转成jsonObject
	 * @param map
	 *            <String,String>
	 * @return
	 */
	public static JSONObject getJson4Map(Map map) {
		JSONObject jsonObject = new JSONObject();
		if (map != null && !map.isEmpty()) {
			Iterator keyIter = map.keySet().iterator();
			String key;
			Object value;
			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				if (map.get(key) instanceof java.util.Date) {
					String format = "yyyy-MM-dd HH:mm:ss";
					Date date = (Date) map.get(key);
					value = getDatatoString(date, format);
				} else {
					value = map.get(key);
				}
				jsonObject.put(key, value);
			}
		}
		return jsonObject;
	}
	/**
	 * 
	 * @Description 这边的DateUtils包里没有这个转fulldate的方法。所以把它放这里了
	 * @param date
	 * @param format
	 * @return
	 */
	private static String getDatatoString(Date date, String format) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 
	 * @描述：object转json
	 *
	 * @参数：@param obj
	 * @参数：@return
	 *
	 * @返回值：String
	 */
	public static String object2json(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof String || obj instanceof Integer
				|| obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Short || obj instanceof Double
				|| obj instanceof Long || obj instanceof BigDecimal
				|| obj instanceof BigInteger || obj instanceof Byte
				|| obj instanceof Date) {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		} else if (obj instanceof Object[]) {
			json.append(array2json((Object[]) obj));
		} else if (obj instanceof List) {
			json.append(list2json((List<?>) obj));
		} else if (obj instanceof Map) {
			json.append(map2json((Map<?, ?>) obj));
		} else if (obj instanceof Set) {
			json.append(set2json((Set<?>) obj));
		} else {
			json.append(bean2json(obj));
		}
		return json.toString();
	}

	/**
	 * 
	 * @描述：bean转json
	 *
	 * @参数：@param bean
	 * @参数：@return
	 *
	 * @返回值：String
	 */
	public static String bean2json(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = object2json(props[i].getName());
					String value = object2json(props[i].getReadMethod().invoke(
							bean));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * 
	 * @描述：list转json

	 * @参数：@param list
	 * @参数：@return
	 *
	 * @返回值：String
	 */
	public static String list2json(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * 
	 * @描述：数组转json
	 *

	 * @参数：@param array
	 * @参数：@return
	 *
	 * @返回值：String
	 */
	public static String array2json(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * 
	 * @描述：map转json

	 * @参数：@param map
	 * @参数：@return
	 *
	 * @返回值：String
	 */
	public static String map2json(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append(object2json(key));
				json.append(":");
				json.append(object2json(map.get(key)));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * 
	 * @描述：set集合转json
	 *

	 * @参数：@param set
	 * @参数：@return
	 *
	 * @返回值：String
	 */
	public static String set2json(Set<?> set) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (set != null && set.size() > 0) {
			for (Object obj : set) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * 
	 * @描述：String 转 json
	 *
	 * @参数：@param s
	 * @参数：@return
	 *
	 * @返回值：String
	 */
	public static String string2json(String s) {
		if (s == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * json转成订单list
	 * @参数：@param json_unit
	 * @参数：@return
	 * @返回值：List
	 */
	public static List convertJsonToList(JSONArray jsonArr) {
		List<Object> list = new ArrayList<Object>();
		
        for(Object o : jsonArr){
            if(o instanceof JSONArray)
                list.add(convertJsonToList((JSONArray) o));
            else if(o instanceof JSONObject)
                list.add(convertJsonToList((JSONObject) o));
            else
                list.add(o);
        }
        return list;

	}
	
	public static  HashMap<String, Object> convertJsonToList(JSONObject json){
        HashMap<String, Object> map = new HashMap<String, Object>();
        Set<?> keys = json.keySet();
        for(Object key : keys){
            Object o = json.get(key);
            if(o instanceof JSONArray)
                map.put((String) key, convertJsonToList((JSONArray) o));
            else if(o instanceof JSONObject)
                map.put((String) key, convertJsonToList((JSONObject) o));
            else
                map.put((String) key, o);
        }
        return map;
    }
}
