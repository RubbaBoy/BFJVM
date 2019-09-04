package com.uddernetworks.bfjvm.bytecode;

import com.uddernetworks.bfjvm.bytecode.chunks.clazz.ClassInfo;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantPool;
import com.uddernetworks.bfjvm.bytecode.chunks.fields.Fields;
import com.uddernetworks.bfjvm.bytecode.chunks.interfase.InterfaceInfo;
import com.uddernetworks.bfjvm.bytecode.chunks.methods.Methods;

public interface ClassCreator {

    void setName(String name);

    void setClassInfo(ClassInfo classInfo);

    void setInterfaceInfo(InterfaceInfo interfaceInfo);

    void setFields(Fields fields);

    void setMethods(Methods methods);

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
