package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.CLASS;
import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class ClassConstant extends Constant {

    private static Logger LOGGER = LoggerFactory.getLogger(ClassConstant.class);

    public ClassConstant(Utf8Constant utf8Constant) {
        this(utf8Constant.getId());
    }

    public ClassConstant(int utf8Constant) {
        super();
        var indexArray = intToFlatHex(utf8Constant, 2);
        bytes = new byte[] {0x07, indexArray[0], indexArray[1]};
    }

    @Override
    public ConstantType getType() {
        return CLASS;
    }
}
