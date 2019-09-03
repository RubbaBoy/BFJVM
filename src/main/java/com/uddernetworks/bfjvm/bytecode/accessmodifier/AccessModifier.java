package com.uddernetworks.bfjvm.bytecode.accessmodifier;

import com.uddernetworks.bfjvm.bytecode.chunks.clazz.ClassAccessModifier;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface AccessModifier {
    int getValue();

    static byte[] getByte(AccessModifier... modifiers) {
        var accessors = Arrays.stream(modifiers).map(AccessModifier::getValue).collect(Collectors.toList());

        var result = -1;
        for (var value : accessors) {
            if (result == -1) {
                result = value;
            } else {
                result = result | value;
            }
        }

        return ByteUtils.intToFlatHex(result, 2);
    }
}
