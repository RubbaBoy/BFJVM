package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.FIELDREF;
import static com.uddernetworks.bfjvm.utils.ByteUtils.createByteArray;
import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class FieldrefConstant extends Constant {

    private static Logger LOGGER = LoggerFactory.getLogger(FieldrefConstant.class);

    public FieldrefConstant(ClassConstant classConstant, NameAndTypeConstant nameAndTypeConstant) {
        this(classConstant.getId(), nameAndTypeConstant.getId());
    }

    public FieldrefConstant(int classId, int nameAndType) {
        super();
        var classArray = intToFlatHex(classId, 2);
        var ntArray = intToFlatHex(nameAndType, 2);
        bytes = createByteArray(0x09, classArray, ntArray);
    }

    @Override
    public ConstantType getType() {
        return FIELDREF;
    }
}
