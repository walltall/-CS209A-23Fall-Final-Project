package org.example.java2finalproject.common;

public class NumCountObject implements Comparable<NumCountObject> {
    public final Object content;
    public final long num;
    public NumCountObject(Object content, long num) {
        this.content = content;
        this.num = num;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NumCountObject){
            return this.num==((NumCountObject)obj).num;
        }else {
            return false;
        }
    }


    @Override
    public int compareTo(NumCountObject o) {
        return Long.compare(o.num, this.num);
    }
}
