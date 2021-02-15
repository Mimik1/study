public class CooValue extends Value
{
    private int index;
    private Value value;
    CooValue(int index, Value value)
    {
        this.index = index;
        this.value = value;
    }
    public int getIndex() {
        return index;
    }
    public Value getValue() {
        return value;
    }
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Value add(Value value) {
        if (value instanceof CooValue) {
            return this.value.add(((CooValue) value).getValue());
        }
        return this.value.add(value);
    }

    @Override
    public Value sub(Value value) {
        if (value instanceof CooValue) {
            return this.value.sub(((CooValue) value).getValue());
        }
        return this.value.sub(value);
    }

    @Override
    public Value mul(Value value) {
        if (value instanceof CooValue) {
            return this.value.mul(((CooValue) value).getValue());
        }
        return this.value.mul(value);
    }

    @Override
    public Value div(Value value) {
        if (value instanceof CooValue) {
            return this.value.div(((CooValue) value).getValue());
        }
        return this.value.div(value);
    }

    @Override
    public Value pow(Value value) {
        if (value instanceof CooValue) {
            return this.value.pow(((CooValue) value).getValue());
        }
        return this.value.pow(value);
    }

    @Override
    public boolean eq(Value value) {
        if (value instanceof CooValue) {
            return this.value.eq(((CooValue) value).getValue());
        }
        return this.value.eq(value);
    }

    @Override
    public boolean lte(Value value) {
        if (value instanceof CooValue) {
            return this.value.lte(((CooValue) value).getValue());
        }
        return this.value.lte(value);
    }

    @Override
    public boolean gte(Value value) {
        if (value instanceof CooValue) {
            return this.value.gte(((CooValue) value).getValue());
        }
        return this.value.gte(value);
    }

    @Override
    public boolean neq(Value value) {
        if (value instanceof CooValue) {
            return this.value.neq(((CooValue) value).getValue());
        }
        return this.value.neq(value);
    }

    @Override
    public boolean equals(Object other) {
        if (value instanceof CooValue) {
            return this.value.equals(((CooValue) value).getValue()) && this.index == ((CooValue) value).getIndex();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public Value clone() {
        return new CooValue(index, value.clone());
    }

    @Override
    public Value create(String s) {
        return this.value.create(s);
    }

}
