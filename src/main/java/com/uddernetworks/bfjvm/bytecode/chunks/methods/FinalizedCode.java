package com.uddernetworks.bfjvm.bytecode.chunks.methods;

import java.util.List;

public class FinalizedCode {

    private byte[] bytes;
    private List<Integer> frameLocations;

    public FinalizedCode(byte[] bytes, List<Integer> frameLocations) {
        this.bytes = bytes;
        this.frameLocations = frameLocations;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public List<Integer> getFrameLocations() {
        return frameLocations;
    }
}
