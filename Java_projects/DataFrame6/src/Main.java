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


public class Main extends Application {
    @Override
    public void start(final Stage stage) throws Exception {
        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        DataFrame dfp = new DataFrame("/home/dominik/Downloads/groupby.csv");
        DataFrame df = dfp.groupBy("id").max();
        String a = df.columns.get(0).records.get(0).getValue();
        series.getData().add(new XYChart.Data(df.columns.get(3).records.get(0).getValue(), df.columns.get(2).records.get(0).getValue()));
        series.getData().add(new XYChart.Data(df.columns.get(3).records.get(1).getValue(), df.columns.get(2).records.get(1).getValue()));


        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
        /*stage.setTitle("Data Frame");
        DataFrame df = new DataFrame();
        Button button = new Button("Choose");
        Label chosen = new Label();
        button.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                String fileAsString = file.toString();
                try {
                    df = new DataFrame(fileAsString);

                } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
                chosen.setText("Chosen: " + fileAsString);
            } else {
                chosen.setText(null);
            }
        });


        VBox layout = new VBox(10, button, chosen);
        layout.setMinWidth(400);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        stage.setScene(new Scene(layout));
        stage.show();
        draw(df);

    }
}
/*public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, CloneNotSupportedException {
        DataFrame df = new DataFrame("/home/dominik/Downloads/groupby.csv", new String[]{"StringValue", "StringValue", "DoubleValue", "DoubleValue"});
    }
}*/
