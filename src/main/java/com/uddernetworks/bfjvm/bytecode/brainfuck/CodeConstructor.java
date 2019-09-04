package com.uddernetworks.bfjvm.bytecode.brainfuck;

import com.uddernetworks.bfjvm.bytecode.chunks.constant.FieldrefConstant;

import static com.uddernetworks.bfjvm.bytecode.chunks.methods.Instruction.*;
import static com.uddernetworks.bfjvm.utils.ByteUtils.createByteArray;

public class CodeConstructor {

    private byte[] tapeRef;
    private byte[] indexRef;
    private byte[] outFieldRef;
    private byte[] printlnFieldRef;

    public CodeConstructor(FieldrefConstant tapeRef, FieldrefConstant indexRef, FieldrefConstant outFieldRef, FieldrefConstant printlnFieldRef) {
        this.tapeRef = tapeRef.getId();
        this.indexRef = indexRef.getId();
        this.outFieldRef = outFieldRef.getId();
        this.printlnFieldRef = printlnFieldRef.getId();
    }

    public byte[] add() {
        return createByteArray(
                getstatic, tapeRef,
                getstatic, indexRef,
                dup2,
                baload,
                iconst_1,
                iadd,
                i2b,
                bastore
        );
    }

    public byte[] subtract() {
        return createByteArray(
                getstatic, tapeRef,
                getstatic, indexRef,
                dup2,
                baload,
                iconst_1,
                isub,
                i2b,
                bastore
        );
    }

    public byte[] moveLeft() {
        return createByteArray(
                getstatic, indexRef,
                iconst_1,
                isub,
                putstatic, indexRef
        );
    }

    public byte[] moveRight() {
        return createByteArray(
                getstatic, indexRef,
                iconst_1,
                iadd,
                putstatic, indexRef
        );
    }

    public byte[] print() {
        return createByteArray(
                getstatic, outFieldRef,
                getstatic, tapeRef,
                getstatic, indexRef,
                baload,
                i2c,
                invokevirtual, printlnFieldRef
        );
    }
}
