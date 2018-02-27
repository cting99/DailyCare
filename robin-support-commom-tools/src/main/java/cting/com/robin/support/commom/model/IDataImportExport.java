package cting.com.robin.support.commom.model;

import java.util.ArrayList;

public interface IDataImportExport<I> {
    void exportData(ArrayList<I> items);
    void importData();
}
