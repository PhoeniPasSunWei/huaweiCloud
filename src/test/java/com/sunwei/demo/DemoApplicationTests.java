package com.sunwei.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    public void TestPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encode = encoder.encode("333");
//        String encode2 = encoder.encode("333");
//        System.out.println(encode);
//        System.out.println(encode2);
        boolean matches = encoder.matches("333", "$2a$10$xtPXx2s72yu4SGQB3Ut.V.htyOxW21q8mCLWdbZmVDOjrNuH9ktfa");
        System.out.println(matches);
    }

}
