package com.uddernetworks.bfjvm.bytecode.chunks.fields;

import com.uddernetworks.bfjvm.bytecode.accessmodifier.AccessModifier;
import com.uddernetworks.bfjvm.bytecode.chunks.BytecodeChunk;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.Utf8Constant;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import static com.uddernetworks.bfjvm.utils.ConstantUtil.idOr0;

public class Field implements BytecodeChunk {

    private byte[] bytes;

    /**
     * Attributes not supported, because I'm too lazy.
     */
    public Field(Utf8Constant nameConstant, Utf8Constant descriptionConstant, FieldAccessModifier... accessModifiers) {
        this(idOr0(nameConstant), idOr0(descriptionConstant), accessModifiers);
    }

    /**
     * Attributes not supported, because I'm too lazy.
     *
     * @param nameId u2
     * @param descriptionId u2
     */
    public Field(byte[] nameId, byte[] descriptionId, FieldAccessModifier... accessModifiers) {
        bytes = ByteUtils.createByteArray(AccessModifier.getByte(accessModifiers), nameId, descriptionId, 0x0, 0x0);
    }

    @Override
    public String getName() {
        return "Field";
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }
}
