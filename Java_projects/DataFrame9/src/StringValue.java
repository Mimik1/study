public class StringValue extends Value{

    public String value;

    public StringValue(){}
    public StringValue(String val) { value = val;}
    public String getValue()
    {
        return value;
    }
    public String toString(){
        return value;
    }

    public Value add(Value arg) {
        value += ((StringValue)arg).value;
        return this;
    }

    public Value sub(Value arg)
    {
        return null;
    }

    public Value mul(Value arg)
    {
        return null;
    }

    public Value div(Value arg)
    {
        return null;
    }

    public Value pow(Value arg)
    {
        return null;
    }

    public boolean eq(Value arg)
    {
        if (value == ((StringValue)arg).value) return true;
        else return false;
    }

    public boolean lte(Value arg)
    {
        return this.value.length() <= ((StringValue) arg).value.length();
    }

    public boolean gte(Value arg)
    {
        return this.value.length() >= ((StringValue) arg).value.length();
    }

    public boolean neq(Value arg)
    {
        return !((StringValue) arg).value.equals(value);
    }

    public boolean equals(Object other){
        if( value == other) return true;
        if( other == null || this.getClass() != other.getClass())  return false;
        StringValue t = (StringValue) other;
        return (t.value == this.value);
    }

    public int hashCode()
    {
        return value.hashCode();
    }

    @Override
    public Value clone() {
        return new StringValue(value);
    }

    public Value create(String s){
        StringValue t = new StringValue(s);
        return t;
    }

}
