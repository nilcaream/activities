package com.nilcaream.activities;

import com.nilcaream.utilargs.Option;
import com.nilcaream.utilargs.UtilArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Main {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Option(value = "d", alternative = "definitions")
    private File definitionsFile;

    @Option(value = "s", alternative = "sleep")
    private int sleep = 60;

    private DefinitionsReader definitionsReader = new DefinitionsReader();

    private Scheduler scheduler = new Scheduler();

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        UtilArgs.bind(args, main);
        main.go();
    }

    private void go() throws IOException {
        logger.info("Started");
        scheduler.run(definitionsReader.load(definitionsFile), sleep);
    }
}
