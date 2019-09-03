package com.uddernetworks.bfjvm.bytecode.chunks.fields;

import com.uddernetworks.bfjvm.bytecode.accessmodifier.AccessModifier;

public enum FieldAccessModifier implements AccessModifier {
    PUBLIC(0x0001),
    PRIVATE(0x0002),
    PROTECTED(0x0004),
    STATIC(0x0008),
    FINAL(0x0010),
    VOLATILE(0x0040),
    TRANSIENT(0x0080),
    SYNTHETIC(0x1000),
    ENUM(0x4000);

    private int value;

    FieldAccessModifier(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
