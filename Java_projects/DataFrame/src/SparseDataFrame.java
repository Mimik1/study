public class SparseDataFrame extends DataFrame {
    char hide;
    public SparseDataFrame(String[] names, String[] types, char hide){
        super(names,types);
    }
    public void addValue(int i, int value) {
        if((char)value != hide)
        aframe.get(i).getValue().column.add( new CooValue(aframe.column.size,value));

    }
    public toDanse()
}
