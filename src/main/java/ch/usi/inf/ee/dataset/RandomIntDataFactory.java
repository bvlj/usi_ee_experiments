package ch.usi.inf.ee.dataset;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static ch.usi.inf.ee.DataSet.*;

public final class RandomIntDataFactory extends DataProviderFactory<Integer> {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 1 << 24;

    @Override
    public Map<String, Provider<Integer>> getDatasets() {
        final HashMap<String, Provider<Integer>> map = new HashMap<>();
        map.put(LABEL_SMALL_RANDOM, () -> getData(SIZE_SMALL));
        map.put(LABEL_MEDIUM_RANDOM, () -> getData(SIZE_MEDIUM));
        map.put(LABEL_LARGE_RANDOM, () -> getData(SIZE_LARGE));
        return map;
    }

    @Override
    protected Integer[] getData(int size) {
        return new Random().ints(MIN_VALUE, MAX_VALUE)
                .limit(size)
                .boxed()
                .toArray(Integer[]::new);
    }
}
