package tech.wangjie.httpmanager.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * Created by wangjie on 2016/10/8.
 */
public abstract class ModelParamBuilder {

    private static final String TAG = ModelParamBuilder.class.getSimpleName();

    protected String charSet = "UTF-8";

    public ModelParamBuilder() {
    }

    public static LinkedHashMap<String, String> buildPrimaryMap(Object param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
        if (param == null) {
            return null;
        } else {
            ArrayList<Field> fieldList = getAllDeclaredFields(param.getClass());
            LinkedHashMap map = new LinkedHashMap(fieldList.size());

            Log.d(TAG , "Param Map Size :" + getAllDeclaredFields(param.getClass()).size());

            for (Field f : fieldList) {
                f.setAccessible(true);
                try {
                    map.put(f.getName(), f.get(param));

                    Log.d(TAG , "字段的名：" + f.getName() + " 值为：" + f.get(param));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return map;
        }
    }

    protected StringBuilder buildUriKey(StringBuilder sb, String key) throws UnsupportedEncodingException {
        if (key != null) {
            sb.append(this.encode(key)).append("=");
        }

        return sb;
    }

    public String decode(String content) throws UnsupportedEncodingException {
        return URLDecoder.decode(content, this.charSet);
    }

    public String encode(String content) throws UnsupportedEncodingException {
        return URLEncoder.encode(content, this.charSet);
    }

    protected static boolean isInvalidField(Field f) {
        return Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()) || f.isSynthetic();
    }

    protected static boolean isPrimitive(Object value) {
        return value instanceof CharSequence || value instanceof Number || value instanceof Boolean || value instanceof Character;
    }

    public static ArrayList<Field> getAllDeclaredFields(Class<?> claxx) {
        ArrayList fieldList;
        for (fieldList = new ArrayList(); claxx != null && claxx != Object.class; claxx = claxx.getSuperclass()) {
            Field[] fs = claxx.getDeclaredFields();

            for (int i = 0; i < fs.length; ++i) {
                Field f = fs[i];
                if (!isInvalidField(f)) {
                    fieldList.add(f);
                }
            }
        }

        return fieldList;
    }
}