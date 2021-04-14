package com.nilcaream.activities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefinitionsReader {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<Definition> load(File definitionsFile) throws IOException {
        Path path = definitionsFile.toPath().toAbsolutePath();
        logger.info("Loading {}", path);

        return Files.readAllLines(path, StandardCharsets.UTF_8).stream()
                .map(String::trim)
                .filter(x -> !x.isEmpty())
                .filter(x -> !x.startsWith("#"))
                .map(this::resolve)
                .peek(x -> logger.info(x.toString()))
                .collect(Collectors.toList());
    }

    private Definition resolve(String text) {
        List<String> elements = Arrays.stream(text.split("\\|"))
                .map(String::trim)
                .collect(Collectors.toList());

        List<DayOfWeek> daysOfWeek = Arrays.stream(elements.get(0).split(","))
                .map(Integer::parseInt)
                .map(DayOfWeek::of)
                .collect(Collectors.toList());

        LocalTime from = LocalTime.parse(elements.get(1), DateTimeFormatter.ISO_TIME);
        LocalTime to = LocalTime.parse(elements.get(2), DateTimeFormatter.ISO_TIME);
        Pattern windowTitle = Pattern.compile(elements.get(3), Pattern.CASE_INSENSITIVE);

        return new Definition(daysOfWeek, from, to, windowTitle);
    }
}
