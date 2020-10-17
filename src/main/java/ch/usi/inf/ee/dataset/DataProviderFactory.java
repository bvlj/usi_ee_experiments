package ch.usi.inf.ee.dataset;

import ch.usi.inf.ee.DataSet;

import java.util.Map;

public abstract class DataProviderFactory<T> {

    public abstract Map<String, Provider<T>> getDatasets();

    protected abstract T[] getData(int size);

    public Provider<T> warmupData() {
        return () -> getData(DataSet.SIZE_LARGE);
    }

    public interface Provider<T> {
        T[] getData();
    }
}
