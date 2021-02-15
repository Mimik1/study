public class FloatValue extends Value{

    public Float value;
    public FloatValue(float val) { value = val;}
    public Float getValue()
    {
        return value;
    }
    public String toString(){
        FloatValue t = new FloatValue(value);
        return Float.toString(t.value);
    }

    public Value add(Value arg)
    {
        value += ((FloatValue)arg).value;
        return this;
    }

    public Value sub(Value arg)
    {
        value -= ((FloatValue)arg).value;
        return this;
    }

    public Value mul(Value arg)
    {
        value *= ((FloatValue)arg).value;
        return this;
    }

    public Value div(Value arg)
    {
        value /= ((FloatValue)arg).value;
        return this;
    }

    public Value pow(Value arg)
    {
        FloatValue t = new FloatValue(value);
        for(int i=0;i<((IntegerValue)arg).getValue();i++) {
            value *= t.value;
        }
        return this;
    }

    public boolean eq(Value arg)
    {
        if (value == ((FloatValue)arg).value) return true;
        else return false;
    }

    public boolean lte(Value arg)
    {
        if (value < ((FloatValue)arg).value) return true;
        else return false;
    }

    public boolean gte(Value arg)
    {
        if (value > ((FloatValue)arg).value) return true;
        else return false;
    }

    public boolean neq(Value arg)
    {
        if( value != ((FloatValue)arg).value) return true;
        else return false;
    }

    public boolean equals(Object other){
        if( value == other) return true;
        if( other == null || this.getClass() != other.getClass())  return false;
        FloatValue t = (FloatValue) other;
        return (t.value == this.value);
    }

    public int hashCode()
    {
        int valu = value.intValue();
        return valu;
    }

    @Override
    public Value clone() {
        return new FloatValue(value);
    }
    public Value create(String s){
        FloatValue t = new FloatValue(Float.parseFloat(s));
        return t;
    }

}
