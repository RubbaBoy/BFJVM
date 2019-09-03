package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.CLASS;
import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.STRING;
import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class StringConstant extends Constant {

    private static Logger LOGGER = LoggerFactory.getLogger(StringConstant.class);

    public StringConstant(Utf8Constant utf8Constant) {
        this(utf8Constant.getId());
    }

    public StringConstant(int stringId) {
        super();
        var indexArray = intToFlatHex(stringId, 2);
        bytes = new byte[] {0x08, indexArray[0], indexArray[1]};
    }

    @Override
    public ConstantType getType() {
        return STRING;
    }
}
