public class DoubleValue extends Value
{
    private Double value;
    public DoubleValue() {}
    public DoubleValue(double value)
    {
        this.value = value;
    }
    public Double getValue()
    {
        return value;
    }

    public DoubleValue(String string) {
        if (string.contains(".")) {
            value = Double.valueOf(string.substring(0, string.indexOf(".")));
        }
        else {
            value = Double.valueOf(string);
        }
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public Value add(Value value) {
        if(value instanceof DoubleValue)
        {
            Double val = (Double) value.getValue();
            return new DoubleValue(this.value + val);
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public Value sub(Value value) {
        if(value instanceof DoubleValue)
        {
            Double val = (Double) value.getValue();
            return new DoubleValue(this.value - val);
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public Value mul(Value value) {
        if(value instanceof DoubleValue)
        {
            Double val = (Double) value.getValue();
            return new DoubleValue(this.value * val);
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public Value div(Value value) {
        if(value instanceof DoubleValue)
        {
            Double val = (Double) value.getValue();
            if(val == 0) throw new ArithmeticException("Nie dzielimy przez 0");
            return new DoubleValue(this.value / val);
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public Value pow(Value value) {
        if(value instanceof DoubleValue)
        {
            Double val = (Double) value.getValue();
            return new DoubleValue((int) Math.pow(this.value, val));
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public boolean eq(Value value) {
        if(value instanceof DoubleValue)
        {
            Double val = (Double) value.getValue();
            return val.equals(this.value);
        }
        return false;
    }

    @Override
    public boolean lte(Value value) {
        if(value instanceof DoubleValue)
        {
            Double val = (Double) value.getValue();
            return val >= (this.value);
        }
        return false;
    }

    @Override
    public boolean gte(Value value) {
        if(value instanceof DoubleValue)
        {
            Double val = (Double) value.getValue();
            return val <= (this.value);
        }
        return false;
    }

    @Override
    public boolean neq(Value value) {
        if (value instanceof DoubleValue) {
            Double val = (Double) value.getValue();
            return !val.equals(this.value);
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof DoubleValue) {
            DoubleValue DoubleValue = (DoubleValue) other;
            return DoubleValue.value.equals(value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public Value clone() {
        return new DoubleValue(value);
    }

    @Override
    public Value create(String s) {
        return new DoubleValue(Double.parseDouble(s));
    }

}