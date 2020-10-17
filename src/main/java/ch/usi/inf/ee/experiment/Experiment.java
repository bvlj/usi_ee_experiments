package ch.usi.inf.ee.experiment;

import ch.usi.inf.ee.dataset.DataProviderFactory;
import ch.usi.inf.ee.sorter.Sorter;
import ch.usi.inf.ee.util.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Experiment<T extends Comparable<T>> {
    private static final int TEST_SESSIONS = 16;
    private static final int WARM_UP_SESSIONS = 4;
    private static final long NAP_TIME = 1000L;

    private final DataProviderFactory<T> dataProviderFactory;

    public Experiment(DataProviderFactory<T> dataProviderFactory) {
        this.dataProviderFactory = dataProviderFactory;
    }

    public final List<Result> performExperiment(Sorter<T> sorter) {
        runWarmup(sorter);

        // Run experiment
        final List<Result> experimentResults = new ArrayList<>();
        dataProviderFactory.getDatasets().forEach((label, provider) -> {
            // Force garbage collector to avoid it interfering with the test
            System.gc();
            takeANap();

            final T[] data = provider.getData();
            long avgTiming = 0;
            for (int i = 0; i < TEST_SESSIONS; i++) {
                avgTiming += Stopwatch.measure(() -> sorter.sort(data));
            }

            experimentResults.add(new Result(label, avgTiming / TEST_SESSIONS));
        });
        return experimentResults;
    }

    private void runWarmup(Sorter<T> sorter) {
        final T[] warmupData = dataProviderFactory.warmupData().getData();
        for (int i = 0; i < WARM_UP_SESSIONS; i++) {
            sorter.sort(warmupData);
        }
    }

    private void takeANap() {
        try {
            Thread.sleep(NAP_TIME);
        } catch (InterruptedException ignored) {
        }
    }

    @SuppressWarnings("unused")
    public static class Result implements Comparable<Result> {
        private final String label;
        private final long avgTime;

        public Result(String label, long avgTime) {
            this.label = label;
            this.avgTime = avgTime;
        }

        public String getLabel() {
            return label;
        }

        public long getAverageTime() {
            return avgTime;
        }

        @Override
        public int compareTo(Result o) {
            return Long.compare(avgTime, o.avgTime);
        }

        @Override
        public String toString() {
            return "Experiment.Result{" +
                    "avgTime=" + avgTime +
                    '}';
        }
    }
}
