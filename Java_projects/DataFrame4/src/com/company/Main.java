package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},new String[]{"int", "double", "MyCustomType"});
        DataFrame sdf = new DataFrame("/home/dominik/Downloads/sparse.csv", new String[]{"int","int","int"});
        SparseDataFrame nsdf = new SparseDataFrame(sdf, "0");
        nsdf.print();
    }
}
