package com.uddernetworks.bfjvm.bytecode.chunks.methods;

import com.uddernetworks.bfjvm.bytecode.ByteList;

import java.util.ArrayList;

import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class StackMapTable implements Attribute {

    private byte[] bytes;

    public StackMapTable(FinalizedCode finalizedCode, byte[] smtId) {
        var byteList = new ByteList();

        var relativeLocs = new ArrayList<Integer>();
        var lastLoc = 0;
        var locations = finalizedCode.getFrameLocations();
        for (var location : locations) {
            var temp = location - lastLoc - (lastLoc == 0 ? 0 : 1);
            relativeLocs.add(temp);
            lastLoc += temp + (lastLoc > 0 ? 1 : 0);
        }

        if (relativeLocs.isEmpty()) { // clinit
            byteList.pushBytes(0x0, 0x0);
        } else {
            byteList.pushBytes(0x0, 0x1); // 1 attribute (This one)
            byteList.pushBytes(smtId); // name index
            byteList.pushBytes(intToFlatHex(2 + (relativeLocs.size() * 3), 4)); // attribute length, minus 6 bytes
            byteList.pushBytes(intToFlatHex(relativeLocs.size(), 2)); // number of entries
            relativeLocs.forEach(b -> {
                byteList.pushBytes(0xfb); // same_frame_extended
                byteList.pushBytes(intToFlatHex(b, 2));
            });
        }

        bytes = byteList.toBytes();
    }

    @Override
    public String getName() {
        return "StackMapTable";
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }
}
