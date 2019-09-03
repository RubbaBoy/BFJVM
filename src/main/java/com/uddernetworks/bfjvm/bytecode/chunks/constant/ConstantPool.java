package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.bytecode.chunks.BytecodeChunk;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConstantPool implements BytecodeChunk {

    private Map<Constant, Integer> constants = new HashMap<>() {
        @Override
        public Integer remove(Object key) {
            throw new UnsupportedOperationException("Removing constants is not allowed!");
        }
    };

    private final static ConstantPool constantPool = new ConstantPool();
    private int lastId = -1;

    public int addConstant(Constant constant) {
        return constants.computeIfAbsent(constant, c -> {
            constant.setId(++lastId);
            return lastId;
        });
    }

    public int getIndex(Constant constant) {
        return addConstant(constant);
    }

    public static ConstantPool getInstance() {
        return constantPool;
    }

    @Override
    public String getName() {
        return "ConstantPool";
    }

    @Override
    public byte[] getBytes() {
        var bytes = new ByteList();

        //// 2u - Size of constant pool
//        bytes.pushBytes(ByteUtils.intToFlatHex(constants.size()));

        // This will always be in order, as removing constants is impossible
        constants.forEach((constant, integer) -> bytes.pushBytes(constant.getBytes()));

        for (byte b : bytes.toBytes()) {
            System.out.print((char) b);
        }
        System.out.println("\n");

        return bytes.toBytes();
    }
}
