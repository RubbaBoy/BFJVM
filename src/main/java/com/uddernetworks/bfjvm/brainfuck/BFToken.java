package com.uddernetworks.bfjvm.brainfuck;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum BFToken {
    ADD('+'),
    SUB('-'),
    LEFT('<'),
    RIGHT('>'),
    PRINT('.'),
    INPUT(','),
    LLOOP('['),
    RLOOP(']');

    private char character;

    BFToken(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
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
