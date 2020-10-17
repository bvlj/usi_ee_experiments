package ch.usi.inf.ee.dataset;

import java.util.HashMap;
import java.util.Map;

import static ch.usi.inf.ee.DataSet.*;

public final class ReverseSortedFloatDataFactory extends DataProviderFactory<Float> {
    private static final float RATIO = 0.000001f;

    @Override
    public Map<String, Provider<Float>> getDatasets() {
        final HashMap<String, Provider<Float>> map = new HashMap<>();
        map.put(LABEL_SMALL_REVERSE_SORTED, () -> getData(SIZE_SMALL));
        map.put(LABEL_MEDIUM_REVERSE_SORTED, () -> getData(SIZE_MEDIUM));
        map.put(LABEL_LARGE_REVERSE_SORTED, () -> getData(SIZE_LARGE));
        return map;
    }

    @Override
    protected Float[] getData(int size) {
        final Float[] data = new Float[size];
        for (int i = size - 1; i >= 0; i--) {
            data[i] = i * RATIO;
        }
        return data;
    }
}
