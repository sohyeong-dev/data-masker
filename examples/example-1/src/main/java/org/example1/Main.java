package org.example1;

import com.fastcampus.innercircle.datamasking.DataMasker;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {

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

        System.out.println("마스킹 전: " + user);
        DataMasker.mask(user);
        System.out.println("마스킹 후: " + user);
    }
}
