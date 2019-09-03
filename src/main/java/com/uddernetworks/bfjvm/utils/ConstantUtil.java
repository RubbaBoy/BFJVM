package com.uddernetworks.bfjvm.utils;

import com.uddernetworks.bfjvm.bytecode.chunks.constant.Constant;

public class ConstantUtil {

    public static int idOr0(Constant constant) {
        return constant == null ? 0 : constant.getId();
    }

}
