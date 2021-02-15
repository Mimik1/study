
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;


public class DataFrame implements Cloneable{
    protected ArrayList<Column> columns;
    protected String [] columnNames;
    protected Class<? extends Value> [] columnTypes;
    DataFrame() {}
    public DataFrame(String[] columnNames, Class<? extends Value>[] columnTypes) {
        if(columnNames.length != columnTypes.length){
            throw new IllegalArgumentException("Number of columnNames and columnTypes are not equal.");
        }

        columns = new ArrayList<>();

        for (int i=0; i<columnNames.length; i++) {
            columns.add(new Column(columnNames[i], columnTypes[i]));
        }
        this.columnNames = columnNames;
        this.columnTypes = columnTypes;
    }
    public DataFrame(String fileName, Class<? extends Value>[] columnTypes, String[] columnNames) throws Exception {
      //  FileInputStream fileStream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        if(columnNames != null){
            columns = new ArrayList<>();
            for (int i=0; i<columnNames.length; i++) {
                columns.add(new Column(columnNames[i], columnTypes[i]));
            }
        }

        String strLine;
        String splited;
        String[] splitedArray;
        this.columnTypes = columnTypes;
        if(columnNames == null){
            strLine = br.readLine();
            splited = strLine;
            splitedArray = splited.split(",");
            this.columnNames = splitedArray;
            columns = new ArrayList<>();
            for (int i=0; i<splitedArray.length; i++) {
                columns.add(new Column(splitedArray[i], columnTypes[i]));
            }
        }
        Value [] values = new Value[columnTypes.length];
        List<Constructor<? extends Value>> constructors = new ArrayList<>(columnTypes.length);
        for (int i = 0; i < columnTypes.length; i++) {
            constructors.add(columnTypes[i].getConstructor(String.class));
        }
        while ((strLine = br.readLine()) != null)   {
            splited = strLine;
            splitedArray = splited.split(",");
            for (int i=0; i<splitedArray.length; i++) {
                values[i] = constructors.get(i).newInstance(splitedArray[i]);
            }
            add(values);
        }
        br.close();
    }
    public DataFrame(String fileName, Class<? extends Value>[] columnTypes ) throws Exception {
        this(fileName,columnTypes, null);
    }

    private DataFrame(ArrayList<Column> columns) {
        this.columns=columns;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public Class<? extends Value>[] getColumnTypes() {
        return columnTypes;
    }

    public void add(Value[] args) {

        if(args.length > columns.size()){
            throw new IllegalArgumentException("Too many arguments");
        }
        else if(args.length < columns.size()){
            throw new IllegalArgumentException("Too little arguments");
        }
        for (int i=0; i<args.length; i++)
        {
            if (!columns.get(i).getType().isInstance(args[i]))
            {
                throw new IllegalArgumentException();
            }
        }
        for (int i=0; i<args.length; i++)
        {
            columns.get(i).records.add(args[i]);
        }
    }
    public void set(int col, int index, Value x) {
     if(columns.get(col).getType().isInstance(x))
     {
         columns.get(col).records.set(index, x);
     }
     else {
         throw new IllegalArgumentException("Type doesn't match");
     }
    }

    public int size(){
        return columns.get(0).records.size();
    }

    public Column get(String colname){
          return columns.stream()
                .filter(columns -> columns.getName().equals(colname))
                  .findFirst()
                  .orElseThrow(IllegalArgumentException::new);
    }
    public DataFrame get(String [] cols, boolean copy) throws CloneNotSupportedException{
        ArrayList<Column> chosenColumns = new ArrayList<>();
        for(String colName : cols)
        {
            chosenColumns.add(get(colName));
        }

        //TODO bug - probably always deep copy
        if(copy){
            DataFrame newDF = new DataFrame(chosenColumns);
            return (DataFrame) newDF.clone();
        }
        else {
            return  new DataFrame(chosenColumns);
        }
    }
    public Value [] getLine(int index) {
       return columns.stream()
                .map(column -> column.records.get(index))
                .toArray(Value[]::new);
    }
    public DataFrame iloc(int i){
        if(i<0 || i>size()){
            throw new IndexOutOfBoundsException(i + " is out of bound.");
        }

        Value[] chosenLine = new Value[columns.size()];
        for (int j=0; j<columns.size(); j++) {
            chosenLine[j]=columns.get(j).records.get(i);

        }

        DataFrame newDF = new DataFrame(columnNames, columnTypes);
        newDF.add(chosenLine);
        return newDF;
    }

    public DataFrame iloc(int from, int to){
        if(from<0 || from>size()){
            throw new IndexOutOfBoundsException(from + " is out of bound");
        }
        else if (to<0 || to>size()){
            throw new IndexOutOfBoundsException(to + " is out of bound");
        }

        Value[] chosenLine = new Value[columns.size()];
        DataFrame newDF = new DataFrame(columnNames, columnTypes);
        for (int i=from; i<=to; i++) {
            for (int j=0; j<columns.size(); j++) {
                chosenLine[j]=columns.get(j).records.get(i);
            }
            newDF.add(chosenLine);
        }
        return newDF;
    }

    void printTableHeader() {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("|%4s |", "#");
        for (Column col : columns){
            System.out.printf("%25s |", col.name);
        }
        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------");
    }
    public void print(){
        printTableHeader();

        for (int i=0; i<size(); i++) {
            System.out.printf("|%4s |", i);
            for (Column col : columns){
                System.out.printf("%25s |", col.records.get(i));
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------");
    }

    //Groupby
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /*public interface Applyable{
        DataFrame apply();
    }

    class Mediana implements Applyable{
        @Override
        public DataFrame apply(){

        }
    }*/

    public interface GroupBy{
        DataFrame max();
        DataFrame min();
        DataFrame mean();
        DataFrame std();
        DataFrame sum();
        DataFrame var();
        //public DataFrame apply(Applyable applyable);
    }

    public DataFrameGroupBy groupBy(String[] columnNames)
    {
        HashMap<Value, DataFrame> df= new HashMap<>(columnNames.length);
        int index=0;
        for(int i=0; i < columnNames.length; i++) {
            if (columns.get(i).name == columnNames[i]) {
                index = i;
                break;
            }
        }
        for(int i=0; i < size(); i++){
            if(! df.containsKey(getLine(i)[index])){
                df.put(getLine(i)[index], new DataFrame(this.columnNames, this.columnTypes));
            }
            df.get(getLine(i)[index]).add(getLine(i));
        }
        return new DataFrameGroupBy(df, index);
    }
    public class DataFrameGroupBy implements GroupBy {
        private ArrayList<DataFrame> adf = new ArrayList<>();
        private int index;

        DataFrameGroupBy(HashMap<Value, DataFrame> df, int index){
            this.index = index;
            for(DataFrame tmp  : df.values()) {
                adf.add(tmp);
            }
        }

        @Override
        public DataFrame max() {
            DataFrame newdf = new DataFrame(columnNames, columnTypes);
            for(DataFrame frame : adf){
                Value[] tmp = new Value[columnNames.length];
                for(int i=0; i < columnNames.length; i++){
                    if(i != index){
                        tmp[i]=  frame.columns.get(i).max();
                    }
                    else{
                        tmp[i] = frame.columns.get(i).records.get(0);
                    }
                }
                newdf.add(tmp);
            }
            return newdf;
        }
        @Override
        public DataFrame min() {
            DataFrame newdf = new DataFrame(columnNames, columnTypes);
            for(DataFrame frame : adf){
                Value[] tmp = new Value[columnNames.length];
                for(int i=0; i < columnNames.length; i++){
                    if(i != index){
                        tmp[i]=  frame.columns.get(i).min();
                    }
                    else{
                        tmp[i] = frame.columns.get(0).records.get(0);
                    }
                }
                newdf.add(tmp);
            }
            return newdf;
        }

        @Override
        public DataFrame mean() {
            return null;
        }

        @Override
        public DataFrame std() {
            return null;
        }

        @Override
        public DataFrame sum() {
            return null;
        }

        @Override
        public DataFrame var() {
            return null;
        }
    }

}