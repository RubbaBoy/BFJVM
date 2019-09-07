package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.METHODREF;
import static com.uddernetworks.bfjvm.utils.ByteUtils.createByteArray;

public class MethodrefConstant extends Constant {

    private static Logger LOGGER = LoggerFactory.getLogger(MethodrefConstant.class);

    public MethodrefConstant(ClassConstant classConstant, NameAndTypeConstant nameAndTypeConstant) {
        this(classConstant.getId(), nameAndTypeConstant.getId());
    }

    /**
     *
     * @param classId u2
     * @param nameAndType u2
     */
    public MethodrefConstant(byte[] classId, byte[] nameAndType) {
        super();
        bytes = createByteArray(0xa, classId, nameAndType);
    }

    @Override
    public ConstantType getType() {
        return METHODREF;
    }
}
