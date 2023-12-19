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
        return SyntaxErrorMatch(commentData.getBody())||SyntaxErrorMatch(commentData.getBody_markdown());
    }
    public boolean CommentFatalErrorMatch(CommentData commentData){
        return FatalErrorMatch(commentData.getBody())||FatalErrorMatch(commentData.getBody_markdown());
    }
    public boolean CommentExceptionMatch(CommentData commentData){
        return ExceptionMatch(commentData.getBody())||ExceptionMatch(commentData.getBody_markdown());
    }
    public boolean QuestionSyntaxErrorMatch(QuestionData questionData){
        return SyntaxErrorMatch(questionData.getBody())||SyntaxErrorMatch(questionData.getBody_markdown())
                ||SyntaxErrorMatch(questionData.getTitle());
    }
    public boolean QuestionFatalErrorMatch(QuestionData questionData){
        return FatalErrorMatch(questionData.getBody())||FatalErrorMatch(questionData.getBody_markdown())
                ||FatalErrorMatch(questionData.getTitle());
    }
    public boolean QuestionExceptionMatch(QuestionData questionData){
        return ExceptionMatch(questionData.getBody())||ExceptionMatch(questionData.getBody_markdown())
                ||ExceptionMatch(questionData.getTitle());
    }
    public boolean AnswerSyntaxErrorMatch(AnswerData answerData){
        return SyntaxErrorMatch(answerData.getBody())||SyntaxErrorMatch(answerData.getBody_markdown())
                ||SyntaxErrorMatch(answerData.getTitle());
    }
    public boolean AnswerFatalErrorMatch(AnswerData answerData){
        return FatalErrorMatch(answerData.getBody())||FatalErrorMatch(answerData.getBody_markdown())
                ||FatalErrorMatch(answerData.getTitle());
    }
    public boolean AnswerExceptionMatch(AnswerData answerData){
        return ExceptionMatch(answerData.getBody())||ExceptionMatch(answerData.getBody_markdown())
                ||ExceptionMatch(answerData.getTitle());
    }




    
}