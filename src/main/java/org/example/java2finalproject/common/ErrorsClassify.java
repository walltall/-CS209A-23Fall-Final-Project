package org.example.java2finalproject.common;

import org.example.java2finalproject.entity.CommentData;
import org.example.java2finalproject.entity.QuestionData;
import org.example.java2finalproject.entity.AnswerData;
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
    public boolean SyntaxErrorMatch(String article){
        Matcher a=patternSyntaxError.matcher(article);
        return a.find();
    }
    public boolean FatalErrorMatch(String article){
        Matcher a=patternFatalError.matcher(article);
        return a.find();
    }
    public boolean ExceptionMatch(String article){
        Matcher a=patternException.matcher(article);
        return a.find();
    }
    public boolean CommentSyntaxErrorMatch(CommentData commentData){
        return SyntaxErrorMatch(commentData.getBody())||SyntaxErrorMatch(commentData.getBodyMarkdown());
    }
    public boolean CommentFatalErrorMatch(CommentData commentData){
        return FatalErrorMatch(commentData.getBody())||FatalErrorMatch(commentData.getBodyMarkdown());
    }
    public boolean CommentExceptionMatch(CommentData commentData){
        return ExceptionMatch(commentData.getBody())||ExceptionMatch(commentData.getBodyMarkdown());
    }
    public boolean QuestionSyntaxErrorMatch(QuestionData questionData){
        return SyntaxErrorMatch(questionData.getBody())||SyntaxErrorMatch(questionData.getBodyMarkdown())
                ||SyntaxErrorMatch(questionData.getTitle());
    }
    public boolean QuestionFatalErrorMatch(QuestionData questionData){
        return FatalErrorMatch(questionData.getBody())||FatalErrorMatch(questionData.getBodyMarkdown())
                ||FatalErrorMatch(questionData.getTitle());
    }
    public boolean QuestionExceptionMatch(QuestionData questionData){
        return ExceptionMatch(questionData.getBody())||ExceptionMatch(questionData.getBodyMarkdown())
                ||ExceptionMatch(questionData.getTitle());
    }
    public boolean AnswerSyntaxErrorMatch(AnswerData answerData){
        return SyntaxErrorMatch(answerData.getBody())||SyntaxErrorMatch(answerData.getBodyMarkdown())
                ||SyntaxErrorMatch(answerData.getTitle());
    }
    public boolean AnswerFatalErrorMatch(AnswerData answerData){
        return FatalErrorMatch(answerData.getBody())||FatalErrorMatch(answerData.getBodyMarkdown())
                ||FatalErrorMatch(answerData.getTitle());
    }
    public boolean AnswerExceptionMatch(AnswerData answerData){
        return ExceptionMatch(answerData.getBody())||ExceptionMatch(answerData.getBodyMarkdown())
                ||ExceptionMatch(answerData.getTitle());
    }




    
}