package com.uddernetworks.bfjvm.bytecode;

import java.util.ArrayList;
import java.util.List;

public class DefaultClassCreator implements ClassCreator {

    private String name;
    private List<Byte> bytes = new ArrayList<>();

    @Override
    public void setName(String name) {
        this.name = name;
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

        // Size of constant pool
        pushBytes(0x0, 0x5);

        ///////////////////////////////////////// Numerous bytes making up the constant pool

        // Class at index 2
        pushBytes(0x07, 0x0, 0x02);

        // UTF8 10 bytes
        pushBytes(0x01, 0x0, 0x0a);

        // Hello World
        pushBytes(0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x57, 0x6f, 0x72, 0x6c, 0x64);

        pushBytes(0x07, 0x0, 0x04);
        pushBytes(0x01, 0x0, 0x10, 0x6a, 0x61, 0x76, 0x61, 0x2f, 0x6c, 0x61, 0x6e, 0x67, 0x2f, 0x4f, 0x62, 0x6a, 0x65, 0x63, 0x74);

        /////////////////////////////////////////

        // This class' access modifiers (Ie. public)
        pushBytes(0x0, 0x21);

        // Index of this class in constant pool
        pushBytes(0x0, 0x1);

        // Index of this class' super class in constant pool
        pushBytes(0x0, 0x0);

        // Number of interfaces
        pushBytes(0x0, 0x0);

        // Numerous bytes making up interface definitions

        // Number of fields in this class
        pushBytes(0x0, 0x0);

        // Numerous bytes making up field definitions

        // Number of methods in this class
        pushBytes(0x0, 0x0);

        // Numerous bytes making up method definitions

        // Attributes count ( meta data for class file )
        pushBytes(0x0, 0x0);

        // Numerous bytes making up attribute definitions

        return new DefaultBytecodeClass(bytes);
    }
}
