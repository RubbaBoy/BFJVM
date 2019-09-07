package com.uddernetworks.bfjvm.bytecode;

import java.util.List;

public class DefaultBytecodeClass implements BytecodeClass {

    private byte[] bytes;

    public DefaultBytecodeClass(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }
}
