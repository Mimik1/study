package com.company;

import java.io.IOException;

public class IntegerValue extends Value {
    public int value;
    public IntegerValue(int val){
        value = val;
    }
    public String toString(){
        return String.valueOf(value);
    }

    public Value add(Value x ){
        value += ((IntegerValue)x).value;
        return this;
    }
    public Value sub(Value  x ){
        value -= ((IntegerValue)x).value;
        return this;
    }
    public Value mul(Value x ){
        value *= ((IntegerValue)x).value;
        return this;
    }
    public Value div(Value x ){
        value /= ((IntegerValue)x).value;
        return this;
    }
    public Value pow(Value x ){
        for(int i=0; i < ((IntegerValue)x).value; i++){
            value *= value;
        }
        return this;
    }
    public boolean eq(Value x ){
        return value == ((IntegerValue)x).value;
    }
    public boolean lte(Value x ){
        return value < ((IntegerValue)x).value;
    }
    public boolean gte(Value x ){
        return value > ((IntegerValue)x).value;
    }
    public boolean neq(Value x ){
        return value != ((IntegerValue)x).value;
    }
    @Override
    public boolean equals(Object other){
        //return other.equals(value);

        if (this == other)
            return true;
        if (other == null)
            return false;
        if (getClass() != other.getClass())
            return false;
        IntegerValue o = (IntegerValue) other;
        if (Double.doubleToLongBits(value) != Double.doubleToLongBits(o.value))
            return false;
        return true;
    }
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    public Value create(String s){
        value = java.lang.Integer.parseInt(s);
        return this;
    }
}
