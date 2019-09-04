package com.uddernetworks.bfjvm.bytecode.chunks.methods;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.Utf8Constant;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class CodeAttribute implements Attribute {

    private byte[] bytes;

    /**
     * Assumes no catches, and no attributes.
     */
    public CodeAttribute(Utf8Constant codeConstant, int maxStack, int maxLocals, Object... code) {
        this(codeConstant.getId(), maxStack, maxLocals, code);
    }

    /**
     *
     * @param codeId u2
     * @param maxStack
     * @param maxLocals
     * @param code
     */
    public CodeAttribute(byte[] codeId, int maxStack, int maxLocals, Object... code) {
        var byteList = new ByteList();
        var flatCode = ByteUtils.createByteArray(code);
        byteList.pushBytes(codeId);
        byteList.pushBytes(intToFlatHex(12 + flatCode.length, 4)); // Reserve indices 2-5 later for size
        System.out.println("Size is: " + (12 + flatCode.length) + "");
        byteList.pushBytes(intToFlatHex(maxStack, 2));
        byteList.pushBytes(intToFlatHex(maxLocals, 2));
        byteList.pushBytes(intToFlatHex(flatCode.length, 4));
        byteList.pushBytes(flatCode);
        byteList.pushBytes(0x0, 0x0); // Exception table of size 0
        byteList.pushBytes(0x0, 0x0); // AccessModifier count for this attribute of 0
        bytes = byteList.toBytes();
    }

    @Override
    public String getName() {
        return "Code";
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }
}
