package com.uddernetworks.bfjvm.bytecode;

import java.util.ArrayList;
import java.util.List;

import static com.uddernetworks.bfjvm.utils.ByteUtils.listToArray;

public class ByteList {

    private List<Byte> bytes = new ArrayList<>();

    public void pushByte(byte b) {
        bytes.add(b);
    }

    public void pushByte(int num) {
        bytes.add((byte) num);
    }

    public void pushBytes(byte[] bs) {
        for (byte b : bs) {
            pushByte(b);
        }
    }

    public void pushBytes(int... nums) {
        for (int num : nums) {
            pushByte(num);
        }
    }

    public byte[] toBytes() {
        System.out.println("Size: " + bytes.size());
        return listToArray(bytes);
    }

}
