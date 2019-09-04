package com.uddernetworks.bfjvm.bytecode;

import com.uddernetworks.bfjvm.bytecode.chunks.clazz.ClassInfo;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantPool;
import com.uddernetworks.bfjvm.bytecode.chunks.fields.Fields;
import com.uddernetworks.bfjvm.bytecode.chunks.interfase.InterfaceInfo;
import com.uddernetworks.bfjvm.bytecode.chunks.methods.Methods;

import java.util.ArrayList;
import java.util.List;

public class DefaultClassCreator implements ClassCreator {

    private String name;
    private List<Byte> bytes = new ArrayList<>();
    private ClassInfo classInfo;
    private InterfaceInfo interfaceInfo;
    private Fields fields;
    private Methods methods;

    public DefaultClassCreator() {

    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    @Override
    public void setInterfaceInfo(InterfaceInfo interfaceInfo) {
        this.interfaceInfo = interfaceInfo;
    }

    @Override
    public void setFields(Fields fields) {
        this.fields = fields;
    }

    @Override
    public void setMethods(Methods methods) {
        this.methods = methods;
    }

    @Override
    public void pushByte(byte b) {
        bytes.add(b);
    }

    @Override
    public void pushByte(int num) {
        bytes.add((byte) num);
    }

    @Override
    public void pushBytes(byte[] bs) {
        for (byte b : bs) {
            pushByte(b);
        }
    }

    @Override
    public void pushBytes(int... nums) {
        for (int num : nums) {
            pushByte(num);
        }
    }

    @Override
    public BytecodeClass create() {
        // Java Magic Number
        pushBytes(0xCA, 0xFE, 0xBA, 0xBE);

        // Minor Version
        pushBytes(0x0, 0x0, 0x0);

        // Major Version
        pushByte(0x37); // Java 11

        pushBytes(ConstantPool.getInstance().getBytes());
        pushBytes(classInfo.getBytes());
        pushBytes(interfaceInfo.getBytes());
        pushBytes(fields.getBytes());
        pushBytes(methods.getBytes());

        // Attributes count
        pushBytes(0x0, 0x0);

        return new DefaultBytecodeClass(bytes);
    }
}
