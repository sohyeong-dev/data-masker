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
    }
}
