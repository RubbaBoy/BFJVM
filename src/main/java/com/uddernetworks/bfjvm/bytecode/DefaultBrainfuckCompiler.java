package com.uddernetworks.bfjvm.bytecode;

import com.uddernetworks.bfjvm.brainfuck.DefaultBrainfuckInterpreter;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.*;

public class DefaultBrainfuckCompiler implements BrainfuckCompiler {

    @Override
    public BytecodeClass compileBrainfuck(String program) {
        var interpreter = new DefaultBrainfuckInterpreter();
        interpreter.readProgram(program);

        var classCreator = new DefaultClassCreator();
        classCreator.setName("Brainfuck");

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

        return classCreator.create();
    }
}
