package com.uddernetworks.bfjvm.brainfuck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultBrainfuckInterpreter implements BrainfuckInterpreter {

    private static Logger LOGGER = LoggerFactory.getLogger(DefaultBrainfuckInterpreter.class);

    private int index;
    private List<BFToken> tokens;
    private Map<Integer, Map.Entry<Integer, Integer>> pairIndices = new HashMap<>();

    @Override
    public void readProgram(String program) {
        program = program.replaceAll(BFToken.getNotRegex(), "");
        LOGGER.info("Program: {}", program);
        tokens = program.chars().mapToObj(BFToken::fromChar).collect(Collectors.toUnmodifiableList());
        LOGGER.info("Tokens = {}", tokens);
        // <depth, startIndex>
        var incomplete = new HashMap<Integer, Integer>();

        int depth = 0;
        for (int i = 0; i < tokens.size(); i++) {
            var current = tokens.get(i);
            if (current == BFToken.LLOOP) {
                incomplete.put(++depth, i);
            } else if (current == BFToken.RLOOP) {
                if (!incomplete.containsKey(depth)) {
                    LOGGER.error("Unbalanced brackets at " + index);
                    System.exit(0);
                }
                pairIndices.put(pairIndices.size(), new AbstractMap.SimpleEntry<>(incomplete.remove(depth), i));
                depth--;
            }
        }

        if (depth != 0) {
            LOGGER.error("Unbalanced ending brackets (Ending depth of {})", depth);
            System.exit(0);
        }

        System.out.println(pairIndices);
    }

    private int getIdOfLeft(int index) {
        return pairIndices.entrySet().stream().filter(entry -> entry.getValue().getKey() == index).findFirst().map(Map.Entry::getKey).orElse(-1);
    }

    private int getIdOfRight(int index) {
        return pairIndices.entrySet().stream().filter(entry -> entry.getValue().getValue() == index).findFirst().map(Map.Entry::getKey).orElse(-1);
    }

    @Override
    public boolean hasNext() {
        return index < tokens.size();
    }

    @Override
    public BFToken nextToken() {
        return tokens.get(index++);
    }

    @Override
    public void resetNext() {
        index = 0;
    }

    @Override
    public int loopId() {
        var curr = tokens.get(index - 1);
        if (curr == BFToken.LLOOP) {
            return getIdOfLeft(index - 1);
        } else if (curr == BFToken.RLOOP) {
            return getIdOfRight(index - 1);
        }
        LOGGER.info("-1 because token: {}", curr.name());
        return -1;
    }
}
