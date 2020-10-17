package ch.usi.inf.ee.dataset;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static ch.usi.inf.ee.DataSet.*;

public final class RandomFloatDataFactory extends DataProviderFactory<Float> {

    @Override
    public Map<String, Provider<Float>> getDatasets() {
        final HashMap<String, Provider<Float>> map = new HashMap<>();
        map.put(LABEL_SMALL_RANDOM, () -> getData(SIZE_SMALL));
        map.put(LABEL_MEDIUM_RANDOM, () -> getData(SIZE_MEDIUM));
        map.put(LABEL_LARGE_RANDOM, () -> getData(SIZE_LARGE));
        return map;
    }

    @Override
    protected Float[] getData(int size) {
        final Random random = new Random();
        final Float[] data = new Float[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextFloat();
        }
        return data;
    }
}
