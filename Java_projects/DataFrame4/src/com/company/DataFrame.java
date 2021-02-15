package com.company;
import java.io.*;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.io.BufferedReader;
import java.io.FileReader;


public class DataFrame implements Cloneable{
    protected ArrayList<Column> columns;

    DataFrame() {}

    public DataFrame(String[] columnNames, String[] columnTypes) {
        if(columnNames.length != columnTypes.length){
            throw new IllegalArgumentException("Number of columnNames and columnTypes are not equal.");
        }

        columns = new ArrayList<>();

        for (int i=0; i<columnNames.length; i++) {
            columns.add(new Column(columnNames[i], columnTypes[i]));
        }
    }

    /*public DataFrame(String fileName, String[] columnTypes, String[] columnNames) throws IOException {
        FileInputStream fStream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fStream));

        if(columnNames != null){
            columns = new ArrayList<>();
            for (int i=0; i<columnNames.length; i++) {
                columns.add(new Column(columnNames[i], columnTypes[i]));
            }
        }

        String strLine;
        String splited;
        String[] splitedArray;

        if(columnNames == null){
            strLine = br.readLine();
            splited = strLine;
            splitedArray = splited.split(",");

            columns = new ArrayList<>();
            for (int i=0; i<splitedArray.length; i++) {
                columns.add(new Column(splitedArray[i], columnTypes[i]));
            }
        }

        while ((strLine = br.readLine()) != null)   {
            splited = strLine;
            splitedArray = splited.split(",");
            add(splitedArray);
        }
        br.close();
    }*/


    public DataFrame(String fileName, String[] columnTypes) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){

            columns = new ArrayList<>();
            String line = br.readLine();
            String[] header = line.split(",");
            for (int i=0; i<columnTypes.length; i++) {
                System.out.println();
                columns.add(new Column(header[i], columnTypes[i]));
            }
            while((line = br.readLine()) != null){
                String[] cells = line.split(",");
                add(cells);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    private DataFrame(ArrayList<Column> columns) {
        this.columns=columns;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void add(Class<? extends Value> [] args) throws IllegalArgumentException {
        if(args.length != columns.size()){
            throw new IllegalArgumentException("Wrong number of arguments");
        }

        for (int i=0; i<args.length; i++){
            columns.get(i).records.add(args[i]);
        }
    }

    public int size(){
        return columns.get(0).colsize();
    }

    private Column getcolumn(String colname){
        for(Column col : columns){
            if(col.name.equals(colname)){
                return col;
            }
        }
        throw new IllegalArgumentException("Column not found");
    }

    public DataFrame get(String [] cols, boolean copy) throws CloneNotSupportedException{
        ArrayList<Column> chosenColumns = new ArrayList<>();
        for(String colName : cols)
        {
            chosenColumns.add(getcolumn(colName));
        }
        if(copy){
            DataFrame newDF = new DataFrame(chosenColumns);
            return (DataFrame) newDF.clone();
        }
        else {
            return new DataFrame(chosenColumns);
        }
    }

    public DataFrame iloc(int i){
        if(i<0 || i>size()){
            throw new IndexOutOfBoundsException(i + " is out of bound.");
        }

        Object[] chosenLine = new Object[columns.size()];
        String[] columnNames = new String[columns.size()];
        String[] columnTypes = new String[columns.size()];

        for (int j=0; j<columns.size(); j++) {
            chosenLine[j]=columns.get(j).records.get(i);
            columnNames[j]=columns.get(j).name;
            columnTypes[j]=columns.get(j).type;
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

        Value[] chosenLine = new Object[columns.size()];
        String[] columnNames = new String[columns.size()];
        String[] columnTypes = new String[columns.size()];

        for (int j=0; j<columns.size(); j++) {
            columnNames[j]=columns.get(j).name;
            columnTypes[j]=columns.get(j).type;
        }

        DataFrame newDF = new DataFrame(columnNames, columnTypes);
        for (int i=from; i<=to; i++) {
            for (int j=0; j<columns.size(); j++) {
                chosenLine[j]=columns.get(j).records.get(i);
            }
            newDF.add(chosenLine);
        }
        return newDF;
    }

    private void printTableHeader() {
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

}