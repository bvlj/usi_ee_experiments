package ch.usi.inf.ee.dataset;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static ch.usi.inf.ee.DataSet.*;

public final class RandomLongDataFactory extends DataProviderFactory<Long> {
    private static final long MIN_VALUE = 1L << 32;
    private static final long MAX_VALUE = 1L << 56;

    @Override
    public Map<String, Provider<Long>> getDatasets() {
        final HashMap<String, Provider<Long>> map = new HashMap<>();
        map.put(LABEL_SMALL_RANDOM, () -> getData(SIZE_SMALL));
        map.put(LABEL_MEDIUM_RANDOM, () -> getData(SIZE_MEDIUM));
        map.put(LABEL_LARGE_RANDOM, () -> getData(SIZE_LARGE));
        return map;
    }

    @Override
    protected Long[] getData(int size) {
        return new Random().longs(MIN_VALUE, MAX_VALUE)
                .limit(size)
                .boxed()
                .toArray(Long[]::new);
    }
}
