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

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, CloneNotSupportedException {
        DataFrame df = new DataFrame("/home/dominik/Downloads/groupby.csv", new String[]{"StringValue", "StringValue", "DoubleValue", "DoubleValue" });
        df.print(20);
        DataFrameDB dfdb0 = new DataFrameDB(df);
        DataFrame ndf = dfdb0.toDataFrame();
        ndf.print(0);
        dfdb0.delate();
        DataFrameDB dfdb = new DataFrameDB("/home/dominik/Downloads/groupby.csv", new String[]{"StringValue", "StringValue", "DoubleValue", "DoubleValue" });
        dfdb.toDataFrame().print(0);
        dfdb.delate();
    }
}
