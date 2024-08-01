package com.fastcampus.innercircle.data_masker;

import com.fastcampus.innercircle.data_masker.examples.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataMaskingServiceTest {

    @Test
    void testMaskUser() throws IllegalAccessException {

        // given
        User user = new User();
        user.setId(123L);
        user.setName("홍길동");
        user.setRrn("1234561234567");
        user.setPhoneNumber("010-1234-5678");

        // when
        DataMaskingService.mask(user);

        // then
        assertEquals(123L, user.getId());
        assertEquals("홍*동", user.getName());
        assertEquals("*************", user.getRrn());
        assertEquals("010-****-5678", user.getPhoneNumber());
    }
}
