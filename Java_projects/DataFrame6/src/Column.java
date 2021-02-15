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
        int mean=0;
        switch (type) {
            case "IntegerValue":
                for (Value cell : records) {
                    mean += (Integer)(cell.getValue());
                }
                mean = mean/records.size();
                return new DoubleValue((Integer)mean);
            case "DoubleValue":
                for (Value cell : records) {
                    mean += (Double)(cell.getValue());
                }
                mean = mean/records.size();
                return new DoubleValue(mean);
            case "StringValue":
                break;
        }
        return records.get(0);
    }
    @Override
    public Column clone() throws CloneNotSupportedException {
        Column columnCloned = (Column) super.clone();
        columnCloned.records = new ArrayList<>();
        columnCloned.records.addAll(records);
        return columnCloned;
    }
}

