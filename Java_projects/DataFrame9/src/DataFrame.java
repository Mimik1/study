
import java.io.*;
import java.nio.file.Files;
import java.lang.reflect.Constructor;
import java.util.*;


public class DataFrame implements Cloneable{
    protected ArrayList<Column> columns;
    protected String [] columnNames;
    protected String [] columnTypes;
    DataFrame() {}
    public DataFrame(String[] columnNames, String[] columnTypes) {
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
    public DataFrame(String fileName, String[] columnTypes) throws IOException,ClassNotFoundException, IllegalAccessException, InstantiationException  {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){

            columns = new ArrayList<>();
            String line = br.readLine();
            String[] header = line.split(",");
            columnNames = header;
            this.columnTypes = columnTypes;
            for (int i=0; i<columnTypes.length; i++) {
                System.out.println();
                columns.add(new Column(header[i], columnTypes[i]));
            }

            Value[] cells2 = new Value[columnTypes.length];
            while((line = br.readLine()) != null){
                String[] cells1 = line.split(",");
                for(int i=0; i<cells1.length; i++){
                    switch (columnTypes[i]) {
                        case "IntegerValue":
                            IntegerValue a = new IntegerValue();
                            cells2[i] = a.create(cells1[i]);
                            break;
                        case "DoubleValue":
                            DoubleValue c = new DoubleValue();
                            cells2[i] = c.create(cells1[i]);
                            break;
                        case "StringValue":
                            StringValue b = new StringValue();
                            cells2[i] = b.create(cells1[i]);
                            break;
                    }

                }
                add(cells2);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public DataFrame(String fileName) throws IOException,ClassNotFoundException, IllegalAccessException, InstantiationException  {
        columns = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            String[] header = line.split(",");
            columnNames = header;
            line = br.readLine();
            String[] types = line.split(",");
            String[] ctypes= new String[header.length];
            for (int i = 0; i < header.length; i++) {
                Scanner in=new Scanner(header[i]);
                if(in.hasNextInt()) {
                    columns.add(new Column(types[i], "IntegerValue"));
                    ctypes[i] = "IntegerValue";
                }
                else if(in.hasNextFloat()) {
                    columns.add(new Column(types[i], "FloatValue"));
                    ctypes[i] = "FloatValue";
                }
                else if(in.hasNextDouble()) {
                    columns.add(new Column(types[i], "DoubleValue"));
                    ctypes[i] = "DoubleValue";
                }
                else if(in.hasNext()) {
                    columns.add(new Column(types[i], "StringValue"));
                    ctypes[i] = "StringValue";
                }
                this.columnTypes = ctypes;
            }
        }
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            br.readLine();
            String line;
            Value[] cells2 = new Value[columnTypes.length];
            while((line = br.readLine()) != null){
                String[] cells1 = line.split(",");
                for(int i=0; i<cells1.length; i++){
                    switch (columnTypes[i]) {
                        case "IntegerValue":
                            IntegerValue a = new IntegerValue();
                            cells2[i] = a.create(cells1[i]);
                            break;
                        case "DoubleValue":
                            DoubleValue c = new DoubleValue();
                            cells2[i] = c.create(cells1[i]);
                            break;
                        case "StringValue":
                            StringValue b = new StringValue();
                            cells2[i] = b.create(cells1[i]);
                            break;
                    }

                }
                add(cells2);
            }
        } catch (FileNotFoundException ex) {
             ex.printStackTrace();
         } catch(IOException e){
            e.printStackTrace();
        }

    }


    private DataFrame(ArrayList<Column> columns) {
        this.columns=columns;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public String[] getColumnTypes() {
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
            columns.get(i).records.add(args[i]);
        }
    }
    public void set(int col, int index, Value x) {
        columns.get(col).records.set(index, x);
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

    public void print(int limit) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        String line = "------" + "+--------------------------".repeat(Math.max(0, columns.size())) + "-";
        System.out.println(line);
        System.out.printf("|%4s |", "#");
        for (Column col : columns){
            System.out.printf("%25s |", col.name);
        }
        System.out.println();
        System.out.println(line);

        if (limit == 0) limit = size();

        for (int i=0; i<limit; i++) {
            System.out.printf("|%4s |", i);
            for (Column col : columns){
                System.out.printf("%25s |", col.records.get(i));
            }
            System.out.println();
        }
        System.out.println(line);
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
        DataFrame maxt() throws InterruptedException;
        DataFrame min();
        DataFrame mint()  throws InterruptedException ;
        DataFrame mean();
        DataFrame meant()throws InterruptedException ;
        DataFrame std();
        DataFrame sum();
        DataFrame sumt() throws InterruptedException;
        DataFrame var();
        //public DataFrame apply(Applyable applyable);
    }

    public DataFrameGroupBy groupBy(String columnNames) {
        HashMap<String, DataFrame> df = new HashMap<>();
        int index=0;

        for(int i=0; i < this.columnNames.length; i++) {
            if (columnNames.equals(columns.get(i).name)) {
                index = i;
                break;
            }
        }

        for(int i=0; i < size(); i++){
            if( !df.containsKey((String)getLine(i)[index].getValue())){
                df.put((String)getLine(i)[index].getValue(), new DataFrame(this.columnNames, this.columnTypes));
            }
            df.get((String)getLine(i)[index].getValue()).add(getLine(i));
        }

        return new DataFrameGroupBy(df, index);
    }

    public class DataFrameGroupBy implements GroupBy {
        private ArrayList<DataFrame> adf = new ArrayList<>();
        private int index;

        DataFrameGroupBy(HashMap<String, DataFrame> df, int index){
            this.index = index;
            adf.addAll(df.values());
        }
        @Override
        public DataFrame max()  {
            DataFrame newdf = new DataFrame(columnNames, columnTypes);
            for(DataFrame frame : adf){
                Value[] tmp = new Value[columnNames.length];
                for(int i=0; i < columnNames.length; i++){
                    if(i != index){
                        tmp[i]=  frame.columns.get(i).max();
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
        public DataFrame maxt() throws InterruptedException {
            DataFrame newdf = new DataFrame(columnNames, columnTypes);
            Thread[] threads = new Thread[adf.size()];
            for(int i=0; i<adf.size(); i++){
                DataFrame frame = adf.get(i);
                Runnable r =() ->{
                    Value[] line = new Value[columnNames.length];
                    for(int j=0; j < columnNames.length; j++){
                        if(j != index){
                            line[j]=  frame.columns.get(j).max();
                        }
                        else{
                            line[j] = frame.columns.get(j).records.get(0);
                        }
                    }
                    synchronized (newdf) {
                        newdf.add(line);
                    }
                };
                threads[i] = new Thread(r);
            }
            for(int i=0; i<adf.size(); i++){ threads[i].start();}
            for(int i=0; i<adf.size(); i++){ threads[i].join();}
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
        public DataFrame mint() throws InterruptedException {
            DataFrame newdf = new DataFrame(columnNames, columnTypes);
            Thread[] threads = new Thread[adf.size()];
            for(int i=0; i<adf.size(); i++){
                DataFrame frame = adf.get(i);
                Runnable r =() ->{
                    Value[] line = new Value[columnNames.length];
                    for(int j=0; j < columnNames.length; j++){
                        if(j != index){
                            line[j]=  frame.columns.get(j).min();
                        }
                        else{
                            line[j] = frame.columns.get(j).records.get(0);
                        }
                    }
                    synchronized (newdf) {
                        newdf.add(line);
                    }
                };
                threads[i] = new Thread(r);
            }
            for(int i=0; i<adf.size(); i++){ threads[i].start();}
            for(int i=0; i<adf.size(); i++){ threads[i].join();}
            return newdf;
        }
        @Override
        public DataFrame mean() {
            DataFrame newdf = new DataFrame(columnNames, columnTypes);
            for(DataFrame frame : adf){
                Value[] tmp = new Value[columnNames.length];
                for(int i=0; i < columnNames.length; i++){
                    if(i != index){
                        tmp[i]=  frame.columns.get(i).mean();
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
        public DataFrame meant() throws InterruptedException  {
            DataFrame newdf = new DataFrame(columnNames, columnTypes);
            Thread[] threads = new Thread[adf.size()];
            for(int i=0; i<adf.size(); i++){
                DataFrame frame = adf.get(i);
                Runnable r =() ->{
                    Value[] line = new Value[columnNames.length];
                    for(int j=0; j < columnNames.length; j++){
                        if(j != index){
                            line[j]=  frame.columns.get(j).mean();
                        }
                        else{
                            line[j] = frame.columns.get(j).records.get(0);
                        }
                    }
                    synchronized (newdf) {
                        newdf.add(line);
                    }
                };
                threads[i] = new Thread(r);
            }
            for(int i=0; i<adf.size(); i++){ threads[i].start();}
            for(int i=0; i<adf.size(); i++){ threads[i].join();}
            return newdf;
        }
        @Override
        public DataFrame std() {
            return null;
        }
        @Override
        public DataFrame sum() {
            DataFrame newdf = new DataFrame(columnNames, columnTypes);
            for(DataFrame frame : adf){
                Value[] tmp = new Value[columnNames.length];
                for(int i=0; i < columnNames.length; i++){
                    if(i != index){
                        tmp[i]=  frame.columns.get(i).sum();
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
        public DataFrame sumt() throws InterruptedException {
            DataFrame newdf = new DataFrame(columnNames, columnTypes);
            Thread[] threads = new Thread[adf.size()];
            for(int i=0; i<adf.size(); i++){
                DataFrame frame = adf.get(i);
                Runnable r =() ->{
                    Value[] line = new Value[columnNames.length];
                    for(int j=0; j < columnNames.length; j++){
                        if(j != index){
                            line[j]=  frame.columns.get(j).sum();
                        }
                        else{
                            line[j] = frame.columns.get(j).records.get(0);
                        }
                    }
                    synchronized (newdf) {
                        newdf.add(line);
                    }
                };
                threads[i] = new Thread(r);
            }
            for(int i=0; i<adf.size(); i++){ threads[i].start();}
            for(int i=0; i<adf.size(); i++){ threads[i].join();}
            return newdf;
        }
        @Override
        public DataFrame var() {
            return null;
        }
    }
}