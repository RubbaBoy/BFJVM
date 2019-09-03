package com.uddernetworks.bfjvm.bytecode.chunks.clazz;

import com.uddernetworks.bfjvm.bytecode.accessmodifier.AccessModifier;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ClassAccessModifier implements AccessModifier {
    PUBLIC(0x0001),
    FINAL(0x0010),
    SUPER(0x0020),
    INTERFACE(0x0200),
    ABSTRACT(0x0400),
    SYNTHETIC(0x1000),
    ANNOTATION(0x2000),
    ENUM(0x4000);

    private int value;

    ClassAccessModifier(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
