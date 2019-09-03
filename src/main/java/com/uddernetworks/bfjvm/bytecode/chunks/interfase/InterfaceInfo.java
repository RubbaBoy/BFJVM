package com.uddernetworks.bfjvm.bytecode.chunks.interfase;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.bytecode.chunks.BytecodeChunk;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.ClassConstant;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import java.util.Arrays;

import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class InterfaceInfo implements BytecodeChunk {

    private byte[] bytes;

    public InterfaceInfo(ClassConstant... interfaces) {
        var byteList = new ByteList();
        byteList.pushBytes(intToFlatHex(interfaces.length, 2));
        Arrays.stream(interfaces).forEach(constant -> byteList.pushBytes(intToFlatHex(constant.getId(), 2)));
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
