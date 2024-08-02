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
            // 주민등록번호 마스킹
            case RRN -> rrnMask(value, masked.replaceChar());
            // 이메일 주소 마스킹
            case EMAIL -> emailMask(value, masked.replaceChar());
            // 주소 마스킹
            case ADDRESS -> addressMask(value, masked.replaceChar());
            // 신용카드 번호 마스킹
            case CREDIT_CARD -> creditCardMask(value, masked.replaceChar());
            // 사용자 정의 마스킹
            case CUSTOM -> customMask(value, masked.pattern(), masked.replacement());
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

    private static String rrnMask(String rrn, char replaceChar) {
        // 000000-0******

        if (rrn.replaceAll("[^0-9]", "").length() != 13) {
            return rrn;
        }

        StringBuilder maskedValue = new StringBuilder();
        maskedValue.append(rrn, 0, 6);
        maskedValue.append("-");
        maskedValue.append(rrn, 7, 8);
        for (int i = 0; i < 6; i++) {
            maskedValue.append(replaceChar);
        }
        return maskedValue.toString();
    }

    private static String emailMask(String email, char replaceChar) {
        // ma******@abcd.com

        String[] parts = email.split("@");
        if (parts.length != 2) {
            return email;   // Invalid email Exception
        }

        String local = parts[0];
        String domain = parts[1];
        int local_len = local.length();

        StringBuilder maskedValue = new StringBuilder();
        maskedValue.append(local, 0, 2);
        for (int i = 2; i < local_len; i++) {
            maskedValue.append(replaceChar);
        }
        maskedValue.append("@");
        maskedValue.append(domain);
        return maskedValue.toString();
    }

    private static String addressMask(String address, char replaceChar) {
        // 서울 영등포구 영등포로3길 ****

        if (address == null || address.isEmpty()) {
            return address;
        }

        String[] parts = address.split(" ");
        if (parts.length < 3) {
            return address;
        }

        StringBuilder maskedAddress = new StringBuilder();
        maskedAddress.append(parts[0]).append(" "); // 시/도
        maskedAddress.append(parts[1]).append(" "); // 구
        maskedAddress.append(parts[2]).append(" "); // 도로명
        for (int i = 0; i < 4; i++) {
            maskedAddress.append(replaceChar);
        }
        return maskedAddress.toString().trim();
    }

    private static String creditCardMask(String creditCardNumber, char replaceChar) {
        // 4558-12**-****-3872

        String ccn = creditCardNumber.replaceAll("[^0-9]", "");
        if (ccn.length() < 16) {
            return creditCardNumber;
        }

        StringBuilder maskedValue = new StringBuilder();
        maskedValue.append(ccn.substring(0, 4)).append("-").append(ccn.substring(4, 6));
        for (int i = 0; i < 2; i++) {
            maskedValue.append(replaceChar);
        }
        maskedValue.append("-");
        for (int i = 0; i < 4; i++) {
            maskedValue.append(replaceChar);
        }
        maskedValue.append("-");
        maskedValue.append(ccn.substring(12));
        return maskedValue.toString();
    }

    private static String customMask(String value, String pattern, String replacement) {
        // 정규 표현식 패턴 매칭 및 마스킹 적용

        return value.replaceAll(pattern, replacement);
    }
}
