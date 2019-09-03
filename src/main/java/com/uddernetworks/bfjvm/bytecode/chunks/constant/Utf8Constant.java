package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import com.uddernetworks.bfjvm.utils.ByteUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.UTF8;
import static com.uddernetworks.bfjvm.utils.ByteUtils.intToHex;

public class Utf8Constant extends Constant {

    public Utf8Constant(String string) {
        super();
        var sizeBytes = ByteUtils.decodeHex(intToHex(string.length(), true));
        var stringBytes = Arrays.stream(string.split("")).map(s -> (byte) s.charAt(0)).collect(Collectors.toUnmodifiableList());
        bytes = new byte[sizeBytes.length + stringBytes.size()];
        var offset = sizeBytes.length;
        System.arraycopy(sizeBytes, 0, bytes, 0, offset);

        for (int i = 0; i < stringBytes.size(); i++) {
            bytes[i + offset] = stringBytes.get(i);
        }
    }

    @Override
    public ConstantType getType() {
        return UTF8;
    }
}
