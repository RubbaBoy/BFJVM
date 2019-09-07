package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.FIELDREF;
import static com.uddernetworks.bfjvm.utils.ByteUtils.createByteArray;

public class FieldrefConstant extends Constant {

    private static Logger LOGGER = LoggerFactory.getLogger(FieldrefConstant.class);

    public FieldrefConstant(ClassConstant classConstant, NameAndTypeConstant nameAndTypeConstant) {
        this(classConstant.getId(), nameAndTypeConstant.getId());
    }

    /**
     *
     * @param classId u2
     * @param nameAndType u2
     */
    public FieldrefConstant(byte[] classId, byte[] nameAndType) {
        super();
        bytes = createByteArray(0x09, classId, nameAndType);
    }

    @Override
    public ConstantType getType() {
        return FIELDREF;
    }
}
