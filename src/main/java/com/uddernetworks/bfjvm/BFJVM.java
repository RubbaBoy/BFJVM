package com.uddernetworks.bfjvm;

import com.uddernetworks.bfjvm.bytecode.DefaultBrainfuckCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BFJVM {

    private static Logger LOGGER = LoggerFactory.getLogger(BFJVM.class);

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            LOGGER.error("The program must be supplied with a brainfuck file");
            System.exit(0);
        }

        var inputFile = new File(args[0]);
        if (!inputFile.exists()) {
            LOGGER.error("The given file \"{}\" does not exist!", inputFile.getAbsolutePath());
            System.exit(0);
        }

        var bfCode = new String(Files.readAllBytes(inputFile.toPath()));

        var compiler = new DefaultBrainfuckCompiler();
        var start = System.currentTimeMillis();
        var clazz = compiler.compileBrainfuck(bfCode);
        var duration = System.currentTimeMillis() - start;
        LOGGER.info("Compiled in {}ms", duration);

        start = System.currentTimeMillis();
        var outPath = Paths.get(inputFile.getName() + ".class");
        Files.write(outPath, clazz.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        duration = System.currentTimeMillis() - start;
        LOGGER.info("Wrote to class file in {}ms", duration);
    }

}
