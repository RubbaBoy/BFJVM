package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import java.util.Arrays;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.UTF8;
import static com.uddernetworks.bfjvm.utils.ByteUtils.createByteArray;
import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class Utf8Constant extends Constant {

    public Utf8Constant(String string) {
        super();
        var sizeBytes = intToFlatHex(string.length(), 2);
        var stringBytes = Arrays.stream(string.split("")).map(s -> (byte) s.charAt(0)).toArray(Byte[]::new);
        bytes = createByteArray(0x01, sizeBytes, stringBytes);
    }

    @Override
    public ConstantType getType() {
        return UTF8;
    }
}
