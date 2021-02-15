import java.io.IOException;
import java.util.HashMap;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.chart.CategoryAxis;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, CloneNotSupportedException, InterruptedException {
        DataFrame df = new DataFrame("/home/dominik/Downloads/groupby.csv", new String[]{"StringValue", "StringValue", "DoubleValue", "DoubleValue" });
        long start = System.currentTimeMillis();
        df.groupBy("id").max().print(0);
        long firstTime = System.currentTimeMillis()-start;
        System.out.println(firstTime);
        df.groupBy("id").maxt().print(0);
        System.out.println(System.currentTimeMillis()-start-firstTime);

        //df.groupBy("id").meant().print(0);
    }
}
