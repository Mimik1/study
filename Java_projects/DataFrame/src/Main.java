import java.util.*;
public class Main {
    public static void main(String[] args) {
        DataFrame df = new  DataFrame(new String[]{"kol1","kol2","kol3"},
                new String[]{"int","int","double"});
        int count = 0;
        int x=0;
        double y=1.5;
        while(10 > count){
            df.addValue(0, 2);
            df.addValue(1, x++);
            df.addValue(2, y++);
            count++;
        }
        /*df.getName(0);
        df.getValues(0);
        df.getName(1);
        df.getValues(1);
        df.getName(2);
        df.getValues(2);*/

        /*System.out.println("");
        System.out.println(df.size());
        System.out.println("");*/


        /*DataFrame nf= df.iloc(0,6);
        nf.getValues(1);
        System.out.println("");
        nf.getValues(2);*/

        System.out.println(df.aframe.size());
        String[] z = new String[]{"kol1", "kol2"};
        DataFrame nf= df.get(z, false);
        //nf.getName(0);
        //nf.getValues(0);
        System.out.println(nf.aframe.size());

        System.out.println(nf.aframe.size());
        System.out.println(df.aframe.size());
    }
}
