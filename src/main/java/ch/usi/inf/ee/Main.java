package ch.usi.inf.ee;

import ch.usi.inf.ee.dataset.*;
import ch.usi.inf.ee.experiment.Experiment;
import ch.usi.inf.ee.experiment.FloatDataExperiment;
import ch.usi.inf.ee.experiment.IntDataExperiment;
import ch.usi.inf.ee.experiment.LongDataExperiment;
import ch.usi.inf.ee.experiment.StringDataExperiment;
import ch.usi.inf.ee.sorter.BubbleSortPassPerItem;
import ch.usi.inf.ee.sorter.BubbleSortUntilNoChange;
import ch.usi.inf.ee.sorter.BubbleSortWhileNeeded;
import ch.usi.inf.ee.sorter.Sorter;
import ch.usi.inf.ee.util.StringProvider;
import com.jakewharton.fliptables.FlipTable;
import com.jakewharton.fliptables.FlipTableConverters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class Main {
    private static final byte[] NEW_LINE = {(byte) '\n'};
    private static final String[] HEADERS = {
            BubbleSortPassPerItem.class.getSimpleName(),
            BubbleSortUntilNoChange.class.getSimpleName(),
            BubbleSortWhileNeeded.class.getSimpleName()
    };
    private final File outputFile;

    private Main() {
        final String dateStr = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
        final String outFileName = String.format("results_%s.txt", dateStr);
        this.outputFile = new File(outFileName);

        intExperiments();
        longExperiments();
        floatExperiments();
        stringExperiments();

        System.out.printf("Done! Results written to %s%n", outputFile.getAbsolutePath());
    }

    public static void main(String[] args) {
        new Main();
    }

    private void intExperiments() {
        final Sorter<Integer>[] sorters = new Sorter[]{
                new BubbleSortPassPerItem<>(),
                new BubbleSortUntilNoChange<>(),
                new BubbleSortWhileNeeded<>()
        };
        experiment(
                "Sorted Int arrays",
                new IntDataExperiment(new SortedIntDataFactory()),
                sorters
        );
        experiment(
                "Reverse sorted Int arrays",
                new IntDataExperiment(new ReverseSortedIntDataFactory()),
                sorters
        );
        experiment(
                "Unordered Int arrays",
                new IntDataExperiment(new UnorderedIntDataFactory()),
                sorters
        );
        experiment(
                "Random Int arrays",
                new IntDataExperiment(new RandomIntDataFactory()),
                sorters
        );
    }

    private void longExperiments() {
        final Sorter<Long>[] sorters = new Sorter[]{
                new BubbleSortPassPerItem<>(),
                new BubbleSortUntilNoChange<>(),
                new BubbleSortWhileNeeded<>()
        };
        experiment(
                "Sorted Long arrays",
                new LongDataExperiment(new SortedLongDataFactory()),
                sorters
        );
        experiment(
                "Reverse sorted Long arrays",
                new LongDataExperiment(new ReverseSortedLongDataFactory()),
                sorters
        );
        experiment(
                "Unordered Long arrays",
                new LongDataExperiment(new UnorderedLongDataFactory()),
                sorters
        );
        experiment(
                "Random Long arrays",
                new LongDataExperiment(new RandomLongDataFactory()),
                sorters
        );
    }

    private void floatExperiments() {
        final Sorter<Float>[] sorters = new Sorter[]{
                new BubbleSortPassPerItem<>(),
                new BubbleSortUntilNoChange<>(),
                new BubbleSortWhileNeeded<>()
        };
        experiment(
                "Sorted Float arrays",
                new FloatDataExperiment(new SortedFloatDataFactory()),
                sorters
        );
        experiment(
                "Reverse sorted Float arrays",
                new FloatDataExperiment(new ReverseSortedFloatDataFactory()),
                sorters
        );
        experiment(
                "Unordered Float arrays",
                new FloatDataExperiment(new UnorderedFloatDataFactory()),
                sorters
        );
        experiment(
                "Random Float arrays",
                new FloatDataExperiment(new RandomFloatDataFactory()),
                sorters
        );
    }

    private void stringExperiments() {
        final Sorter<String>[] stringSorters = new Sorter[]{
                new BubbleSortPassPerItem<>(),
                new BubbleSortUntilNoChange<>(),
                new BubbleSortWhileNeeded<>()
        };
        final int[] sizes = new int[]{DataSet.SIZE_SMALL, DataSet.SIZE_MEDIUM, DataSet.SIZE_LARGE};
        for (final int size : sizes) {
            final Map<Integer, String[]> strings = StringProvider.getStringsMap(size, sizes);
            experiment(
                    String.format("Sorted String arrays (size=%d)", size),
                    new StringDataExperiment(new SortedStringDataSetFactory(strings)),
                    stringSorters
            );
            experiment(
                    String.format("Reverse Sorted String arrays (size=%d)", size),
                    new StringDataExperiment(new ReverseSortedStringDataSetFactory(strings)),
                    stringSorters
            );
            experiment(
                    String.format("Unordered String arrays (size=%d)", size),
                    new StringDataExperiment(new UnorderedStringDataSetFactory(strings)),
                    stringSorters
            );
            experiment(
                    String.format("Random String arrays (size=%d)", size),
                    new StringDataExperiment(new RandomStringDataSetFactory(strings)),
                    stringSorters
            );
        }
    }

    private <T extends Comparable<T>> void experiment(String name, Experiment<T> experiment,
                                                      Sorter<T>[] sorters) {
        final String[][] sortersResults = new String[1][];
        sortersResults[0] = new String[sorters.length];

        for (int i = 0; i < sorters.length; i++) {
            final List<Experiment.Result> testResults = experiment.performExperiment(sorters[i]);
            testResults.sort(Experiment.Result::compareTo);

            sortersResults[0][i] = FlipTableConverters.fromIterable(testResults, Experiment.Result.class);
            testResults.clear();
        }

        final String testResult = FlipTable.of(
                HEADERS,
                sortersResults
        );

        try (FileOutputStream oStream = new FileOutputStream(outputFile, true)) {
            oStream.write(name.getBytes());
            oStream.write(NEW_LINE);
            oStream.write(testResult.getBytes());
            oStream.write(NEW_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
