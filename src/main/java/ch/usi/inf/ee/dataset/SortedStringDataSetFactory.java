package ch.usi.inf.ee.dataset;

import ch.usi.inf.ee.util.StringProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static ch.usi.inf.ee.DataSet.*;

public class SortedStringDataSetFactory extends DataProviderFactory<String> {
    private final Map<Integer, String[]> strings;

    public SortedStringDataSetFactory(Map<Integer, String[]> strings) {
        this.strings = strings;
    }

    @Override
    public Map<String, Provider<String>> getDatasets() {
        final HashMap<String, Provider<String>> map = new HashMap<>();
        map.put(LABEL_SMALL_SORTED, () -> getData(SIZE_SMALL));
        map.put(LABEL_MEDIUM_SORTED, () -> getData(SIZE_MEDIUM));
        map.put(LABEL_LARGE_SORTED, () -> getData(SIZE_LARGE));
        return map;
    }

    @Override
    protected String[] getData(int size) {
        final String[] data = Arrays.copyOf(strings.get(size), size);
        Arrays.sort(data);
        return data;
    }
}
