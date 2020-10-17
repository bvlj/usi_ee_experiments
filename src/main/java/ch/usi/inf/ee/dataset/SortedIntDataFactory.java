package ch.usi.inf.ee.dataset;

import java.util.HashMap;
import java.util.Map;

import static ch.usi.inf.ee.DataSet.*;

public final class SortedIntDataFactory extends DataProviderFactory<Integer> {

    @Override
    public Map<String, Provider<Integer>> getDatasets() {
        final HashMap<String, Provider<Integer>> map = new HashMap<>();
        map.put(LABEL_SMALL_SORTED, () -> getData(SIZE_SMALL));
        map.put(LABEL_MEDIUM_SORTED, () -> getData(SIZE_MEDIUM));
        map.put(LABEL_LARGE_SORTED, () -> getData(SIZE_LARGE));
        return map;
    }

    @Override
    protected Integer[] getData(int size) {
        final Integer[] data = new Integer[size];
        for (int i = 0; i < size; i++) {
            data[i] = i;
        }
        return data;
    }
}
