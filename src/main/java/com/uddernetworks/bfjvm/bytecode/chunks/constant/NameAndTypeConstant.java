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

    public NameAndTypeConstant(int nameId, int descriptorId) {
        super();
        var nameArray = intToFlatHex(nameId, 2);
        var descriptorArray = intToFlatHex(descriptorId, 2);
        bytes = createByteArray(0x0c, nameArray, descriptorArray);
    }

    @Override
    public ConstantType getType() {
        return NAMEANDTYPE;
    }
}
