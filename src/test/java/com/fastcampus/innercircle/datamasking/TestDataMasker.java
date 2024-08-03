package com.fastcampus.innercircle.datamasking;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDataMasker {

    @Test
    void testPartialMask() {

        // given
        Masked masked = new MaskedImpl(Masked.MaskingType.PARTIAL, 1, 2, '*', "", "");

        // when
        String result = DataMasker.applyMask("홍길동", masked);

        // then
        assertEquals("홍*동", result);
    }

    @Test
    void testFullMask() {

        // given
        Masked masked = new MaskedImpl(Masked.MaskingType.FULL, 0, 0, '*', "", "");

        // when
        String result = DataMasker.applyMask("1234567890123456", masked);

        // then
        assertEquals("****************", result);
    }

    @Test
    void testFixedPatternMask() {

        // given
        Masked masked = new MaskedImpl(Masked.MaskingType.FIXED_PATTERN, 0, 0, '*', "010-****-1234", "");

        // when
        String result = DataMasker.applyMask("010-1234-5678", masked);

        // then
        assertEquals("010-****-5678", result);
    }

    @Test
    void testRrnMask() {

        // given
        Masked masked = new MaskedImpl(Masked.MaskingType.RRN, 0, 0, '*', "", "");

        // when
        String result = DataMasker.applyMask("123456-1234567", masked);

        // then
        assertEquals("123456-1******", result);
    }

    @Test
    void testEmailMask() {

        // given
        Masked masked = new MaskedImpl(Masked.MaskingType.EMAIL, 0, 0, '*', "", "");

        // when
        String result = DataMasker.applyMask("example@abcd.com", masked);

        // then
        assertEquals("ex*****@abcd.com", result);
    }

    @Test
    void testAddressMask() {

        // given
        Masked masked = new MaskedImpl(Masked.MaskingType.ADDRESS, 0, 0, '*', "", "");

        // when
        String result = DataMasker.applyMask("서울 영등포구 영등포로3길 12-34", masked);

        // then
        assertEquals("서울 영등포구 영등포로3길 ****", result);
    }

    @Test
    void testCreditCardMask() {

        // given
        Masked masked = new MaskedImpl(Masked.MaskingType.CREDIT_CARD, 0, 0, '*', "", "");

        // when
        String result = DataMasker.applyMask("1234-5678-1234-5678", masked);

        // then
        assertEquals("1234-56**-****-5678", result);
    }

    @Test
    void testCustomMask() {

        // given
        Masked masked = new MaskedImpl(Masked.MaskingType.CUSTOM, 0, 0, '*', "^\\d{4}(-\\d{4})", "****$1");

        // when
        String result = DataMasker.applyMask("1599-9009", masked);

        // then
        assertEquals("****-9009", result);
    }

    private record MaskedImpl(MaskingType maskingType, int start, int end, char replaceChar, String pattern,
                              String replacement) implements Masked {

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
    }
}
