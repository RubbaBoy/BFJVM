package com.uddernetworks.bfjvm.bytecode;

import java.util.List;

public class DefaultBytecodeClass implements BytecodeClass {

    private List<Byte> bytes;

    public DefaultBytecodeClass(List<Byte> bytes) {
        this.bytes = bytes;
    }


    @Override
    public List<Byte> getBytes() {
        return bytes;
    }
}
