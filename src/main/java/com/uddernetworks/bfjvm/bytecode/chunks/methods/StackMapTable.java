package com.uddernetworks.bfjvm.bytecode.chunks.methods;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.utils.ByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

import static com.uddernetworks.bfjvm.bytecode.chunks.methods.Instruction.*;
import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class StackMapTable implements Attribute {

    private static Logger LOGGER = LoggerFactory.getLogger(StackMapTable.class);

    private byte[] bytes;

    public StackMapTable(FinalizedCode finalizedCode) {
        var code = finalizedCode.getBytes();
        LOGGER.info("LENGTH: {}", code.length);
        var byteList = new ByteList();
        if (code.length == 21) { // clinit
            byteList.pushBytes(0x0, 0x0);
        } else {
            var relativeLocs = new ArrayList<Integer>();
            var lastLoc = 0;
            var locations = finalizedCode.getFrameLocations();
            System.out.println("locations = " + locations);
            var first = true;
            for (var location : locations) {
                var shit = location - lastLoc - (first ? 0 : 1);
                relativeLocs.add(shit);
                if (!first) shit++;
                lastLoc += shit;
                first = false;
            }

            System.out.println("relativeLocs = " + relativeLocs);

            /*
            [14, 24, 37, 40]
            [14, 9, 26, 11]

            14
            9
            26
            11
             */

//            var stuff = Arrays.asList(120, 245 - 121); // 0x78, 0x7d

//            byteList.pushBytes(0x0, 0x0);
//            LOGGER.info("ADDING A LOT");
            byteList.pushBytes(0x0, 0x1);
            byteList.pushBytes(0x0, 0x9); // name index
//            byteList.pushBytes(0x0, 0x0, 0x0, 0x4); // attribute length, minus 6 bytes
            LOGGER.info("shit: {}", intToFlatHex(2 + relativeLocs.size(), 4));
            byteList.pushBytes(intToFlatHex(2 + (relativeLocs.size() * 3), 4)); // attribute length, minus 6 bytes
            byteList.pushBytes(intToFlatHex(relativeLocs.size(), 2)); // number of entries
            relativeLocs.forEach(b -> {
                byteList.pushBytes(0xfb);
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
