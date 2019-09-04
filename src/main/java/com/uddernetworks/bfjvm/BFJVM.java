package com.uddernetworks.bfjvm;

import com.uddernetworks.bfjvm.brainfuck.DefaultBrainfuckInterpreter;
import com.uddernetworks.bfjvm.bytecode.BrainfuckCompiler;
import com.uddernetworks.bfjvm.bytecode.DefaultBrainfuckCompiler;
import com.uddernetworks.bfjvm.bytecode.DefaultClassCreator;
import com.uddernetworks.bfjvm.utils.Commandline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BFJVM {

    private static Logger LOGGER = LoggerFactory.getLogger(BFJVM.class);

    public static void main(String[] args) throws IOException {
        var compiler = new DefaultBrainfuckCompiler();
        var clazz = compiler.compileBrainfuck("+++++>>+++<---.");

        System.out.println(clazz.getBytes());

        var byteList = clazz.getBytes();
        var bytes = new byte[byteList.size()];
        for (int i = 0; i < clazz.getBytes().size(); i++) {
            bytes[i] = byteList.get(i);
        }

        var outPath = Paths.get("out.class");
        Files.write(outPath, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        LOGGER.info("javap out.class:");
        LOGGER.info(Commandline.runCommand(true, "javap out.class"));
        LOGGER.info("javap -verbose out.class:");
        LOGGER.info(Commandline.runCommand(true, "javap", "-verbose", "out.class"));
    }

}
