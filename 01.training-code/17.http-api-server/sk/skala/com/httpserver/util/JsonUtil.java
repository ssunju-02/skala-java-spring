package sk.skala.com.httpserver.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Jackson 없이, Reflection만으로 아주 단순한 JSON 직렬화/역직렬화를 흉내내는 유틸리티
public class JsonUtil {

    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String s) {
            return "\"" + escape(s) + "\"";
        }
        if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }
        if (obj instanceof Collection<?> collection) {
            StringBuilder sb = new StringBuilder("[");
            boolean first = true;
            for (Object item : collection) {
                if (!first) {
                    sb.append(",");
                }
                sb.append(toJson(item));
                first = false;
            }
            return sb.append("]").toString();
        }

        StringBuilder sb = new StringBuilder("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        boolean first = true;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (!first) {
                    sb.append(",");
                }
                sb.append("\"").append(field.getName()).append("\":").append(toJson(value));
                first = false;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return sb.append("}").toString();
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            if (json == null || json.isBlank()) {
                return instance;
            }

            String trimmed = json.trim();
            trimmed = trimmed.substring(1, trimmed.length() - 1); // 앞뒤 { } 제거

            for (String pair : splitTopLevel(trimmed)) {
                if (pair.isBlank()) {
                    continue;
                }
                int colonIdx = pair.indexOf(':');
                String key = pair.substring(0, colonIdx).trim().replaceAll("^\"|\"$", "");
                String rawValue = pair.substring(colonIdx + 1).trim();

                Field field = findField(clazz, key);
                if (field == null) {
                    continue;
                }
                field.setAccessible(true);
                field.set(instance, parseValue(rawValue, field.getType()));
            }
            return instance;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("JSON 파싱 실패: " + json, e);
        }
    }

    private static Field findField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private static Object parseValue(String rawValue, Class<?> type) {
        if (rawValue.equals("null")) {
            return null;
        }
        if (type == String.class) {
            return rawValue.replaceAll("^\"|\"$", "").replace("\\\"", "\"");
        }
        if (type == long.class || type == Long.class) {
            return Long.parseLong(rawValue);
        }
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(rawValue);
        }
        if (type == double.class || type == Double.class) {
            return Double.parseDouble(rawValue);
        }
        if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(rawValue);
        }
        return rawValue;
    }

    // 최상위 depth의 콤마만 기준으로 분리 (문자열/중괄호 내부의 콤마는 무시)
    private static List<String> splitTopLevel(String s) {
        List<String> result = new ArrayList<>();
        int depth = 0;
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            }
            if (!inQuotes) {
                if (c == '{' || c == '[') {
                    depth++;
                }
                if (c == '}' || c == ']') {
                    depth--;
                }
            }
            if (c == ',' && depth == 0 && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        if (!current.isEmpty()) {
            result.add(current.toString());
        }
        return result;
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
