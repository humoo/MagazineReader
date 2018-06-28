package com.hyc.libs.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhxumao on 2017/11/22 11:59.
 */

public class JsonUtil {
    public JsonUtil() {
    }

    public static JSONObject string2Json(String json){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }

    public static Object GetObject(JSONObject jsonObject, Object... nodePaths) {
        if (null == jsonObject) {
            return null;
        } else if (null != nodePaths && 0 != nodePaths.length) {
            Object obj = jsonObject;

            for (int i = 0; i < nodePaths.length; ++i) {
                if (null == obj) {
                    return null;
                }

                Object nodePath = nodePaths[i];
                if (null == nodePath) {
                    return null;
                }

                if (nodePath instanceof String && obj instanceof JSONObject) {
                    try {
                        obj = ((JSONObject) obj).get((String) nodePath);
                    } catch (Exception var9) {
                        var9.printStackTrace();
                        Log.d("ASY - JsonUtil", "Err nodePath[%s]" + new Object[]{nodePath});
                        return null;
                    }
                } else if (nodePath instanceof Integer && obj instanceof JSONArray) {
                    int index = ((Integer) nodePath).intValue();
                    JSONArray jsonArray = (JSONArray) obj;
                    if (null == jsonArray) {
                        return null;
                    }

                    if (index >= jsonArray.length() || 0 == jsonArray.length()) {
                        return null;
                    }

                    try {
                        obj = jsonArray.get(index);
                    } catch (Exception var8) {
                        return null;
                    }
                }
            }

            return obj;
        } else {
            return jsonObject;
        }
    }

    static Object GetObjectPre(JSONObject jsonObject, int size, Object... nodePaths) {
        if (null == jsonObject) {
            return null;
        } else if (null != nodePaths && 0 != nodePaths.length) {
            Object obj = jsonObject;

            for (int i = 0; i < size; ++i) {
                if (null == obj) {
                    return null;
                }

                Object nodePath = nodePaths[i];
                if (null == nodePath) {
                    return null;
                }

                if (nodePath instanceof String && obj instanceof JSONObject) {
                    try {
                        obj = ((JSONObject) obj).get((String) nodePath);
                    } catch (Exception var10) {
                        var10.printStackTrace();
                        Log.d("ASY - JsonUtil", "Err nodePath[%s]" + new Object[]{nodePath});
                        return null;
                    }
                } else if (nodePath instanceof Integer && obj instanceof JSONArray) {
                    int index = ((Integer) nodePath).intValue();
                    JSONArray jsonArray = (JSONArray) obj;
                    if (null == jsonArray) {
                        return null;
                    }

                    if (index >= jsonArray.length() || 0 == jsonArray.length()) {
                        return null;
                    }

                    try {
                        obj = jsonArray.get(index);
                    } catch (Exception var9) {
                        return null;
                    }
                }
            }

            return obj;
        } else {
            return jsonObject;
        }
    }

    public static JSONObject GetJSONObject(JSONObject jsonObject, Object... nodePaths) {
        Object object = GetObject(jsonObject, nodePaths);
        return object instanceof JSONObject ? (JSONObject) object : null;
    }

    public static JSONObject GetJSONObjectDefault(JSONObject jsonObject, JSONObject defaultJsonObject, Object... nodePaths) {
        JSONObject object = GetJSONObject(jsonObject, nodePaths);
        return null == object ? defaultJsonObject : object;
    }

    public static JSONArray GetJSONArray(JSONObject jsonObject, Object... nodePaths) {
        Object object = GetObject(jsonObject, nodePaths);
        return object instanceof JSONArray ? (JSONArray) object : null;
    }

    public static JSONArray GetJSONArrayDefault(JSONObject jsonObject, JSONArray defaultArray, Object... nodePaths) {
        JSONArray jsonArray = GetJSONArray(jsonObject, nodePaths);
        return null == jsonArray ? defaultArray : jsonArray;
    }

    public static int GetJSONArrayLength(JSONObject jsonObject, Object... nodePaths) {
        Object object = GetObject(jsonObject, nodePaths);
        if (null == object) {
            return 0;
        } else if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            return jsonArray.length();
        } else {
            return 0;
        }
    }

    public static String GetString(JSONObject jsonObject, Object... nodePaths) {
        Object object = GetObject(jsonObject, nodePaths);
        return object instanceof String ? (String) object : null;
    }

    public static String GetStringDefault(JSONObject jsonObject, String defaultString, Object... nodePaths) {
        String string = GetString(jsonObject, nodePaths);
        return !StringUtil.isFine(string) ? defaultString : string;
    }

    public static int GetIntDefault(JSONObject jsonObject, Object... nodePaths) {
        return GetInt(jsonObject, -1, nodePaths);
    }

    public static int GetInt(JSONObject jsonObject, int defaultInt, Object... nodePaths) {
        Object object = GetObjectPre(jsonObject, nodePaths.length - 1, nodePaths);
        String e;
        if (object instanceof String) {
            e = (String) object;
            return StringUtil2.s2i(e, defaultInt);
        } else {
            try {
                e = (String) nodePaths[nodePaths.length - 1];
                JSONObject jsONObject = (JSONObject) object;
                return jsONObject.getInt(e);
            } catch (Exception var6) {
                return defaultInt;
            }
        }
    }

    public static long GetLong(JSONObject jsonObject, int defaultLong, Object... nodePaths) {
        Object object = GetObjectPre(jsonObject, nodePaths.length - 1, nodePaths);
        String e;
        if (object instanceof String) {
            e = (String) object;
            return StringUtil2.s2l(e, (long) defaultLong);
        } else {
            try {
                e = (String) nodePaths[nodePaths.length - 1];
                JSONObject jsONObject = (JSONObject) object;
                return jsONObject.getLong(e);
            } catch (Exception var6) {
                return (long) defaultLong;
            }
        }
    }

    public static double GetDouble(JSONObject jsonObject, int defaultDouble, Object... nodePaths) {
        Object object = GetObjectPre(jsonObject, nodePaths.length - 1, nodePaths);
        String e;
        if (object instanceof String) {
            e = (String) object;
            return StringUtil2.s2d(e, (double) defaultDouble);
        } else {
            try {
                e = (String) nodePaths[nodePaths.length - 1];
                JSONObject jsONObject = (JSONObject) object;
                return jsONObject.getDouble(e);
            } catch (Exception var6) {
                return (double) defaultDouble;
            }
        }
    }
}
