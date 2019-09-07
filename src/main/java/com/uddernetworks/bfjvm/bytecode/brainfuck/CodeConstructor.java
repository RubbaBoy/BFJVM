package com.uddernetworks.bfjvm.bytecode.brainfuck;

import com.uddernetworks.bfjvm.bytecode.chunks.constant.FieldrefConstant;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.MethodrefConstant;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.StringConstant;

import static com.uddernetworks.bfjvm.bytecode.chunks.methods.IndexAwareCode.get;
import static com.uddernetworks.bfjvm.bytecode.chunks.methods.IndexAwareCode.set;
import static com.uddernetworks.bfjvm.bytecode.chunks.methods.Instruction.*;
import static com.uddernetworks.bfjvm.utils.ByteUtils.createByteArray;

public class CodeConstructor {

    private byte[] tapeRef;
    private byte[] indexRef;
    private byte[] outFieldRef;
    private byte[] printlnFieldRef;
    private byte[] scannerRef;
    private byte[] periodString; // unlim
    private byte[] findInLineMethod;
    private byte[] charAtMethod;

    public CodeConstructor(FieldrefConstant tapeRef, FieldrefConstant indexRef, FieldrefConstant outFieldRef, MethodrefConstant printlnFieldRef, FieldrefConstant scannerRef, StringConstant periodString, MethodrefConstant findInLineMethod, MethodrefConstant charAtMethod) {
        this.tapeRef = tapeRef.getId();
        this.indexRef = indexRef.getId();
        this.outFieldRef = outFieldRef.getId();
        this.printlnFieldRef = printlnFieldRef.getId();
        this.scannerRef = scannerRef.getId();
        this.periodString = periodString.getUnlimId();
        this.findInLineMethod = findInLineMethod.getId();
        this.charAtMethod = charAtMethod.getId();
    }

    private byte[] loadNumber(int number) {
        if (number <= 5) {
            return new byte[] {(byte) (0x03 + number)}; // iconst_X
        } else {
           return new byte[] {bipush, (byte) number};
        }
    }

    public byte[] add(int data) {
        return createByteArray(
                getstatic, tapeRef,
                getstatic, indexRef,
                dup2,
                baload,
                loadNumber(data),
                iadd,
                i2b,
                bastore
        );
    }

    public byte[] subtract(int data) {
        return createByteArray(
                getstatic, tapeRef,
                getstatic, indexRef,
                dup2,
                baload,
                loadNumber(data),
                isub,
                i2b,
                bastore
        );
    }

    public byte[] moveLeft(int data) {
        return createByteArray(
                getstatic, indexRef,
                loadNumber(data),
                isub,
                putstatic, indexRef
        );
    }

    public byte[] moveRight(int data) {
        return createByteArray(
                getstatic, indexRef,
                loadNumber(data),
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

    public byte[] input() {
        return createByteArray(
                getstatic, tapeRef,
                getstatic, indexRef,
                getstatic, scannerRef,
                ldc, periodString,
                invokevirtual, findInLineMethod,
                iconst_0,
                invokevirtual, charAtMethod,
                i2b,
                bastore
        );
    }

    public Object[] leftLoop(int id) {
        return new Object[]{
                "frame",
                set("ls" + id),
                getstatic, tapeRef,
                getstatic, indexRef,
                baload,
                ifle, get("le" + id)
        };
    }

    public Object[] rightLoop(int id) {
        return new Object[]{
                _goto, get("ls" + id),
                "frame",
                set("le" + id),
        };
    }
}
