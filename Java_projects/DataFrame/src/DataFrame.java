import javafx.util.*;
import java.util.*;

public class DataFrame implements Cloneable {
    public int size, sizeOfColumn=2;
    ArrayList<Pair<String, Column>> aframe = new ArrayList<Pair<String, Column>>(size);

    public DataFrame(String[] names, String[] types) {
        size = names.length;
        for (int i = 0; i < size; i++) {
            if(types[i].equals("int")) {
                Pair pair = new Pair(names[i], new Column<Integer>());
                aframe.add(pair);
            }
            if(types[i].equals("double")) {
                Pair pair = new Pair(names[i], new Column<Double>());
                aframe.add(pair);
            }
            if(types[i].equals("String")) {
                Pair pair = new Pair(names[i], new Column<String>());
                aframe.add(pair);
            }
        }
    }
    public DataFrame(DataFrame oldFrame){
        size= oldFrame.size;
        sizeOfColumn= oldFrame.sizeOfColumn;
        aframe= oldFrame.aframe;
    }

    public void getName(int i){
        System.out.println(aframe.get(i).getKey());
    }
    public void getValues(int i){
        for(int j=0; j<sizeOfColumn;j++ ){
            System.out.println(aframe.get(i).getValue().column.get(j));
        }
    }
    public <T> void addValue(int i, T value){
        aframe.get(i).getValue().column.add(value);
        sizeOfColumn = aframe.get(i).getValue().column.size();
    }


    //methods
    //^^^^^^^^^^^^^^1^^^^^^^^^^^^^^
    public int size(){
        return sizeOfColumn;
    }
    //^^^^^^^^^^^^^^2^^^^^^^^^^^^^^
    public <T> ArrayList<T> get(String colname){
        for(int i=0; i<size; i++){
            if(aframe.get(i).getKey().equals(colname)){
                return aframe.get(i).getValue().column;
            }
        }
        return null;
    }
    //^^^^^^^^^^^^^^3^^^^^^^^^^^^^^
    public DataFrame get(String[] cols, boolean copy){
            DataFrame newFrame;
            newFrame = new DataFrame(new String[] {}, new String[] {});
            newFrame.size = cols.length;
            newFrame.sizeOfColumn = this.sizeOfColumn;
            int count=0;
            if(copy){
                for(int i=0; i< size; i++){
                    if(count < cols.length) {
                        if( cols[count].equals(aframe.get(i).getKey()) ) {
                            Pair<String,Column> tmp =  new Pair<String,Column>(cols[count], this.aframe.get(i).getValue());
                            newFrame.aframe.add(tmp);
                        }
                        else{
                            count++;
                        }
                    }
                }
            }
            else{
                for(int i=0; i<size; i++){
                    if(count < cols.length) {
                        if( cols[count].equals(aframe.get(i).getKey()) ) {
                            newFrame.aframe.add(aframe.get(i));
                        }
                        else{
                            count++;
                        }
                    }
                }
            }
            return newFrame;
    }
    //^^^^^^^^^^^^^^5^^^^^^^^^^^^^^
    public DataFrame iloc(int from, int to){
        DataFrame newFrame = new DataFrame(this);
        int count=0;
        while(count != from){
            for(int count2= 0; count2<size; count2++){
                newFrame.aframe.get(count2).getValue().column.remove(0);
            }
            count++;
            newFrame.sizeOfColumn --;
        }
        int comp= to-from;
        count=count+comp;
        while(count !=sizeOfColumn-1){
            for(int count2= 0; count2<size; count2++){
                newFrame.aframe.get(count2).getValue().column.remove(comp+1);
            }
            count++;
            newFrame.sizeOfColumn --;
        }
        return newFrame;
    }
    //^^^^^^^^^^^^^^4^^^^^^^^^^^^^^
    public DataFrame iloc(int i){ return iloc(i,i); }
}
