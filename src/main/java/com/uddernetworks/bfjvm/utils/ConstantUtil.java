package com.uddernetworks.bfjvm.utils;

import com.uddernetworks.bfjvm.bytecode.chunks.constant.Constant;

public class ConstantUtil {

    public static byte[] idOr0(Constant constant) {
        return constant == null ? new byte[2] : constant.getId();
    }

}
