package com.fastcampus.innercircle.data_masker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Masked {
    MaskingType maskingType();

    // 부분 마스킹용
    int start() default 0;
    int end() default 0;

    char replaceChar() default '*';

    String pattern() default ""; // 고정 패턴 마스킹용 패턴

    enum MaskingType {
        PARTIAL, FULL, FIXED_PATTERN, RRN, EMAIL, ADDRESS, CREDIT_CARD
    }
}

