package com.uddernetworks.bfjvm.bytecode.chunks.methods;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.Utf8Constant;

import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class CodeAttribute implements Attribute {

    private byte[] bytes;

    /**
     * Assumes no catches, and no attributes.
     */
    public CodeAttribute(Utf8Constant smtConstant, Utf8Constant codeConstant, int maxStack, int maxLocals, FinalizedCode code) {
        this(smtConstant.getId(), codeConstant.getId(), maxStack, maxLocals, code);
    }

    /**
     *
     * @param codeId u2
     * @param maxStack
     * @param maxLocals
     * @param code
     */
    public CodeAttribute(byte[] smtId, byte[] codeId, int maxStack, int maxLocals, FinalizedCode code) {
        var byteList = new ByteList();

        var stackMapTable = new StackMapTable(code, smtId);

        byteList.pushBytes(codeId);
        byteList.pushBytes(intToFlatHex(10 + stackMapTable.getBytes().length + code.getBytes().length, 4)); // Reserve indices 2-5 later for size
        byteList.pushBytes(intToFlatHex(maxStack, 2));
        byteList.pushBytes(intToFlatHex(maxLocals, 2));
        byteList.pushBytes(intToFlatHex(code.getBytes().length, 4));
        byteList.pushBytes(code.getBytes());
        byteList.pushBytes(0x0, 0x0); // Exception table of size 0
        byteList.pushBytes(stackMapTable.getBytes());
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
