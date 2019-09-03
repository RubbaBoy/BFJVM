package com.uddernetworks.bfjvm.bytecode.chunks;

import com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType;

public interface BytecodeChunk {
    String getName();
    byte[] getBytes();
}
