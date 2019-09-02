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
        //<editor-fold desc="Constant Shit">
        // Java Magic Number
        pushBytes(0xCA, 0xFE, 0xBA, 0xBE);

        // Minor Version
        pushBytes(0x0, 0x0, 0x0);

        // Major Version
        pushByte(0x37); // Java 11
        //</editor-fold>

        //<editor-fold desc="Constant Pool">

        // Size of constant pool
        pushBytes(0x0, 0x1f);

        ///////////////////////////////////////// Numerous bytes making up the constant pool

        // Class at index 2
        pushBytes(0x07, 0x0, 0x02);

        // HelloWorld
        pushBytes(/* UTF8 */ 0x01, 0x0, /* Size, 10 bytes */ 0x0a);
        pushBytes(0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x57, 0x6f, 0x72, 0x6c, 0x64);

        // java/lang/Object
        pushBytes(0x07, 0x0, /* 4 */ 0x04);
        pushBytes(0x01, 0x0, 0x10,   0x6a, 0x61, 0x76, 0x61, 0x2f, 0x6c, 0x61, 0x6e, 0x67, 0x2f, 0x4f, 0x62, 0x6a, 0x65, 0x63, 0x74);

        // java/lang/System
        pushBytes(0x07, 0x0, /* 6 */ 0x06);
        pushBytes(0x01, 0x0, 0x10,   0x6a, 0x61, 0x76, 0x61, 0x2f, 0x6c, 0x61, 0x6e, 0x67, 0x2f, 0x53, 0x79, 0x73, 0x74, 0x65, 0x6d);

        // java/io/PrintStream
        pushBytes(0x07, 0x0, /* 8 */ 0x08);
        pushBytes(0x1, 0x0, 0x13,   0x6a, 0x61, 0x76, 0x61, 0x2f, 0x69, 0x6f, 0x2f, 0x50, 0x72, 0x69, 0x6e, 0x74, 0x53, 0x74, 0x72, 0x65, 0x61, 0x6d);

        // "Hello World"
        pushBytes(/* String constant */ 0x8, 0x0, /* the index of the UTF8 data of string */ 0x0a);
        pushBytes(0x1, 0x0, 0xb,   0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x57, 0x6f, 0x72, 0x6c, 0x64);


        /// .out reference
        pushBytes(0x09, 0x0, 0x05, 0x0, 0x0c); // Field reference, 00 05 refers to the constant pool index of System
        pushBytes(0x0c, 0x0, 0x0d, 0x0, 0x0e); // Name and type reference, 0c is NameAndType, 00 0d is the name
        //                                        (UTF entry at #13, 'out'). 00 0e is the object to get it from (UTF entry at #14, Ljava/io/PrintStream;)
        pushBytes(0x01, 0x0, 0x3,   0x6f, 0x75, 0x74);
        pushBytes(0x01, 0x0, 0x15,  0x4c, 0x6a, 0x61, 0x76, 0x61, 0x2f, 0x69, 0x6f, 0x2f, 0x50, 0x72, // Ljava/io/PrintStream;
                                    0x69, 0x6e, 0x74, 0x53, 0x74, 0x72, 0x65, 0x61, 0x6d, 0x3b);

        // .println reference
        pushBytes(0x0a, 0x0, 0x07, 0x0, 0x10);
        pushBytes(0x0c, 0x0, 0x11, 0x0, 0x12);
        pushBytes(0x01, 0x0, 0x07,   0x70, 0x72, 0x69, 0x6e, 0x74, 0x6c, 0x6e); // println
        pushBytes(0x01, 0x0, 0x15,   0x28, 0x4c, 0x6a, 0x61, 0x76, 0x61, 0x2f, 0x6c, 0x61, 0x6e, 0x67, // (Ljava/lang/String;)V
                                     0x2f, 0x53, 0x74, 0x72, 0x69, 0x6e, 0x67, 0x3b, 0x29, 0x56);

        // Main method
        pushBytes(0x01, 0x0, 0x04,   0x6d, 0x61, 0x69, 0x6e);
        pushBytes(0x01, 0x0, 0x16,   0x28, 0x5b, 0x4c, 0x6a, 0x61, 0x76, 0x61, 0x2f, 0x6c, 0x61, 0x6e, // ([Ljava/lang/String;)V
                                     0x67, 0x2f, 0x53, 0x74, 0x72, 0x69, 0x6e, 0x67, 0x3b, 0x29, 0x56);
        pushBytes(0x01, 0x0, 0x04,   0x43, 0x6f, 0x64, 0x65); // Code

        // "tape"  #22
        pushBytes(/* UTF8 */ 0x01, 0x0, /* Size, 4 bytes */ 0x04);
        pushBytes(0x74, 0x61, 0x70, 0x65);

        // "[B"  #23
        pushBytes(/* UTF8 */ 0x01, 0x0, /* 2 bytes */ 0x02);
        pushBytes(0x5b, 0x42);

        // NamdAndType <tape> <[B>   #24
        pushBytes(/* NameAndType */ 0x0c, /* #22 */ 0x0, 0x16, /* #23 */ 0x0, 0x17);

        // "<clinit>"  #25
        pushBytes(/* UTF8 */ 0x01, 0x0, /* 8 bytes */ 0x08);
        pushBytes(0x3c, 0x63, 0x6c, 0x69, 0x6e, 0x69, 0x74, 0x3e);

        // "()V"  #26
        pushBytes(/* UTF8 */ 0x01, 0x0, /* 3 bytes */ 0x03);
        pushBytes(0x28, 0x29, 0x56);

        // 65536  #27
        pushBytes(/* Integer */ 0x03, /* 65536 */ 0x00, 0x01, 0x00, 0x00);

        // Fieldref com/uddernetworks/bfjvm/HelloWorld tape[B  #28
        pushBytes(/* Fieldref */ 0x09, /* Class Index #29 */ 0x0, 0x1D, /* NameAndType tape[B #24 */ 0x0, 0x18);

        // Class com/uddernetworks/bfjvm/HelloWorld #29
        pushBytes(/* Class */ 0x07, /* Name index #30 */ 0x0, 0x1E);

        // "com/uddernetworks/bfjvm/HelloWorld"  #30
        pushBytes(/* UTF8 */ 0x01, 0x0, /* 34 bytes */ 0x22);
        pushBytes(0x63, 0x6f, 0x6d, 0x2f, 0x75, 0x64, 0x64, 0x65, 0x72, 0x6e, 0x65, 0x74, 0x77, 0x6f, 0x72, 0x6b, 0x73, 0x2f, 0x62, 0x66, 0x6a, 0x76, 0x6d, 0x2f, 0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x57, 0x6f, 0x72, 0x6c, 0x64);

        /////////////////////////////////////////
        //</editor-fold>

        //<editor-fold desc="Class Info">
        // This class' access modifiers (Ie. public)
        pushBytes(0x0, 0x21);

        // Index of this class in constant pool
        pushBytes(0x0, 0x1);

        // Index of this class' super class in constant pool
        pushBytes(0x0, 0x0);
        //</editor-fold>

        //<editor-fold desc="Interfaces">
        // Number of interfaces
        pushBytes(0x0, 0x0);

        // Numerous bytes making up interface definitions
        //</editor-fold>

        //<editor-fold desc="Fields">
        // Number of fields in this class
        pushBytes(0x0, 0x1);

        /////////////// Numerous bytes making up field definitions

        // private static byte[] tape;
        pushBytes(/* private static */ 0x0, 0x0A, /* "tape" index, #22 */ 0x0, 0x16, /* "[B" index, #23 */ 0x0, 0x17, /* attribute count */ 0x0, 0x0);

        ///////////////
        //</editor-fold>

        //<editor-fold desc="Methods">

        // Number of methods in this class
        pushBytes(0x0, 0x02);

        /////////////// Numerous bytes making up method definitions

//        // main
        pushBytes(/* public static */ 0x0, 0x09);
        pushBytes(0x0, 0x13, 0x0, 0x14); // main ([Ljava/lang/String;)
        pushBytes(0x0, 0x01); // attribute size = 1
        pushBytes(0x0, 0x15); // Code Attribute ( this is index #21 in our constant pool )
        pushBytes(0x0, 0x0, 0x0, 0x15); // Code Attribute size of 21 bytes.

        // 21 bytes of code attribute:
        pushBytes(0x0, 0x02, 0x0, 0x1); // Max stack size of 2, and Max local var size of 1
        pushBytes(0x0, 0x0, 0x0, 0x09); // Size of code. 9 bytes

        // The actual machine instructions:
        pushBytes(0xb2, 0x0, 0x0b); // b2 = getstatic, 000b = index #11 in constant pool ( out )
        pushBytes(0x12, 0x09); // 12 = ldc ( load constant ), 09 = index #19 ( Hello World )
        pushBytes(0xb6, 0x0, 0x0f); // b6 = invokevirtual, 000f = index #15 ( method println )
        pushBytes(0xb1); // return void

        pushBytes(0x0, 0x0); // Exception table of size 0
        pushBytes(0x0, 0x0); // Attribute count for this attribute of 0


        // clinit
        pushBytes(/* static */ 0x0, 0x08);
        pushBytes(/* <clinit> */ 0x0, 0x19, /* ()V */ 0x0, 0x1a); // <clinit> ()V
        pushBytes(0x0, 0x01); // attribute size = 1

        pushBytes(0x0, 0x15); // Code Attribute ( this is index #21 in our constant pool )
        pushBytes(0x0, 0x0, 0x0, 0x14); // Code Attribute size of 19 bytes.

        // 21 bytes of code attribute:
        pushBytes(0x0, 0x01, 0x0, 0x0); // Max stack size of 2, and Max local var size of 1
        pushBytes(0x0, 0x0, 0x0, 0x08); // Size of code. 7 bytes

        // The actual machine instructions:
        pushBytes(/* ldc */ 0x12, /* 65536 #27 */ 0x1b);
        pushBytes(/* newarray */ 0xbc, /* byte */ 0x8);
        pushBytes(/* putstatic */ 0xb3, /* Fieldref #28 */ 0x0, 0x1c);
        pushBytes(/* return */ 0xb1);

        pushBytes(0x0, 0x0); // Exception table of size 0
        pushBytes(0x0, 0x0); // Attribute count for this attribute of 0


        ///////////////
        //</editor-fold>

        //<editor-fold desc="Attributes">
        // Attributes count ( meta data for class file )
        pushBytes(0x0, 0x0);

        // Numerous bytes making up attribute definitions
        //</editor-fold>

        return new DefaultBytecodeClass(bytes);
    }
}
