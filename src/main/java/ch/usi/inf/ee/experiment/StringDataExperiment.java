package ch.usi.inf.ee.experiment;

import ch.usi.inf.ee.dataset.DataProviderFactory;

public class StringDataExperiment extends Experiment<String> {

    public StringDataExperiment(DataProviderFactory<String> factory) {
        super(factory);
    }
}
