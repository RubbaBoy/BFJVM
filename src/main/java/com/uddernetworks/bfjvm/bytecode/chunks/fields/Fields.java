package com.uddernetworks.bfjvm.bytecode.chunks.fields;

import com.uddernetworks.bfjvm.bytecode.ByteList;
import com.uddernetworks.bfjvm.bytecode.chunks.BytecodeChunk;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import java.util.ArrayList;
import java.util.List;

public class Fields implements BytecodeChunk {

    private List<Field> fields = new ArrayList<>();

    public void addField(Field field) {
        fields.add(field);
    }

    @Override
    public String getName() {
        return "Fields";
    }

    @Override
    public byte[] getBytes() {
        var byteList = new ByteList();
        byteList.pushBytes(ByteUtils.intToFlatHex(fields.size(), 2));
        fields.forEach(field -> byteList.pushBytes(field.getBytes()));
        return byteList.toBytes();
    }
}
