package com.fastcampus.innercircle.data_masker.examples;

import com.fastcampus.innercircle.data_masker.Masked;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long id;

    @Masked(maskingType = Masked.MaskingType.PARTIAL, start = 1, end = 2, replaceChar = '*')
    private String name;

    @Masked(maskingType = Masked.MaskingType.FULL)
    private String rrn;

    @Masked(maskingType = Masked.MaskingType.FIXED_PATTERN, pattern = "010-****-1234")
    private String phoneNumber;
}
