package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.INTEGER;
import static com.uddernetworks.bfjvm.utils.ByteUtils.createByteArray;
import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class IntegerConstant extends Constant {

    private static Logger LOGGER = LoggerFactory.getLogger(IntegerConstant.class);

    public IntegerConstant(int value) {
        super();
        var indexArray = intToFlatHex(value, 4);
        bytes = createByteArray(0x03, indexArray);
    }

    @Override
    public ConstantType getType() {
        return INTEGER;
    }
}
