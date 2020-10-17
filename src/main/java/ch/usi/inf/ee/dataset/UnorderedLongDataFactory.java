package ch.usi.inf.ee.dataset;

import java.util.HashMap;
import java.util.Map;

import static ch.usi.inf.ee.DataSet.*;

public final class UnorderedLongDataFactory extends DataProviderFactory<Long> {

    @Override
    public Map<String, Provider<Long>> getDatasets() {
        final HashMap<String, Provider<Long>> map = new HashMap<>();
        map.put(LABEL_SMALL_SORTED, () -> getData(SIZE_SMALL));
        map.put(LABEL_MEDIUM_SORTED, () -> getData(SIZE_MEDIUM));
        map.put(LABEL_LARGE_SORTED, () -> getData(SIZE_LARGE));
        return map;
    }

    @Override
    protected Long[] getData(int size) {
        final Long[] data = new Long[size];
        for (int i = 1; i < size; i += 2) {
            data[i - 1] = size - i + 1L;
            data[i] = (long) (size - i);
        }
        return data;
    }
}
