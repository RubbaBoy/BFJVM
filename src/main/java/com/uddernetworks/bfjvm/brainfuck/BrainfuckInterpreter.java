package com.uddernetworks.bfjvm.brainfuck;

public interface BrainfuckInterpreter {

    void readProgram(String program);

    boolean hasNext();

    BFToken nextToken();

    void resetNext();

}
