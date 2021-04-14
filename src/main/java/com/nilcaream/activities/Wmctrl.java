package com.nilcaream.activities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Wmctrl implements WindowManager {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<Window> get() {
        return execute("env DISPLAY=:0 wmctrl -pl").output.stream()
                .map(this::map)
                .filter(x -> !"xfce4-panel".equalsIgnoreCase(x.getTitle()))
                .filter(x -> !"Desktop".equalsIgnoreCase(x.getTitle()))
                .peek(x -> logger.info(x.toString()))
                .collect(Collectors.toList());
    }

    @Override
    public void close(Window window) {
        Result result = execute("env DISPLAY=:0 wmctrl -i -c " + window.getWid());

        if (result.exitValue == 0) {
            logger.info("Closed {}", window);
            String title = window.getTitle().replace(" ", "_");
            //execute("env DISPLAY=:0 zenity --error --no-wrap --text='" + window.getTitle() + "'");
            execute("env DISPLAY=:0 zenity --notification --text=" + title);
        } else {
            logger.warn("Close failed with {} for {}", result.exitValue, window);
        }
    }

    private List<String> read(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    private Window map(String line) {
        String[] split = line.split(" +");
        String title = Arrays.stream(split).skip(4).collect(Collectors.joining(" "));
        int pid = Integer.parseInt(split[2]);
        return new Window(split[0], pid, title);
    }

    private Result execute(String command) {
        try {
            logger.debug("Executing {}", command);
            Process process = Runtime.getRuntime().exec(command);

            int exitValue;
            List<String> output;

            if (process.waitFor(30, TimeUnit.SECONDS)) {
                output = read(process.getInputStream());
                exitValue = process.exitValue();
            } else {
                process.destroy();
                output = Collections.emptyList();
                exitValue = -1;
            }

            if (logger.isDebugEnabled()) {
                output.forEach(line -> logger.debug("Output {}", line));
                logger.debug("Exit value {}", exitValue);
            }

            return new Result(exitValue, output);
        } catch (IOException | InterruptedException e) {
            logger.error("Cannot execute " + command, e);
            return new Result(-1, Collections.emptyList());
        }
    }

    private static final class Result {
        int exitValue;
        List<String> output;

        public Result(int exitValue, List<String> output) {
            this.exitValue = exitValue;
            this.output = output;
        }
    }
}
