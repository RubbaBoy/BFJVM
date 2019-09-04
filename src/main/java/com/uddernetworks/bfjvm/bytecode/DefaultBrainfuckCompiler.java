package com.uddernetworks.bfjvm.bytecode;

import com.uddernetworks.bfjvm.brainfuck.DefaultBrainfuckInterpreter;
import com.uddernetworks.bfjvm.bytecode.brainfuck.CodeConstructor;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

import static com.uddernetworks.bfjvm.utils.ByteUtils.intToFlatHex;
import static com.uddernetworks.bfjvm.bytecode.chunks.methods.Instruction.*;

public class DefaultBrainfuckCompiler implements BrainfuckCompiler {

    private static Logger LOGGER = LoggerFactory.getLogger(DefaultBrainfuckCompiler.class);

    @Override
    public BytecodeClass compileBrainfuck(String program) {
        var interpreter = new DefaultBrainfuckInterpreter();
        interpreter.readProgram(program);

        var classCreator = new DefaultClassCreator();
        classCreator.setName("Brainfuck");

        //// Constant Pool
        var thisClass = new ClassConstant(new Utf8Constant("Brainfuck"));

        var objectClass = new ClassConstant(new Utf8Constant("java/lang/Object"));
        var systemClass = new ClassConstant(new Utf8Constant("java/lang/System"));
        var printStreamClass = new ClassConstant(new Utf8Constant("java/io/PrintStream"));
        var helloWorldSpaceUtf = new Utf8Constant("Hello World");
        var helloWorldSpaceString = new StringConstant(helloWorldSpaceUtf);

        var outFieldReference = new FieldrefConstant(systemClass, new NameAndTypeConstant(new Utf8Constant("out"), new Utf8Constant("Ljava/io/PrintStream;")));
        var printlnFieldReference = new FieldrefConstant(printStreamClass, new NameAndTypeConstant(new Utf8Constant("println"), new Utf8Constant("(Ljava/lang/String;)V")));

        var helloWorldClass = new ClassConstant(new Utf8Constant("com/uddernetworks/bfjvm/HelloWorld"));

        // main(String[])
        var mainUtf = new Utf8Constant("main");
        var stringArrayUtf = new Utf8Constant("([Ljava/lang/String;)V");
        var codeUtf = new Utf8Constant("Code");

        // byte[] tape
        var tapeUtf = new Utf8Constant("tape");
        var byteArrayUtf = new Utf8Constant("[B");
        var tapeByteArrayNAT = new NameAndTypeConstant(tapeUtf, byteArrayUtf);
        var tapeRef = new FieldrefConstant(helloWorldClass, tapeByteArrayNAT);

        // int index
        var indexUtf = new Utf8Constant("index");
        var intUtf = new Utf8Constant("I");
        var indexNAT = new NameAndTypeConstant(indexUtf, intUtf);
        var indexRef = new FieldrefConstant(helloWorldClass, indexNAT);

        // Scanner scanner
        var scannerUtf = new Utf8Constant("scanner");
        var scannerObjUtf = new Utf8Constant("java/util/Scanner");
        var scannerClass = new ClassConstant(scannerObjUtf);
        var scannerNAT = new NameAndTypeConstant(scannerUtf, scannerObjUtf);
        var scannerRef = new FieldrefConstant(helloWorldClass, scannerNAT);
        var initScannerMethod = new MethodrefConstant(scannerClass, new NameAndTypeConstant(new Utf8Constant("<init>"), new Utf8Constant("(Ljava/io/InputStream;)V")));



        // System.in
        var inFieldReference = new FieldrefConstant(systemClass, new NameAndTypeConstant(new Utf8Constant("in"), new Utf8Constant("Ljava/io/PrintStream;")));


        // <clinit>
        var clinitUtf = new Utf8Constant("<clinit>");
        var voidMethodUtf = new Utf8Constant("()V");

        var num65536 = new IntegerConstant(65536);

        var codeConstructor = new CodeConstructor(tapeRef, indexRef, outFieldReference, printlnFieldReference);

        //// Class Info
        classCreator.setClassInfo(new ClassInfo(thisClass, null, ClassAccessModifier.PUBLIC));

        //// Interface Info
        classCreator.setInterfaceInfo(new InterfaceInfo());

        //// Fields
        var fields = new Fields();
        classCreator.setFields(fields);

        fields.addField(new Field(tapeUtf, byteArrayUtf, FieldAccessModifier.PRIVATE, FieldAccessModifier.STATIC));
        fields.addField(new Field(indexUtf, intUtf, FieldAccessModifier.PRIVATE, FieldAccessModifier.STATIC));
        fields.addField(new Field(scannerUtf, scannerObjUtf, FieldAccessModifier.PRIVATE, FieldAccessModifier.STATIC));

        //// Methods
        var methods = new Methods();
        classCreator.setMethods(methods);

        var bfCode = new ArrayList<>();
        while (interpreter.hasNext()) {
            var token = interpreter.nextToken();
            switch (token) {
                case ADD:
                    bfCode.add(codeConstructor.add());
                    break;
                case SUB:
                    bfCode.add(codeConstructor.subtract());
                    break;
                case LEFT:
                    bfCode.add(codeConstructor.moveLeft());
                    break;
                case RIGHT:
                    bfCode.add(codeConstructor.moveRight());
                    break;
                case PRINT:
                    bfCode.add(codeConstructor.print());
                    break;
                case INPUT:
                    break;
                case LLOOP:
                    break;
                case RLOOP:
                    break;
            }
        }

        // main
        methods.addMethod(new Method(mainUtf, stringArrayUtf, Arrays.asList(
                new CodeAttribute(codeUtf, 2, 1,
                        // System.out.println()
                        getstatic, outFieldReference.getId(),
                        ldc, helloWorldSpaceString.getUnlimId(),
                        invokevirtual, printlnFieldReference.getId(),
//                        bfCode.toArray(Object[]::new),



                        _return
                )
        ), MethodAccessModifier.PUBLIC, MethodAccessModifier.STATIC));

        // clinit
        methods.addMethod(new Method(clinitUtf, voidMethodUtf, Arrays.asList(
                new CodeAttribute(codeUtf, 1, 0,
                        // Setting tape to new byte[65536]
                        ldc, num65536.getUnlimId(),
                        newarray, 0x8,
                        putstatic, tapeRef.getId(),

                        _new, scannerClass.getId(),
                        dup,
                        getstatic, inFieldReference.getId(),
                        invokespecial, initScannerMethod.getId(),
                        putstatic, scannerRef.getId(),

                        _return
                )
        ), MethodAccessModifier.STATIC));

        return classCreator.create();
    }
}
