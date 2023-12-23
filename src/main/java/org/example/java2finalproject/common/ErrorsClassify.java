package org.example.java2finalproject.common;

import org.example.java2finalproject.entity.CommentData;
import org.example.java2finalproject.entity.QuestionData;
import org.example.java2finalproject.entity.AnswerData;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorsClassify {
//    public static final int other=0;
//    public static final int SyntaxError = 1;
//    public static final int FatalError = 2;
//    public static final int ExceptionError = 3;
    public static String SyntaxErrorName = "SyntaxError";
    public static String FatalErrorName = "FatalError";
    public static String ExceptionErrorName = "ExceptionError";
    public static String ErrorName="Error";
    public static String ExceptionName="Exception";
    private static final String regexSyntaxError =
             "(error:|SyntaxError)\\s*([^\\n]+(?:\\n(?!\\n).+)*)";
    private static final String regexFatalError =
            "(OutOfMemoryError|StackOverflowError|NoClassDefFoundError|" +
                    "AssertionError|LinkageError|VirtualMachineError|UnknownError|" +
                    "InternalError|UnsatisfiedLinkError|ExceptionInInitializerError|" +
                    "NoSuchMethodError|NoSuchFieldError|IllegalAccessError|" +
                    "AbstractMethodError|IncompatibleClassChangeError|UnsupportedClassVersionError|" +
                    "ClassFormatError|BootstrapMethodError|ClassCircularityError" +
                    "|FieldErrors|ResponseError)";
    public static final String[] FatalErrorArray=new String[]{
            "OutOfMemoryError",
            "StackOverflowError",
            "NoClassDefFoundError",
            "AssertionError",
            "LinkageError",
            "VirtualMachineError",
            "UnknownError",
            "InternalError",
            "UnsatisfiedLinkError",
            "ExceptionInInitializerError",
            "NoSuchMethodError",
            "NoSuchFieldError",
            "IllegalAccessError",
            "AbstractMethodError",
            "IncompatibleClassChangeError",
            "UnsupportedClassVersionError",
            "ClassFormatError",
            "BootstrapMethodError",
            "ClassCircularityError",
            "FieldErrors",
            "ResponseError"
    };
    //filewriteexception", "```exception", "nosuchpaddingexception,"
    //responsestatusexception(\n", "saxexception,", "(datetimeexception", "parseexception",
    private static final String regexException =
            " (IOException|SQLException|PSQLException|FileNotFoundException|" +
                    "NullPointerException|ArrayIndexOutOfBoundsException|" +
                    "ClassCastException|ArithmeticException|" +
                    "RuntimeException|IndexOutOfBoundsException|ConcurrentModificationException|" +
                    "UnsupportedOperationException|" +
                    "SecurityException|NoSuchElementException|IllegalArgumentException|" +
                    "NegativeArraySizeException|ArrayStoreException|" +
                    "IllegalThreadStateException|IllegalMonitorStateException|" +
                    "NumberFormatException|IllegalStateException|ClientNotFoundException|" +
                    "AuthenticationException|ExecutionException|ImageCaptureException|" +
                    "FileWriteException|NoSuchPaddingException|PrinterException|TimeoutException|" +
                    "ResponseStatusException|ParseException|TechniqueException|JavascriptException|InvalidKeyspecException)";
    public static final String[]ExceptionArray=new String[]{
            "IOException",
            "SQLException",
            "PSQLException",
            "FileNotFoundException",
            "NullPointerException",
            "ArrayIndexOutOfBoundsException",
            "ClassCastException",
            "ArithmeticException",
            "RuntimeException",
            "IndexOutOfBoundsException",
            "ConcurrentModificationException",
            "UnsupportedOperationException",
            "SecurityException",
            "NoSuchElementException",
            "IllegalArgumentException",
            "NegativeArraySizeException",
            "ArrayStoreException",
            "IllegalThreadStateException",
            "ClientNotFoundException",
            "AuthenticationException",
            "ExecutionException",
            "ImageCaptureException",
            "FileWriteException",
            "NoSuchPaddingException",
            "PrinterException",
            "TimeoutException",
            "ResponseStatusException",
            "ParseException",
            "TechniqueException",
            "JavascriptException",
            "InvalidKeyspecException"
        };
    private static Pattern patternSyntaxError = Pattern.compile(regexSyntaxError, Pattern.CASE_INSENSITIVE);
    private static Pattern patternFatalError = Pattern.compile(regexFatalError, Pattern.CASE_INSENSITIVE);
    private static Pattern patternException = Pattern.compile(regexException, Pattern.CASE_INSENSITIVE);
    public static boolean SyntaxErrorMatch(String article){
        Matcher a=patternSyntaxError.matcher(article);
        return a.find();
    }
    public static boolean FatalErrorMatch(String article){
        Matcher a=patternFatalError.matcher(article);
        return a.find();
    }
    public static boolean ExceptionMatch(String article){
        Matcher a=patternException.matcher(article);
        return a.find();
    }
    public static boolean CommentSyntaxErrorMatch(CommentData commentData){
        return SyntaxErrorMatch(commentData.getBody())||SyntaxErrorMatch(commentData.getBodyMarkdown());
    }
    public static boolean CommentFatalErrorMatch(CommentData commentData){
        return FatalErrorMatch(commentData.getBody())||FatalErrorMatch(commentData.getBodyMarkdown());
    }
    public static boolean CommentExceptionMatch(CommentData commentData){
        return ExceptionMatch(commentData.getBody())||ExceptionMatch(commentData.getBodyMarkdown());
    }
    public static boolean QuestionSyntaxErrorMatch(QuestionData questionData){
        return SyntaxErrorMatch(questionData.getBody())||SyntaxErrorMatch(questionData.getBodyMarkdown())
                ||SyntaxErrorMatch(questionData.getTitle());
    }
    public static boolean QuestionFatalErrorMatch(QuestionData questionData){
        return FatalErrorMatch(questionData.getBody())||FatalErrorMatch(questionData.getBodyMarkdown())
                ||FatalErrorMatch(questionData.getTitle());
    }
    public static boolean QuestionExceptionMatch(QuestionData questionData){
        return ExceptionMatch(questionData.getBody())||ExceptionMatch(questionData.getBodyMarkdown())
                ||ExceptionMatch(questionData.getTitle());
    }
    public static boolean AnswerSyntaxErrorMatch(AnswerData answerData){
        return SyntaxErrorMatch(answerData.getBody())||SyntaxErrorMatch(answerData.getBodyMarkdown())
                ||SyntaxErrorMatch(answerData.getTitle());
    }
    public static boolean AnswerFatalErrorMatch(AnswerData answerData){
        return FatalErrorMatch(answerData.getBody())||FatalErrorMatch(answerData.getBodyMarkdown())
                ||FatalErrorMatch(answerData.getTitle());
    }
    public static boolean AnswerExceptionMatch(AnswerData answerData){
        return ExceptionMatch(answerData.getBody())||ExceptionMatch(answerData.getBodyMarkdown())
                ||ExceptionMatch(answerData.getTitle());
    }

    public static void ErrorArrayClassifyQ(QuestionData questionData,HashMap<String,
            Boolean>errorNumber,String[]errorArray){
        Pattern[]patterns=new Pattern[errorArray.length];
        for(int i=0;i<errorArray.length;i++){
            patterns[i]=Pattern.compile(errorArray[i],Pattern.CASE_INSENSITIVE);
        }
        for(int i=0;i<errorArray.length;i++){
            if(patterns[i].matcher(questionData.getBody()).find()||patterns[i].matcher(questionData.getBodyMarkdown()).find()
                    ||patterns[i].matcher(questionData.getTitle()).find()){
                errorNumber.put(errorArray[i],true);
            }
        }
    }
    public static void ErrorArrayClassifyA(AnswerData answerData,HashMap<String,
            Boolean>errorNumber,String[]errorArray){
        Pattern[]patterns=new Pattern[errorArray.length];
        for(int i=0;i<errorArray.length;i++){
            patterns[i]=Pattern.compile(errorArray[i],Pattern.CASE_INSENSITIVE);
        }
        for(int i=0;i<errorArray.length;i++){
            if(patterns[i].matcher(answerData.getBody()).find()||patterns[i].matcher(answerData.getBodyMarkdown()).find()
                    ||patterns[i].matcher(answerData.getTitle()).find()){
                errorNumber.put(errorArray[i],true);
            }
        }
    }
    public static void ErrorArrayClassifyC(CommentData commentData,HashMap<String,
            Boolean>errorNumber,String[]errorArray){
        Pattern[]patterns=new Pattern[errorArray.length];
        for(int i=0;i<errorArray.length;i++){
            patterns[i]=Pattern.compile(errorArray[i],Pattern.CASE_INSENSITIVE);
        }
        for(int i=0;i<errorArray.length;i++){
            if(patterns[i].matcher(commentData.getBody()).find()||
                    patterns[i].matcher(commentData.getBodyMarkdown()).find()){
                errorNumber.put(errorArray[i],true);
            }
        }
    }

}