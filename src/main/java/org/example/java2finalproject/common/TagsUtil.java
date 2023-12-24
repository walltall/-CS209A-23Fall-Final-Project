package org.example.java2finalproject.common;

import org.junit.runners.model.MemberValueConsumer;

import java.util.Set;

public class TagsUtil {
    public static String[] interestingTags=
            {"spring","spring-boot","spring-data-jpa","jpa","generics","tomcat",
            "maven","jvm","javafx","serialization","hibernate","jar"};
    public static String[] getTagsArray(String tags) {
        //去掉开头的[与结尾的],并除去空格
        if (tags.startsWith("[") && tags.endsWith("]")) {
            tags = tags.substring(1, tags.length() - 1);
            tags=tags.replaceAll("\"", "");
            tags = tags.replaceAll(" ", "");
            String[] tagsArray = tags.split(",");
            return tagsArray;
        } else {
            return null;
        }

    }
    public static String[] topic=new String[]{
            "spring","spring-boot","spring-data-jpa","jpa","generics","tomcat",
            "maven","jvm","javafx","serialization","hibernate","jar",
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
            "InvalidKeyspecException", "OutOfMemoryError",
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
            "ResponseError",
            "Design Patterns",
            "JVM",
            "MVC",
            "Runnable",
            "Thread pool",
            "Web"
    };
}
