package com.uddernetworks.bfjvm.bytecode.chunks.methods;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.Utf8Constant;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import java.util.Arrays;

import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class CodeAttribute implements Attribute {

    private byte[] bytes;

    /**
     * Assumes no catches, and no attributes.
     */
    public CodeAttribute(Utf8Constant smtConstant, Utf8Constant codeConstant, int maxStack, int maxLocals, byte... code) {
        this(smtConstant.getId(), codeConstant.getId(), maxStack, maxLocals, code);
    }

    /**
     *
     * @param codeId u2
     * @param maxStack
     * @param maxLocals
     * @param code
     */
    public CodeAttribute(byte[] smtId, byte[] codeId, int maxStack, int maxLocals, byte... code) {
        var byteList = new ByteList();

        var stackMapTable = new StackMapTable(code);

        byteList.pushBytes(codeId);
        byteList.pushBytes(intToFlatHex(10 + stackMapTable.getBytes().length + code.length, 4)); // Reserve indices 2-5 later for size
        byteList.pushBytes(intToFlatHex(maxStack, 2));
        byteList.pushBytes(intToFlatHex(maxLocals, 2));
        byteList.pushBytes(intToFlatHex(code.length, 4));
        byteList.pushBytes(code);
        byteList.pushBytes(0x0, 0x0); // Exception table of size 0
        byteList.pushBytes(stackMapTable.getBytes());
//        byteList.pushBytes(0x0, 0x0); // Exception table of size 0

//        byteList.pushBytes(/* size of this - 6 */ 0x0, 0x0, 0x0, 0x4, /* count */ 0x0, 0x0, 0xc, 0xe);
//        byteList.pushBytes(/* size of this - 6 */ 0x0, 0x0, 0x0, 0x4, /* count */ 0x0, 0x0, 0xc, 0xe);
//        byteList.pushBytes(0x0e, 0x0c);

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
