package com.uddernetworks.bfjvm.bytecode.chunks.methods;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class IndexAwareCode {

    public static FinalizedCode processCode(Object... code) {
        var byteIndices = new HashMap<String, Integer>();
        var frameLocations = new ArrayList<Integer>();
        var index = 0;
        var stringedBytes = ByteUtils.createByteArrayIgnoreStrings(code);
        for (var strOrByte : stringedBytes) {
            if (strOrByte instanceof String) {
                var getSet = ((String) strOrByte).split("\\|");
                if (getSet[0].equals("frame")) {
                    frameLocations.add(index);
                } else if (getSet[0].equals("set")) {
                    byteIndices.put(getSet[1], index + Integer.parseInt(getSet[2]));
                } else {
                    index += 2;
                }
            } else if (strOrByte instanceof Byte) {
                index++;
            }
        }

        index = 0;
        var byteList = new ByteList();
        for (var strOrByte : stringedBytes) {
            if (strOrByte instanceof String) {
                var getSet = ((String) strOrByte).split("\\|");
                if (getSet[0].equals("get")) {
                    var name = getSet[1];
                    if (!byteIndices.containsKey(name)) throw new RuntimeException("Index of name " + name + " has not been stored yet at index " + index);
                    byteList.pushBytes(calculateOffset(byteIndices.get(name) - index + 1));
                    index += 2;
                }
            } else {
                byteList.pushByte((Byte) strOrByte);
                index++;
            }
        }

        return new FinalizedCode(byteList.toBytes(), List.copyOf(frameLocations));
    }

    private static byte[] calculateOffset(int signedInt) {
        if (signedInt > 0) {
            return intToFlatHex(signedInt, 2);
        } else {
            return flatten(0xffff - signedInt);
        }
    }

    private static byte[] flatten(int num) {
        return new byte[] {(byte) (0xff - ((num >> 8) & 0xFF)), (byte) (0xff - (num & 0xFF))};
    }

    public static String set(String name) {
        return set(name, 0);
    }

    public static String set(String name, int offset) {
        return "set|" + name + "|" + offset;
    }

    public static String get(String name) {
        return "get|" + name;
    }

}
