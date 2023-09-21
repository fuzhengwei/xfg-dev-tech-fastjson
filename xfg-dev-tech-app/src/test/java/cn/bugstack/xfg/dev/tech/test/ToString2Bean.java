package cn.bugstack.xfg.dev.tech.test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToString2Bean {

    public static <T> T toObject(String str, Class<T> clazz) throws Exception {
        // 创建一个新的对象
        T obj = clazz.getDeclaredConstructor().newInstance();
        // 获取类对象
        Class<?> objClass = obj.getClass();
        // 解析字符串
        String[] fields = str.substring(str.indexOf("{") + 1, str.indexOf("}")).split(", ");

        // 遍历成员变量
        for (String field : fields) {
            // 获取成员变量名和值
            String[] parts = field.split("=");
            // 获取成员变量对象
            Field objField = objClass.getDeclaredField(parts[0].trim());
            // 设置成员变量可以访问
            objField.setAccessible(true);
            // 设置成员变量的值
            objField.set(obj, convertValue(objField.getType(), parts[1].trim()));
            // 设置成员变量不可访问
            objField.setAccessible(false);
        }

        return obj;
    }

    public static Object convertValue(Class<?> type, String value) throws ParseException {
        if ("'null'".equals(value) || "null".equals(value)) return null;
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        } else if (type == float.class || type == Float.class) {
            return Float.parseFloat(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == char.class || type == Character.class) {
            return value.charAt(0);
        } else if (type == byte.class || type == Byte.class) {
            return Byte.parseByte(value);
        } else if (type == short.class || type == Short.class) {
            return Short.parseShort(value);
        } else if (type == Date.class) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(value);
        } else {
            return value;
        }
    }

}
