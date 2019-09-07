package com.uddernetworks.bfjvm.brainfuck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DefaultBrainfuckInterpreter implements BrainfuckInterpreter {

    private static Logger LOGGER = LoggerFactory.getLogger(DefaultBrainfuckInterpreter.class);

    private int index;
    private boolean optimize;
    private List<BFDataToken> tokens;
    private Map<Integer, Map.Entry<Integer, Integer>> pairIndices = new HashMap<>();

    public DefaultBrainfuckInterpreter(boolean optimize) {
        this.optimize = optimize;
    }

    @Override
    public void readProgram(String program) {
        program = program.replaceAll(BFToken.getNotRegex(), "");
        tokens = program.chars().mapToObj(BFToken::fromChar).map(BFDataToken::new).collect(Collectors.toUnmodifiableList());

        if (optimize) optimize();

        var incomplete = new HashMap<Integer, Integer>();

        int depth = 0;
        for (int i = 0; i < tokens.size(); i++) {
            var current = tokens.get(i).getToken();
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
    }

    private int getIdOf(Predicate<Map.Entry<Integer, Map.Entry<Integer, Integer>>> predicate) {
        return pairIndices.entrySet().stream().filter(predicate).findFirst().map(Map.Entry::getKey).orElse(-1);
    }

    private int getIdOfLeft(int index) {
        return getIdOf(entry -> entry.getValue().getKey() == index);
    }

    private int getIdOfRight(int index) {
        return getIdOf(entry -> entry.getValue().getValue() == index);
    }

    @Override
    public void optimize() {
        var optimizedTokens = new ArrayList<BFDataToken>();
        BFToken currTokenType = null;
        var currCombining = new ArrayList<BFDataToken>();
        for (var dataToken : tokens) {
            var token = dataToken.getToken();
            if (currTokenType == null && token.isRepeatable()) currTokenType = token;
            if (currTokenType == token) {
                currCombining.add(dataToken);
            } else if (!currCombining.isEmpty()) {
                optimizedTokens.add(new BFDataToken(currTokenType, currCombining.stream().mapToInt(BFDataToken::getData).sum()));
                currCombining.clear();

                if (token.isRepeatable()) {
                    currTokenType = token;
                    currCombining.add(dataToken);
                } else {
                    currTokenType = null;
                    optimizedTokens.add(new BFDataToken(token));
                }
            } else { // Probably non-repeatable
                currTokenType = null;
                optimizedTokens.add(new BFDataToken(token));
            }
        }
        if (currTokenType != null) optimizedTokens.add(new BFDataToken(currTokenType, currCombining.stream().mapToInt(BFDataToken::getData).sum()));

        tokens = List.copyOf(optimizedTokens);
    }

    @Override
    public boolean hasNext() {
        return index < tokens.size();
    }

    @Override
    public BFDataToken nextToken() {
        return tokens.get(index++);
    }

    @Override
    public void resetNext() {
        index = 0;
    }

    @Override
    public int loopId() {
        var curr = tokens.get(index - 1).getToken();
        if (curr == BFToken.LLOOP) {
            return getIdOfLeft(index - 1);
        } else if (curr == BFToken.RLOOP) {
            return getIdOfRight(index - 1);
        }
        return -1;
    }
}
