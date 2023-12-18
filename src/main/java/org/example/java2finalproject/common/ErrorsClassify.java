package org.example.java2finalproject.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorsClassify {
//    public static final int other=0;
//    public static final int SyntaxError = 1;
//    public static final int FatalError = 2;
//    public static final int ExceptionError = 3;
    private final String regexSyntaxError =
             "(error:|SyntaxError)\\s*([^\\n]+(?:\\n(?!\\n).+)*)";
    private final String regexFatalError =
            "(OutOfMemoryError|StackOverflowError|NoClassDefFoundError|" +
                    "AssertionError|LinkageError|VirtualMachineError|UnknownError|" +
                    "InternalError|UnsatisfiedLinkError|ExceptionInInitializerError|" +
                    "NoSuchMethodError|NoSuchFieldError|IllegalAccessError|" +
                    "AbstractMethodError|IncompatibleClassChangeError|UnsupportedClassVersionError|" +
                    "ClassFormatError|BootstrapMethodError|ClassCircularityError|" +
                    "ClassCastException|NegativeArraySizeException|ArrayStoreException|" +
                    "IllegalThreadStateException|IllegalMonitorStateException|" +
                    "NumberFormatException|IllegalStateException)";
    private final String regexException =
            " (IOException|SQLException|FileNotFoundException|" +
                    "NullPointerException|ArrayIndexOutOfBoundsException|IllegalArgumentException|" +
                    "NumberFormatException|ClassCastException|ArithmeticException|" +
                    "RuntimeException|IndexOutOfBoundsException|ConcurrentModificationException|" +
                    "UnsupportedOperationException|IllegalStateException|" +
                    "SecurityException|NoSuchElementException|NullPointerException|" +
                    "RuntimeException|IllegalArgumentException|IllegalStateException|" +
                    "IndexOutOfBoundsException)";
    private Pattern patternSyntaxError = Pattern.compile(regexSyntaxError, Pattern.CASE_INSENSITIVE);
    private Pattern patternFatalError = Pattern.compile(regexFatalError, Pattern.CASE_INSENSITIVE);
    private Pattern patternException = Pattern.compile(regexException, Pattern.CASE_INSENSITIVE);
    public Matcher SyntaxErrorMatch(String article){
        return patternSyntaxError.matcher(article);
    }
    public Matcher FatalErrorMatch(String article){
        return patternFatalError.matcher(article);
    }
    public Matcher ExceptionMatch(String article){
        return patternException.matcher(article);
    }

    
}