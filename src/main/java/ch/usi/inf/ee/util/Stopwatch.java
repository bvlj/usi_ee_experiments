package ch.usi.inf.ee.util;

public final class Stopwatch {

    private Stopwatch() {
    }

    public synchronized static long measure(Runnable runnable) {
        final long begin = System.nanoTime();
        runnable.run();
        final long end = System.nanoTime();

        return end - begin;
    }
}
