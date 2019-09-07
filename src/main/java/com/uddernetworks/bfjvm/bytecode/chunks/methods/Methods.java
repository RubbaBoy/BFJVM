package com.uddernetworks.bfjvm.bytecode.chunks.methods;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.bytecode.chunks.BytecodeChunk;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import java.util.ArrayList;
import java.util.List;

public class Methods implements BytecodeChunk {

    private List<Method> methods = new ArrayList<>();

    public void addMethod(Method method) {
        methods.add(method);
    }

    @Override
    public String getName() {
        return "Methods";
    }

    @Override
    public byte[] getBytes() {
        var byteList = new ByteList();
        byteList.pushBytes(ByteUtils.intToFlatHex(methods.size(), 2));
        methods.forEach(method -> byteList.pushBytes(method.getBytes()));
        return byteList.toBytes();
    }
}
