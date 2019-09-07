package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import com.uddernetworks.bfjvm.utils.ByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.STRING;

public class StringConstant extends Constant {

    private static Logger LOGGER = LoggerFactory.getLogger(StringConstant.class);

    public StringConstant(Utf8Constant utf8Constant) {
        this(utf8Constant.getId());
    }

    /**
     *
     * @param stringId u2
     */
    public StringConstant(byte[] stringId) {
        super();
        bytes = ByteUtils.createByteArray(0x08, stringId);
    }

    @Override
    public ConstantType getType() {
        return STRING;
    }
}
