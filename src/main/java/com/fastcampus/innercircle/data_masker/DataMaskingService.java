package com.fastcampus.innercircle.data_masker;

import java.lang.reflect.Field;

public class DataMaskingService {

    public static void mask(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Masked.class)) {
                Masked masked = field.getAnnotation(Masked.class);
                field.setAccessible(true);
                Object value = field.get(object);
                if (value != null && value instanceof String) {
                    String maskedValue = applyMask((String) value, masked);
                    field.set(object, maskedValue);
                }
            }
        }
    }

    private static String applyMask(String value, Masked masked) {
        return switch (masked.maskingType()) {
            // 부분 마스킹
            case PARTIAL -> partialMask(value, masked.start(), masked.end(), masked.replaceChar());
            case FULL -> fullMask(value, masked.replaceChar());
            // 고정 패턴 마스킹
            case FIXED_PATTERN -> fixedPattern(value, masked.replaceChar(), masked.pattern());
        };
    }

    private static String partialMask(String value, int start, int end, char replaceChar) {
        int length = value.length();
        if (start > length || end > length || start > end) {
            return value;
        }

        StringBuilder maskedValue = new StringBuilder();
        maskedValue.append(value, 0, start);
        for (int i = start; i < end; i++) {
            maskedValue.append(replaceChar);
        }
        maskedValue.append(value.substring(end));
        return maskedValue.toString();
    }

    private static String fullMask(String value, char replaceChar) {
        return new String(new char[value.length()]).replace('\0', replaceChar);
    }

    private static String fixedPattern(String value, char replaceChar, String pattern) {

        if (pattern == null || pattern.isEmpty()) {
            return value;
        }

        StringBuilder maskedValue = new StringBuilder();
        int index = 0;
        for (char c :
                pattern.toCharArray()) {
            if (c == replaceChar) {
                maskedValue.append(replaceChar);
            } else {
                if (index < value.length()) {
                    maskedValue.append(value.charAt(index));
                } else {
                    maskedValue.append(replaceChar);
                }
            }
            index++;
        }
        return maskedValue.toString();
    }
}
