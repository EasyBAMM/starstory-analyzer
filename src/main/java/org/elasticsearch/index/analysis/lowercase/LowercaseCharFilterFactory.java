package org.elasticsearch.index.analysis.lowercase;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractCharFilterFactory;

import java.io.Reader;

public class LowercaseCharFilterFactory extends AbstractCharFilterFactory {

    public LowercaseCharFilterFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(name);
    }

    @Override
    public Reader create(Reader reader) {
        return new LowercaseCharFilter(reader);
    }
}
