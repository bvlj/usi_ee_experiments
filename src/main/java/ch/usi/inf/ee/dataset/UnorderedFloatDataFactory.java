package ch.usi.inf.ee.dataset;

import java.util.HashMap;
import java.util.Map;

import static ch.usi.inf.ee.DataSet.*;

public final class UnorderedFloatDataFactory extends DataProviderFactory<Float> {
    private static final float RATIO = 0.000001f;

    @Override
    public Map<String, Provider<Float>> getDatasets() {
        final HashMap<String, Provider<Float>> map = new HashMap<>();
        map.put(LABEL_SMALL_UNORDERED, () -> getData(SIZE_SMALL));
        map.put(LABEL_MEDIUM_UNORDERED, () -> getData(SIZE_MEDIUM));
        map.put(LABEL_LARGE_UNORDERED, () -> getData(SIZE_LARGE));
        return map;
    }

    @Override
    protected Float[] getData(int size) {
        final Float[] data = new Float[size];
        for (int i = 1; i < size; i += 2) {
            data[i - 1] = (size - i) * RATIO + 1f;
            data[i] = (size - i) * RATIO;
        }
        return data;
    }
}
