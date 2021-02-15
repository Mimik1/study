import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},new String[]{"int", "double", "MyCustomType"});
        DataFrame sdf = new DataFrame("/home/dominik/Downloads/groupby.csv", new String[]{"int","int","int","int"});
        sdf.print();

        }
}
