package com.uddernetworks.bfjvm.brainfuck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultBrainfuckInterpreter implements BrainfuckInterpreter {

    private static Logger LOGGER = LoggerFactory.getLogger(DefaultBrainfuckInterpreter.class);

    private int index;
    private List<BFToken> tokens;

    @Override
    public void readProgram(String program) {
        program = program.replaceAll(BFToken.getNotRegex(), "");
        LOGGER.info("Program: {}", program);
        tokens = program.chars().mapToObj(BFToken::fromChar).collect(Collectors.toUnmodifiableList());
        LOGGER.info("Tokens = {}", tokens);
    }

    @Override
    public boolean hasNext() {
        return index + 1 < tokens.size();
    }

    @Override
    public BFToken nextToken() {
        return tokens.get(index++);
    }

    @Override
    public void resetNext() {
        index = 0;
    }
}
