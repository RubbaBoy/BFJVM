package com.uddernetworks.bfjvm.bytecode.chunks.constant;

import com.uddernetworks.bfjvm.bytecode.chunks.BytecodeChunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.UTF8;
import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class Constant implements BytecodeChunk {

    private static Logger LOGGER = LoggerFactory.getLogger(Constant.class);

    ConstantType type;
    byte[] bytes;
    int cpId = -1;

    Constant() {
        cpId = ConstantPool.getInstance().addConstant(this);
    }

    public Constant(ConstantType type, byte... bytes) {
        if (type == UTF8) {
            LOGGER.error("Error: UTF8 should be using {}", Utf8Constant.class.getCanonicalName());
            System.exit(0);
        }

        this.type = type;
        this.bytes = bytes;
        if (bytes.length != type.getAdditionalBytes()) {
            LOGGER.error("Additional bytes for {} is {}, which does not match supplied {}", type.name(), type.getAdditionalBytes(), bytes.length);
            System.exit(0);
        }

        cpId = ConstantPool.getInstance().addConstant(this);
    }

    @Override
    public String getName() {
        return "Constant[" + type + "]";
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    public ConstantType getType() {
        return type;
    }

    public int getIntId() {
        if (cpId == -1) cpId = ConstantPool.getInstance().addConstant(this);
        return cpId;
    }

    public byte[] getId() {
        return intToFlatHex(getIntId(), 2);
    }

    public byte[] getId(int length) {
        return intToFlatHex(getIntId(), length);
    }

    public byte[] getUnlimId() {
        return intToFlatHex(getIntId());
    }

    public void setId(int cpId) {
        this.cpId = cpId;
    }
}
