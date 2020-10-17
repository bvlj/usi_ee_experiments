package ch.usi.inf.ee.sorter;

public interface Sorter<T extends Comparable<T>> {

    void sort(final T[] items);
}
