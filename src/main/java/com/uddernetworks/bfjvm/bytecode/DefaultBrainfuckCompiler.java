package com.uddernetworks.bfjvm.bytecode;

import com.uddernetworks.bfjvm.brainfuck.DefaultBrainfuckInterpreter;
import com.uddernetworks.bfjvm.bytecode.chunks.constant.*;

import static com.uddernetworks.bfjvm.bytecode.chunks.constant.ConstantType.*;

public class DefaultBrainfuckCompiler implements BrainfuckCompiler {

    @Override
    public BytecodeClass compileBrainfuck(String program) {
        var interpreter = new DefaultBrainfuckInterpreter();
        interpreter.readProgram(program);

        var classCreator = new DefaultClassCreator();
        classCreator.setName("Brainfuck");

        var helloWorldUtf = new Utf8Constant("HelloWorld");
        var objectClass = new ClassConstant(new Utf8Constant("java/lang/Object"));
        var systemClass = new ClassConstant(new Utf8Constant("java/lang/System"));
        var printStreamClass = new ClassConstant(new Utf8Constant("java/io/PrintStream"));
        var helloWorldSpaceUtf = new Utf8Constant("Hello World");
        var helloWorldSpaceString = new StringConstant(helloWorldSpaceUtf);

        var outFieldReference = new FieldrefConstant(systemClass, new NameAndTypeConstant(new Utf8Constant("out"), new StringConstant(new Utf8Constant("Ljava/io/PrintStream;"))));

        var printlnFieldReference = new FieldrefConstant(printStreamClass, new NameAndTypeConstant(new Utf8Constant("println"), new StringConstant(new Utf8Constant("(Ljava/lang/String;)V"))));



        return classCreator.create();
    }
}
