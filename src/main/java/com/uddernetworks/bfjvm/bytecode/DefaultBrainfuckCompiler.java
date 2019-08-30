package com.uddernetworks.bfjvm.bytecode;

import com.uddernetworks.bfjvm.brainfuck.DefaultBrainfuckInterpreter;

public class DefaultBrainfuckCompiler implements BrainfuckCompiler {

    @Override
    public BytecodeClass compileBrainfuck(String program) {
        var interpreter = new DefaultBrainfuckInterpreter();
        interpreter.readProgram(program);

        var classCreator = new DefaultClassCreator();
        classCreator.setName("Brainfuck");


        return classCreator.create();
    }
}
