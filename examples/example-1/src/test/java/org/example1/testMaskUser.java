package org.example1;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testMaskUser {

    @Test
    void runMain() throws IOException {

        // given
        final String jar = Arrays.stream(new File("build/libs").listFiles())
                .filter(file -> file.getName().startsWith("example-1")
                        && file.getName().endsWith(".jar"))
                .findFirst()
                .map(File::getAbsolutePath)
                .get();

        // when
        final String line = "java -jar " + jar;
        final CommandLine cmdLine = CommandLine.parse(line);
        final int exitValue = new DefaultExecutor().execute(cmdLine);

        // then
        assertEquals(0, exitValue);
        /*
        assertEquals(123L, user.getId());
        assertEquals("홍*동", user.getName());
        assertEquals("123456-1******", user.getRrn());
        assertEquals("010-****-5678", user.getPhoneNumber());
        assertEquals("ex*****@abcd.com", user.getEmail());
        assertEquals("서울 영등포구 영등포로3길 ****", user.getAddress());
        assertEquals("1234-56**-****-5678", user.getCreditCardNumber());
        assertEquals("****************", user.getAccountNumber());
        assertEquals("****-9009", user.getExt());

         */
    }
}
