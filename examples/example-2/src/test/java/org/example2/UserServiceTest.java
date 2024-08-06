package org.example2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testMasking() {
        User user = userService.getUser();

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
