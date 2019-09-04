package com.uddernetworks.bfjvm.bytecode.chunks.methods;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.bytecode.accessmodifier.AccessModifier;
import com.uddernetworks.bfjvm.bytecode.chunks.BytecodeChunk;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.Utf8Constant;

import java.util.List;

import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class Method implements BytecodeChunk {

    private byte[] bytes;

    public Method(Utf8Constant nameConstant, Utf8Constant descriptorConstant, List<Attribute> attributes, MethodAccessModifier... accessModifiers) {
        this(nameConstant.getId(), descriptorConstant.getId(), attributes, accessModifiers);
    }

    /**
     *
     * @param nameId u2
     * @param descriptorId u2
     * @param attributes
     * @param accessModifiers
     */
    public Method(byte[] nameId, byte[] descriptorId, List<Attribute> attributes, MethodAccessModifier... accessModifiers) {
        var byteList = new ByteList();
        byteList.pushBytes(AccessModifier.getByte(accessModifiers));
        byteList.pushBytes(nameId);
        byteList.pushBytes(descriptorId);
        byteList.pushBytes(intToFlatHex(attributes.size(), 2));
        attributes.forEach(attribute -> byteList.pushBytes(attribute.getBytes()));
        bytes = byteList.toBytes();
    }

    @Override
    public String getName() {
        return "Method";
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }
}
