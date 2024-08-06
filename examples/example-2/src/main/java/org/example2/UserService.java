package org.example2;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    @ApplyMasking
    public User getUser() {
        return new User(123L,
                "홍길동",
                "123456-1234567",
                "010-1234-5678",
                "example@abcd.com",
                "서울 영등포구 영등포로3길 12-34",
                "1234-5678-1234-5678",
                "1234567890123456",
                "1599-9009");
    }
}
