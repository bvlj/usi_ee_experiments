package ch.usi.inf.ee;

public final class DataSet {
    public static final String LABEL_SMALL_SORTED = "Small sorted array";
    public static final String LABEL_MEDIUM_SORTED = "Medium sorted array";
    public static final String LABEL_LARGE_SORTED = "Large sorted array";

    public static final String LABEL_SMALL_REVERSE_SORTED = "Small reverse sorted array";
    public static final String LABEL_MEDIUM_REVERSE_SORTED = "Medium reverse sorted array";
    public static final String LABEL_LARGE_REVERSE_SORTED = "Large reverse sorted array";

    public static final String LABEL_SMALL_UNORDERED = "Small unordered array";
    public static final String LABEL_MEDIUM_UNORDERED = "Medium unordered array";
    public static final String LABEL_LARGE_UNORDERED = "Large unordered array";


    public static final String LABEL_SMALL_RANDOM = "Small random array";
    public static final String LABEL_MEDIUM_RANDOM = "Medium random array";
    public static final String LABEL_LARGE_RANDOM = "Large random array";

    public static final int SIZE_SMALL = 1 << 4;
    public static final int SIZE_MEDIUM = 1 << 8;
    public static final int SIZE_LARGE = 1 << 10;

    private DataSet() {
    }
}
