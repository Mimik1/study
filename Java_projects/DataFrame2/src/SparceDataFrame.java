import java.util.ArrayList;

class SparseDataFrame extends DataFrame {
    private String hide;
    private int size;

    public SparseDataFrame(DataFrame dfToParse, String hide) {
        this.hide=hide;
        size =dfToParse.size();

        ArrayList<COOValue> tmp = new ArrayList<>();

        columns = new ArrayList<>();
        for (int i=0; i<dfToParse.columns.size(); i++) {
            columns.add(new Column(dfToParse.columns.get(i).name, dfToParse.columns.get(i).type));

            for (int j=0; j<dfToParse.size(); j++) {
                if (! dfToParse.columns.get(i).records.get(j).equals(hide)) {
                    tmp.add(new COOValue(j, dfToParse.columns.get(i).records.get(j)));
                }
            }

            for (COOValue element : tmp) {
                columns.get(i).records.add(element);
            }
            tmp.clear();
        }
    }

    public int size() {
        return size;
    }

    private int getCOOIndex(int indexCheckingColumn, int checkingPlace) {
        ArrayList<Object> checkingColumn = columns.get(indexCheckingColumn).records;
        for (int k=0; k<checkingColumn.size(); k++) {
            COOValue tmp = (COOValue) checkingColumn.get(k);
            if (tmp.getPlace() == checkingPlace) {
                return k;
            }
        }
        return -1;
    }


    public void print() {
        DataFrame tmpDf = this.toDense();
        tmpDf.print();
    }

    public DataFrame toDense() {
        DataFrame newDf = new DataFrame();
        newDf.columns = new ArrayList<>();

        for (int i=0; i<columns.size(); i++) {
            newDf.columns.add(new Column(columns.get(i).name, columns.get(i).type));
        }

        Object newDFLine[] = new Object[columns.size()];
        for(int i = 0; i< size; i++) {
            for (int j = 0; j < columns.size(); j++) {
                if (getCOOIndex(j, i) != -1) {
                    COOValue foundC00Value = (COOValue)columns.get(j).records.get(getCOOIndex(j, i));
                    newDFLine[j] = foundC00Value.getValue();
                }
                else {
                    newDFLine[j]=hide;
                }
            }
            newDf.add(newDFLine);
        }
        return newDf;
    }
}
