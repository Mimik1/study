import  java.util.*;

class Column {
    String name;
    String type;
    public ArrayList<Value> records;

    Column(String name, String type) {
        this.name = name;
        this.type = type;
        records = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getType() { return type; }



    public Value max() {
        Value max = records.get(0);
        for(Value cell : records){
            if(cell.gte(max)){
                max = cell;
            }
        }
        return max;
    }
    public Value min() {
        Value min = records.get(0);
        for(Value cell : records){
            if(cell.lte(min)){
                min = cell;
            }
        }
        return min;
    }
    public Value mean(){
        Value tmp;
        if(type != "StringValue"){
            tmp = new DoubleValue((Double)sum().getValue()/records.size());
        }
        else{
            tmp = records.get(0);
        }
        return tmp;
    }

    public Value sum(){
        Value tmp;
        Double sum = 0.0;
        if(type != "StringValue") {
            for (Value cell : records) {
                sum += (Double) cell.getValue();
            }
            tmp = new DoubleValue(sum);
        }
        else {
            tmp = records.get(0);
        }
        return tmp;
    }
    @Override
    public Column clone() throws CloneNotSupportedException {
        Column columnCloned = (Column) super.clone();
        columnCloned.records = new ArrayList<>();
        columnCloned.records.addAll(records);
        return columnCloned;
    }
}

