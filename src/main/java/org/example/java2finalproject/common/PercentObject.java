package org.example.java2finalproject.common;

public class PercentObject implements Comparable<NumCountObject> {
    public final Object content;
    public double num;
    public PercentObject(Object content, double num) {
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
        return Double.compare(o.num, this.num);
    }

}
