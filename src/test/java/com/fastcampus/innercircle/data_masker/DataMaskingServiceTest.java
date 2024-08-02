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
        user.setRrn("123456-1234567");
        user.setPhoneNumber("010-1234-5678");
        user.setEmail("example@abcd.com");
        user.setAddress("서울 영등포구 영등포로3길 12-34");
        user.setCreditCardNumber("1234-5678-1234-5678");
        user.setAccountNumber("1234567890123456");
        user.setExt("1599-9009");

        // when
        DataMaskingService.mask(user);

        // then
        assertEquals(123L, user.getId());
        assertEquals("홍*동", user.getName());
        assertEquals("123456-1******", user.getRrn());
        assertEquals("010-****-5678", user.getPhoneNumber());
        assertEquals("ex*****@abcd.com", user.getEmail());
        assertEquals("서울 영등포구 영등포로3길 ****", user.getAddress());
        assertEquals("1234-56**-****-5678", user.getCreditCardNumber());
        assertEquals("****************", user.getAccountNumber());
        assertEquals("****-9009", user.getExt());
    }
}
