package com.fastcampus.innercircle.datamasking.examples;

import com.fastcampus.innercircle.datamasking.Masked;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long id;

    @Masked(maskingType = Masked.MaskingType.PARTIAL, start = 1, end = 2, replaceChar = '*')
    private String name;

    @Masked(maskingType = Masked.MaskingType.RRN)
    private String rrn;

    @Masked(maskingType = Masked.MaskingType.FIXED_PATTERN, pattern = "010-****-1234")
    private String phoneNumber;

    @Masked(maskingType = Masked.MaskingType.EMAIL)
    private String email;

    @Masked(maskingType = Masked.MaskingType.ADDRESS)
    private String address;

    @Masked(maskingType = Masked.MaskingType.CREDIT_CARD)
    private String creditCardNumber;

    @Masked(maskingType = Masked.MaskingType.FULL)
    private String accountNumber;

    @Masked(maskingType = Masked.MaskingType.CUSTOM, pattern = "^\\d{4}(?=-\\d{4})", replacement = "****")
    private String ext;
}
