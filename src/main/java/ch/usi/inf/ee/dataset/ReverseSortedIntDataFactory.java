package ch.usi.inf.ee.dataset;

import java.util.HashMap;
import java.util.Map;

import static ch.usi.inf.ee.DataSet.*;

public final class ReverseSortedIntDataFactory extends DataProviderFactory<Integer> {

    @Override
    public Map<String, Provider<Integer>> getDatasets() {
        final HashMap<String, Provider<Integer>> map = new HashMap<>();
        map.put(LABEL_SMALL_REVERSE_SORTED, () -> getData(SIZE_SMALL));
        map.put(LABEL_MEDIUM_REVERSE_SORTED, () -> getData(SIZE_MEDIUM));
        map.put(LABEL_LARGE_REVERSE_SORTED, () -> getData(SIZE_LARGE));
        return map;
    }

    @Override
    protected Integer[] getData(int size) {
        final Integer[] data = new Integer[size];
        for (int i = size - 1; i >= 0; i--) {
            data[i] = i;
        }
        return data;
    }
}
