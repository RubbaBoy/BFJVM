package com.uddernetworks.bfjvm.bytecode.chunks.constant;

public enum ConstantType {
    UTF8(1, 2),
    INTEGER(3, 4),
    FLOAT(4, 4),
    LONG(5, 8),
    DOUBLE(6, 8),
    CLASS(7, 2),
    STRING(8, 2),
    FIELDREF(9, 4),
    METHODREF(10, 4),
    INTERFACEMETHODREF(11, 4),
    NAMEANDTYPE(12, 4),
    METHODHANDLE(15, 3),
    METHODTYPE(16, 2),
    DYNAMIC(17, 4),
    INVOKEDYNAMIC(18, 4),
    MODULE(19, 2),
    PACKAGE(20, 2);

    private int tag;
    private int additionalBytes;

    ConstantType(int tag, int additionalBytes) {
        this.tag = tag;
        this.additionalBytes = additionalBytes;
    }

    public int getTag() {
        return tag;
    }

    public int getAdditionalBytes() {
        return additionalBytes;
    }
}
