package com.uddernetworks.bfjvm.bytecode;

public interface BrainfuckCompiler {

    BytecodeClass compileBrainfuck(String program, String name);

}
