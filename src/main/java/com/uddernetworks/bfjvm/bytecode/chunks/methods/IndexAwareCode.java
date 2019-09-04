package com.uddernetworks.bfjvm.bytecode.chunks.methods;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.utils.ByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.uddernetworks.bfjvm.utils.ByteUtils.createByteArray;
import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class IndexAwareCode {

    private static Logger LOGGER = LoggerFactory.getLogger(IndexAwareCode.class);

    public static byte[] processCode(Object... code) {
        var byteIndices = new HashMap<String, Integer>();
        var index = 0;
        var stringedBytes = ByteUtils.createByteArrayIgnoreStrings(code);
        for (var strOrByte : stringedBytes) {
            if (strOrByte instanceof String) {
                var getSet = ((String) strOrByte).split("\\|", 2);
                var name = getSet[1];
                if (getSet[0].equals("set")) {
                    byteIndices.put(name, index);
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
                var getSet = ((String) strOrByte).split("\\|", 2);
                var name = getSet[1];
                if (getSet[0].equals("get")) {
                    if (!byteIndices.containsKey(name)) throw new RuntimeException("Index of name " + name + " has not been stored yet at index " + index);
                    byteList.pushBytes(calculateOffset(byteIndices.get(name) - index));
                    index++;
                }
            } else {
                byteList.pushByte((Byte) strOrByte);
                index++;
            }
        }

        return byteList.toBytes();
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
        return "set|" + name;
    }

    public static String get(String name) {
        return "get|" + name;
    }

}
