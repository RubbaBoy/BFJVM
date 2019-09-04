package com.uddernetworks.bfjvm.bytecode;

import com.uddernetworks.bfjvm.brainfuck.DefaultBrainfuckInterpreter;
import com.uddernetworks.bfjvm.bytecode.chunks.clazz.ClassAccessModifier;
import com.uddernetworks.bfjvm.bytecode.chunks.clazz.ClassInfo;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.*;
import com.uddernetworks.bfjvm.bytecode.chunks.fields.Field;
import com.uddernetworks.bfjvm.bytecode.chunks.fields.FieldAccessModifier;
import com.uddernetworks.bfjvm.bytecode.chunks.fields.Fields;
import com.uddernetworks.bfjvm.bytecode.chunks.interfase.InterfaceInfo;
import com.uddernetworks.bfjvm.bytecode.chunks.methods.CodeAttribute;
import com.uddernetworks.bfjvm.bytecode.chunks.methods.Method;
import com.uddernetworks.bfjvm.bytecode.chunks.methods.MethodAccessModifier;
import com.uddernetworks.bfjvm.bytecode.chunks.methods.Methods;
import com.uddernetworks.bfjvm.utils.ByteUtils;

import java.util.Arrays;

import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;

public class DefaultBrainfuckCompiler implements BrainfuckCompiler {

    @Override
    public BytecodeClass compileBrainfuck(String program) {
        var interpreter = new DefaultBrainfuckInterpreter();
        interpreter.readProgram(program);

        var classCreator = new DefaultClassCreator();
        classCreator.setName("Brainfuck");

        //// Constant Pool
        var thisClass = new ClassConstant(new Utf8Constant("HelloWorld"));

        var objectClass = new ClassConstant(new Utf8Constant("java/lang/Object"));
        var systemClass = new ClassConstant(new Utf8Constant("java/lang/System"));
        var printStreamClass = new ClassConstant(new Utf8Constant("java/io/PrintStream"));
        var helloWorldSpaceUtf = new Utf8Constant("Hello World");
        var helloWorldSpaceString = new StringConstant(helloWorldSpaceUtf);

        var outFieldReference = new FieldrefConstant(systemClass, new NameAndTypeConstant(new Utf8Constant("out"), new Utf8Constant("Ljava/io/PrintStream;")));

        var printlnFieldReference = new FieldrefConstant(printStreamClass, new NameAndTypeConstant(new Utf8Constant("println"), new Utf8Constant("(Ljava/lang/String;)V")));

        var mainUtf = new Utf8Constant("main");
        var stringArrayUtf = new Utf8Constant("([Ljava/lang/String;)V");
        var codeUtf = new Utf8Constant("Code");
        var tapeUtf = new Utf8Constant("tape");
        var byteArrayUtf = new Utf8Constant("[B");

        var tapeByteArrayNAT = new NameAndTypeConstant(tapeUtf, byteArrayUtf);
        var clinitUtf = new Utf8Constant("<clinit>");
        var voidMethodUtf = new Utf8Constant("()V");
        var num65536 = new IntegerConstant(65536);

        var helloWorldClass = new ClassConstant(new Utf8Constant("com/uddernetworks/bfjvm/HelloWorld"));
        var fieldRefTape = new FieldrefConstant(helloWorldClass, tapeByteArrayNAT);


        //// Class Info
        classCreator.setClassInfo(new ClassInfo(thisClass, null, ClassAccessModifier.PUBLIC));

        //// Interface Info
        classCreator.setInterfaceInfo(new InterfaceInfo());

        //// Fields
        var fields = new Fields();
        classCreator.setFields(fields);

        fields.addField(new Field(tapeUtf, byteArrayUtf, FieldAccessModifier.PRIVATE, FieldAccessModifier.STATIC));

        //// Methods
        var methods = new Methods();
        classCreator.setMethods(methods);

        methods.addMethod(new Method(mainUtf, stringArrayUtf, Arrays.asList(
                new CodeAttribute(codeUtf, 2, 1,
                        0xb2, 0x0, outFieldReference.getUnlimId(),
                        0x12, helloWorldSpaceString.getUnlimId(),
                        0xb6, 0x0, printlnFieldReference.getUnlimId(),
                        0xb1
                )
        ), MethodAccessModifier.PUBLIC, MethodAccessModifier.STATIC));

        return classCreator.create();
    }
}
