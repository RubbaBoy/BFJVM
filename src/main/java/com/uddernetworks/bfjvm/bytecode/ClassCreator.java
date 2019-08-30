package com.uddernetworks.bfjvm.bytecode;

public interface ClassCreator {

    void setName(String name);

    void pushByte(byte b);

    void pushByte(int num);

    void pushBytes(byte... bs);

    void pushBytes(int... nums);

    /**
     * Adds proper headers to the class and everything.
     *
     * @return The resulting {@link BytecodeClass}
     */
    BytecodeClass create();

}
