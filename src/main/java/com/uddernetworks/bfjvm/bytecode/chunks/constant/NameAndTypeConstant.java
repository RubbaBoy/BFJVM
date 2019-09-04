package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.NAMEANDTYPE;
import static com.uddernetworks.bfjvm.utils.ByteUtils.createByteArray;
import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class NameAndTypeConstant extends Constant {

    private static Logger LOGGER = LoggerFactory.getLogger(NameAndTypeConstant.class);

    /**
     * Descriptor example:
     * <code>
     *     Ljava/io/PrintStream;
     * </code>
     * @param utf8Constant
     * @param descriptorConstant
     */
    public NameAndTypeConstant(Utf8Constant utf8Constant, Utf8Constant descriptorConstant) {
        this(utf8Constant.getId(), descriptorConstant.getId());
    }

    /**
     *
     * @param nameId u2
     * @param descriptorId u2
     */
    public NameAndTypeConstant(byte[] nameId, byte[] descriptorId) {
        super();
        bytes = createByteArray(0x0c, nameId, descriptorId);
    }

    @Override
    public ConstantType getType() {
        return NAMEANDTYPE;
    }
}
