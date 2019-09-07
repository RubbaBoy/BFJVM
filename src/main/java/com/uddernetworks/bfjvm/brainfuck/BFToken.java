package com.uddernetworks.bfjvm.brainfuck;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum BFToken {
    ADD('+', true),
    SUB('-', true),
    LEFT('<', true),
    RIGHT('>', true),
    PRINT('.', false),
    INPUT(',', false),
    LLOOP('[', false),
    RLOOP(']', false);

    private char character;
    private boolean repeatable;

    BFToken(char character, boolean repeatable) {
        this.character = character;
        this.repeatable = repeatable;
    }

    public char getCharacter() {
        return character;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public static BFToken fromChar(int character) {
        return fromChar((char) character);
    }

    public static BFToken fromChar(char character) {
        return Arrays.stream(values()).filter(token -> token.character == character).findFirst().orElseThrow();
    }

    public static String getNotRegex() {
        return "[^" + Arrays.stream(values()).map(token -> "\\" + token.character).collect(Collectors.joining()) + "]";
    }

    @Override
    public String toString() {
        return name();
    }
}
