package com.uddernetworks.bfjvm.brainfuck;

public interface BrainfuckInterpreter {

    void readProgram(String program);

    void optimize();

    boolean hasNext();

    BFDataToken nextToken();

    void resetNext();

    /**
     * Gets the ID of the current loop.
     *
     * @return The unique ID for the loop, or -1 is not a [ or ]
     */
    int loopId();

}
