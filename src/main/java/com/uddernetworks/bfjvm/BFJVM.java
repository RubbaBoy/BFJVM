package com.uddernetworks.bfjvm;

import com.uddernetworks.bfjvm.brainfuck.DefaultBrainfuckInterpreter;
import com.uddernetworks.bfjvm.bytecode.BrainfuckCompiler;
import com.uddernetworks.bfjvm.bytecode.DefaultBrainfuckCompiler;
import com.uddernetworks.bfjvm.bytecode.DefaultClassCreator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BFJVM {

    public static void main(String[] args) throws IOException {
        var compiler = new DefaultBrainfuckCompiler();
        var clazz = compiler.compileBrainfuck("++++++++++\n" +
                "[->+++++++<]\n" +
                ">++.\n" +
                "+.");

        System.out.println(clazz.getBytes());

        var byteList = clazz.getBytes();
        var bytes = new byte[byteList.size()];
        for (int i = 0; i < clazz.getBytes().size(); i++) {
            bytes[i] = byteList.get(i);
        }

        Files.write(Paths.get("E:\\BFJVM\\out.class"), bytes, StandardOpenOption.TRUNCATE_EXISTING);
    }

}
