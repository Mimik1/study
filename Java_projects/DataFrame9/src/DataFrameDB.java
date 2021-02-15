import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class DataFrameDB extends DataFrame {
    private String cut(){
        String tmp = "";
        for(int i=0; i<columnNames.length; i++){
            tmp += (" " + columnNames[i]);
            switch (columnTypes[i]) {
                case "IntegerValue":
                    tmp += " INT NOT NULL ";
                    break;
                case "DoubleValue":
                    tmp += " REAL NOT NULL ";
                    break;
                case "StringValue":
                    tmp += " TEXT NOT NULL ";
                    break;
            }
            if(i != columnNames.length-1) tmp += ",";
        }
        return tmp;
    }
    private String types;
    private String name = new String("name1");
    Connection conn = connect(name);
    public static Connection connect(String base ) {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+base+".db");
            System.out.println("Połączyłem się z bazą "+base);
        } catch (Exception e) {
            System.err.println("Błąd w połączeniu z bazą: \n" + e.getMessage());
            return null;
        }
        return conn;
    }
    public void make() {
        Statement stat = null;
        try {
            stat = conn.createStatement();
            String tabeleSQL = "CREATE TABLE " + name + "(" + this.types;
            tabeleSQL += ");";
            stat.executeUpdate(tabeleSQL);
            stat.close();
        } catch (SQLException e) {
            System.out.println("Nie mogę stworzyć tabeli" + e.getMessage());
        }
    }
    public void delate(){
        try {
            Statement stat = null;
            stat = conn.createStatement();
            stat.executeUpdate("DROP TABLE " + name);
            System.out.println("Baza usunieta");
        }
        catch (SQLException e) {
            System.out.println("Nie usunieto tabeli" + e.getMessage());
        }
    }
    public void add(Value[] line) {
        Statement stat = null;
        try {
            stat = conn.createStatement();
            String dodajSQL = "INSERT INTO " + name + " VALUES (";
            for(int i=0; i<line.length; i++) {
                if(this.columnTypes[i] == "StringValue"){
                    dodajSQL += "'";
                    dodajSQL += line[i].toString();
                    dodajSQL += "'";
                }
                else {
                    dodajSQL += line[i].toString();
                }
                if(i == line.length-1) dodajSQL += ");";
                else dodajSQL += ", ";
            }
            stat.executeUpdate(dodajSQL);
            stat.close();
            //System.out.println("Polecenie: \n" + dodajSQL + "\n wykonane.");
        } catch (Exception e) {
            System.out.println("Nie mogę dodać danych " + e.getMessage());
        }
    }
    public void searchDB(String baza, Connection conn) {
        Statement stat = null;
        try {
            Class.forName("org.sqlite.JDBC");
            stat = conn.createStatement();
            /*String szukajSQL = "SELECT * FROM " + baza
                    + " WHERE " + pole + "=='" + wartosc + "';";*/
            String szukajSQL = "SELECT * FROM " + baza;


            ResultSet wynik = stat.executeQuery(szukajSQL);
            System.out.println("Wynik polecenia:\n" + szukajSQL);

            while (wynik.next()) {
                System.out.println( this.columnNames[0] +" "+ wynik.getString(this.columnNames[0]));
                System.out.println( this.columnNames[1] +" "+ wynik.getString(this.columnNames[1]));
                System.out.println( this.columnNames[2] +" "+ wynik.getString(this.columnNames[2]));
                System.out.println( this.columnNames[3] +" "+ wynik.getString(this.columnNames[3]));
                System.out.println(" ---------------------- ");

            }
            wynik.close();
            stat.close();
        } catch (Exception e) {
            System.out.println("Nie mogę wyszukać danych " + e.getMessage());
        }

    }
    public void groupbyDB(String fun, String what, String bywhat) {
        Statement stat = null;
        try {
            Class.forName("org.sqlite.JDBC");
            stat = conn.createStatement();
            /*String szukajSQL = "SELECT * FROM " + baza
                    + " WHERE " + pole + "=='" + wartosc + "';";*/
            String szukajSQL = "SELECT "+ bywhat + ", max(total), max(val) FROM " + name + " GROUP BY " + bywhat +";";

            ResultSet wynik = stat.executeQuery(szukajSQL);
            System.out.println("Wynik polecenia:\n" + szukajSQL);

            while (wynik.next()) {
                System.out.println( this.columnNames[0] +" "+ wynik.getString(this.columnNames[0]));
                //System.out.println( this.columnNames[1] +" "+ wynik.getString(this.columnNames[1]));
                System.out.println( this.columnNames[2] +" "+ wynik.getString(this.columnNames[2]));
                System.out.println( this.columnNames[3] +" "+ wynik.getString(this.columnNames[3]));
                System.out.println(" ---------------------- ");

            }
            wynik.close();
            stat.close();
        } catch (Exception e) {
            System.out.println("Nie mogę wyszukać danych " + e.getMessage());
        }

    }
    public DataFrame toDataFrame(){
        DataFrame ndf = new DataFrame(this.columnNames , this.columnTypes);
        Statement stat = null;
        try {
            Class.forName("org.sqlite.JDBC");
            stat = conn.createStatement();
            String szukajSQL = "SELECT * FROM " + name;


            ResultSet wynik = stat.executeQuery(szukajSQL);
            Value[] cells = new Value[columnTypes.length];
            while (wynik.next()) {
                for(int i=0; i<columnTypes.length; i++) {
                    switch (columnTypes[i]) {
                        case "IntegerValue":
                            cells[i] = new IntegerValue(wynik.getInt(this.columnNames[i]));
                            break;
                        case "DoubleValue":
                            cells[i] = new DoubleValue(wynik.getDouble(this.columnNames[i]));
                            break;
                        case "StringValue":
                            cells[i] = new StringValue(wynik.getString(this.columnNames[i]));
                            break;
                    }
                }
                ndf.add(cells);
            }
            wynik.close();
            stat.close();
        } catch (Exception e) {
            System.out.println("Nie mogę wyszukać danych " + e.getMessage());
        }
        return ndf;
    }
    public DataFrame select(String pole, String wartosc){
        DataFrame ndf = new DataFrame(this.columnNames , this.columnTypes);
        Statement stat = null;
        try {
            Class.forName("org.sqlite.JDBC");
            stat = conn.createStatement();
            String szukajSQL = "SELECT * FROM " + name
                    + " WHERE " + pole + "=='" + wartosc + "';";


            ResultSet wynik = stat.executeQuery(szukajSQL);
            Value[] cells = new Value[columnTypes.length];
            while (wynik.next()) {
                for(int i=0; i<columnTypes.length; i++) {
                    switch (columnTypes[i]) {
                        case "IntegerValue":
                            cells[i] = new IntegerValue(wynik.getInt(this.columnNames[i]));
                            break;
                        case "DoubleValue":
                            cells[i] = new DoubleValue(wynik.getDouble(this.columnNames[i]));
                            break;
                        case "StringValue":
                            cells[i] = new StringValue(wynik.getString(this.columnNames[i]));
                            break;
                    }
                }
                ndf.add(cells);
            }
            wynik.close();
            stat.close();
        } catch (Exception e) {
            System.out.println("Nie mogę wyszukać danych " + e.getMessage());
        }
        return ndf;
    }
    DataFrameDB(DataFrame odf){
        this.columnNames = odf.columnNames;
        this.columnTypes = odf.columnTypes;
        types = cut();

        make();
        //for(int i=0; i< odf.size(); i++){
        for(int i=0; i< 20; i++){
            add(odf.getLine(i));
        }
    }
    DataFrameDB(String csv, String[] columnTypes){
        try(BufferedReader br = new BufferedReader(new FileReader(csv))){
            String line = br.readLine();
            String[] header = line.split(",");
            columnNames = header;
            this.columnTypes = columnTypes;
            types = cut();
            make();
            int j=0;
            Value[] cells2 = new Value[columnTypes.length];
                while ((line = br.readLine()) != null && j++<20) {
                    String[] cells1 = line.split(",");
                    for (int i = 0; i < cells1.length; i++) {
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
    public int size(){
        int tmp=0;
        Statement stat = null;
        try {
            Class.forName("org.sqlite.JDBC");
            stat = conn.createStatement();
            String szukajSQL = "SELECT count(*) FROM " + name + ";";
            ResultSet wynik = stat.executeQuery(szukajSQL);
            tmp = wynik.getInt(1);
            wynik.close();
            stat.close();
        } catch (Exception e) {
            System.out.println("Nie mogę wyszukać danych " + e.getMessage());
        }
        return tmp;
    }
}
