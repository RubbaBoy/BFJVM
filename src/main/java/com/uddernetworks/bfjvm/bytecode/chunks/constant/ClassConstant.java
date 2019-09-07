package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.CLASS;
import static com.uddernetworks.bfjvm.utils.ByteUtils.createByteArray;

public class ClassConstant extends Constant {

    private static Logger LOGGER = LoggerFactory.getLogger(ClassConstant.class);

    public ClassConstant(Utf8Constant utf8Constant) {
        this(utf8Constant.getId());
    }

    /**
     *
     * @param utf8Constant u2
     */
    public ClassConstant(byte[] utf8Constant) {
        super();
        bytes = createByteArray(0x07, utf8Constant);
    }

    @Override
    public ConstantType getType() {
        return CLASS;
    }
}
