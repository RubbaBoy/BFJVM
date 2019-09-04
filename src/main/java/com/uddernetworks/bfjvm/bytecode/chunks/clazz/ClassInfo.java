package com.uddernetworks.bfjvm.bytecode.chunks.clazz;

import com.uddernetworks.bfjvm.bytecode.accessmodifier.AccessModifier;
import com.uddernetworks.bfjvm.bytecode.chunks.BytecodeChunk;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.ClassConstant;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;
import static com.uddernetworks.bfjvm.utils.ConstantUtil.idOr0;

public class ClassInfo implements BytecodeChunk {

    private byte[] bytes;

    public ClassInfo(ClassConstant classConstant, ClassConstant superConstant, ClassAccessModifier... accessModifiers) {
        this(idOr0(classConstant), idOr0(superConstant), accessModifiers);
    }

    /**
     *
     * @param classId u2
     * @param superId u2
     * @param accessModifiers
     */
    public ClassInfo(byte[] classId, byte[] superId, ClassAccessModifier... accessModifiers) {
        bytes = ByteUtils.createByteArray(AccessModifier.getByte(accessModifiers), classId, superId);
    }

    @Override
    public String getName() {
        return "ClassInfo";
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }
}
