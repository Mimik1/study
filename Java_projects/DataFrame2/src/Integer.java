public class Integer extends Value {
    int value;
    public Integer(int val){
        value = val;
    }
    public String toString(){
        return String.valueOf(value);
    }
    public Value add(Value x ){
        value += ((Integer)x).value;
        return this;
    }
    public Value sub(Value  x ){
        value -= ((Integer)x).value;
        return this;
    }
    public Value mul(Value x ){
        value *= ((Integer)x).value;
        return this;
    }
    public Value div(Value x ){
        value /= ((Integer)x).value;
        return this;
    }
    public Value pow(Value x ){
        for(int i=0; i < ((Integer)x).value; i++){
            value *= value;
        }
        return this;
    }
    public boolean eq(Value x ){
        if(value == ((Integer)x).value) return true;
        return false;
    }
    public boolean lte(Value x ){
        if(value < ((Integer)x).value) return true;
        return false;
    }
    public boolean gte(Value x ){
        if(value > ((Integer)x).value) return true;
        return false;
    }
    public boolean neq(Value x ){
        if(value != ((Integer)x).value) return true;
        return false;
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
        Integer o = (Integer) other;
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
