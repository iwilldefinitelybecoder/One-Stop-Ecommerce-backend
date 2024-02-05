package com.Onestop.ecommerce.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonUtils {

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> map = new HashMap<>();

        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = json.get(key);

            if (value instanceof JSONObject) {
                // Recursively convert nested JSONObject
                value = jsonToMap((JSONObject) value);
            } else if (value instanceof JSONArray) {
                // Recursively convert nested JSONArray
                value = jsonArrayToList((JSONArray) value);
            }

            map.put(key, value);
        }

        return map;
    }

    public static Object[] jsonArrayToList(JSONArray array) throws JSONException {
        Object[] result = new Object[array.length()];
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);

            if (value instanceof JSONObject) {
                // Recursively convert nested JSONObject
                value = jsonToMap((JSONObject) value);
            } else if (value instanceof JSONArray) {
                // Recursively convert nested JSONArray
                value = jsonArrayToList((JSONArray) value);
            }

            result[i] = value;
        }

        return result;
    }


}
