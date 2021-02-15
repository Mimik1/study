import  java.util.*;

class Column {
    String name;
    private Class<? extends Value> type;
    public ArrayList<Value> records;

    Column(String name, Class<? extends Value> type) {
        this.name = name;
        this.type = type;
        records = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Class<? extends Value> getType() {
        return type;
    }



    public Value max() {
        Value max = records.get(0);
        for(Value cell : records){
            /*if(((IntegerValue)cell).getValue() > ((IntegerValue)min).getValue()){
                min = cell;
            }*/
            if(((Value)cell.getValue()).gte(max)){
                max = cell;
            }
        }
        return max;
    }
    public Value min() {
        Value min = records.get(0);
        for(Value cell : records){
            if(((Value)cell.getValue()).lte(min)){
                min = cell;
            }
        }
        return min;
    }
}

