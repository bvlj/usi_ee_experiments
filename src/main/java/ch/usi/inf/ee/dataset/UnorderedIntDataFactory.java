package ch.usi.inf.ee.dataset;

import java.util.HashMap;
import java.util.Map;

import static ch.usi.inf.ee.DataSet.*;

public final class UnorderedIntDataFactory extends DataProviderFactory<Integer> {

    @Override
    public Map<String, Provider<Integer>> getDatasets() {
        final HashMap<String, Provider<Integer>> map = new HashMap<>();
        map.put(LABEL_SMALL_UNORDERED, () -> getData(SIZE_SMALL));
        map.put(LABEL_MEDIUM_UNORDERED, () -> getData(SIZE_MEDIUM));
        map.put(LABEL_LARGE_UNORDERED, () -> getData(SIZE_LARGE));
        return map;
    }

    @Override
    protected Integer[] getData(int size) {
        final Integer[] data = new Integer[size];
        for (int i = 1; i < size; i += 2) {
            data[i - 1] = size - i + 1;
            data[i] = size - i;
        }
        return data;
    }
}
