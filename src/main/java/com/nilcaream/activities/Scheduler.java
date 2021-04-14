package com.nilcaream.activities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Scheduler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private WindowManager windowManager = new Wmctrl();

    public void run(List<Definition> definitions, int sleep) {
        while (true) {
            sleep(execute(definitions, sleep) * 1000);
        }
    }

    private int execute(List<Definition> definitions, int sleep) {
        List<Window> windows = windowManager.get();
        List<Window> toClose = windows.stream().filter(a -> matches(definitions, a)).collect(Collectors.toList());
        toClose.forEach(a -> windowManager.close(a));
        return toClose.size() == 0 ? sleep : sleep / 4;
    }

    private boolean matches(List<Definition> definitions, Window window) {
        LocalDateTime now = LocalDateTime.now();

        return definitions.stream()
                .filter(d -> d.getWindowTitle().matcher(window.getTitle()).matches())
                .filter(d -> d.getDaysOfWeek().contains(now.getDayOfWeek()))
                .filter(d -> now.toLocalTime().isAfter(d.getFrom()))
                .filter(d -> now.toLocalTime().isBefore(d.getTo()))
                .peek(d -> logger.info("{} matches {}", d, window))
                .findFirst()
                .isPresent();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignore) {
            logger.warn("Sleep interrupted");
        }
    }
}
