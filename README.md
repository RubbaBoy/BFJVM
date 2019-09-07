# BFJVM

BFJVM is just like what the name suggests: Brainfuck on the JVM. This project compiles Brainfuck into raw Java bytecode, skipping the step from Brainfuck to Java to Bytecode, and going directly to bytecode.

This project uses no libraries or anything to create the classes, and was meant partially as a mini research project to figure out how the JVM interprets classes internally, and how classes are made. The program outputs in Java 11, and does small optimizations, such as `+= x` instead of just `++` for repeating iterations. Everything is very object-oriented and easily expandable, with the ability for the bytecode-handling piece of the program to be ripped out completely into its own low-level library (Which may be done in the future). Incase you're interested, the main class creating the program is located here: [DefaultBrainfuckCompiler](https://github.com/RubbaBoy/BFJVM/blob/master/src/main/java/com/uddernetworks/bfjvm/bytecode/DefaultBrainfuckCompiler.java).

Looking back at it I never added docs to the API methods other than what size bytes the java spec calls for sometimes. If you want docs and/or a separate API for the bytecode, make an issue.

Usage is:

```
java -jar bfjvm.jar brainfuck.bf
```

Which would output the class to `brainfuck.class`

An example of this in action is:

![BFJVM Example](https://rubbaboy.me/images/0yplyfd)