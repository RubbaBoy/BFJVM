package com.uddernetworks.bfjvm.bytecode.chunks.interfase;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.bytecode.chunks.BytecodeChunk;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.ClassConstant;

import java.util.Arrays;

import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class InterfaceInfo implements BytecodeChunk {

    private byte[] bytes;

    public InterfaceInfo(ClassConstant... interfaces) {
        var byteList = new ByteList();
        byteList.pushBytes(intToFlatHex(interfaces.length, 2));
        Arrays.stream(interfaces).forEach(constant -> byteList.pushBytes(constant.getId()));
        bytes = byteList.toBytes();
    }

    @Override
    public String getName() {
        return "BytecodeChunk";
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }
}
