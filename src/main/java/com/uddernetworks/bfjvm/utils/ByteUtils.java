package com.uddernetworks.bfjvm.utils;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ByteUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(ByteUtils.class);

    /**
     * Converts something like af71bc to [af, 71, bc]
     * @param input
     * @return
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input);
        } catch (DecoderException e) {
            LOGGER.error("Error decoding hex " + input, e);
            return new byte[0];
        }
    }

    /**
     * Converts a normal string to something like af71bc
     *
     * @param input
     * @return
     */
    public static String toHex(String input) {
        return String.format("%x", new BigInteger(1, input.getBytes()));
    }

    /**
     * Converts something like 15 to f
     *
     * @param num
     * @return
     */
    public static String intToHex(int num) {
        return Integer.toHexString(num);
    }

    public static String intToHex(int num, boolean even) {
        var res =  Integer.toHexString(num);
        if (!even) return res;
        if (res.length() % 2 == 0) return res;
        return "0" + res;
    }

    /**
     * Converts something like 256 to [01, 00]
     *
     * @param num
     * @return
     */
    public static byte[] intToFlatHex(int num) {
        return decodeHex(intToHex(num, true));
    }

    public static byte[] intToFlatHex(int num, int length) {
        var odd = intToHex(num, true);
        if (odd.length() == 1) odd = "0" + odd;
        var array = decodeHex(odd);
        if (array.length == length) return array;
        var res = new byte[length];
        if (array.length > length) {
            System.arraycopy(array, 0, res, 0, length);
        } else {
            var leftEmpty = length - array.length;
            System.arraycopy(array, 0, res, leftEmpty, array.length);
        }
        return res;
    }

    /**
     * Flat maps either bytes or byte[]
     *
     * @param objects
     * @return
     */
    public static byte[] createByteArray(Object... objects) {
        var byteArray = Arrays.stream(objects).collect(Collectors.toList());
        var byteList = new ByteList();
        byteArray.forEach(bytes -> {
            if (!bytes.getClass().isArray()) {
                if (bytes instanceof Integer) {
                    byteList.pushByte(((Integer) bytes).byteValue());
                } else {
                    byteList.pushByte((byte) bytes);
                }
            } else {
                if (bytes instanceof Integer[]) {
                    for (var i : (Integer[]) bytes) {
                        byteList.pushByte(i.byteValue());
                    }
                } else if (bytes instanceof Byte[]) {
                    for (var b : (Byte[]) bytes) {
                        byteList.pushByte(b);
                    }
                } else if (bytes instanceof Object[]) {
                    for (var obj : ((Object[]) bytes)) {
                        byteList.pushBytes(createByteArray(obj));
                    }
                } else {
                    byteList.pushBytes((byte[]) bytes);
                }
            }
        });
        return byteList.toBytes();
    }

    public static byte[] listToArray(List<Byte> byteList) {
        var byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }
        return byteArray;
    }

}
