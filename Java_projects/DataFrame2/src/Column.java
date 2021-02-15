
import java.util.ArrayList;

class Column{
    String name;
    String type;
    ArrayList<Object> records;

    Column(String name, String type) {
        this.name = name;
        this.type = type;
        records = new ArrayList<>();
    }

    public int colsize(){
        return records.size();
    }
}