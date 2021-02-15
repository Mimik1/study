public class COOValue {
    private int place;
    private Object value;

    public COOValue(int index, Object value){
        this.place = index;
        this.value = value;
    }

    int getPlace(){ return place;}

    Object getValue(){ return value;}
}
