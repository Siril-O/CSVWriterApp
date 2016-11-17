package com.ua.sample.services.impl;

import com.google.common.collect.Lists;
import com.ua.sample.exceptions.CsvWriteException;
import com.ua.sample.services.CsvWriterService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyrylo_Kovalchuk on 11/15/2016.
 */
@Service
public class CsvWriterServiceImpl implements CsvWriterService {

    private static final Logger LOG = LoggerFactory.getLogger(CsvWriterServiceImpl.class);

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String HEADER_DELIMITER = ",";
    private static final String SPACE_DELIMITER = " ";

    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

    private static final int ALLOWED_FIELD_DEPTH = 3;

    @Override
    public <T> void writeCsv(List<T> toSerialize, Class<T> classToSerialize, Path filePath) throws CsvWriteException {
        String header = collectHeader(classToSerialize);
        List<List<String>> rows = collectRows(toSerialize, classToSerialize);

        writeToCsv(filePath, header, rows);
    }

    protected void writeToCsv(Path filePath, String header, List<List<String>> rows) throws CsvWriteException {
        validateFilePath(filePath);
        try (FileWriter fileWriter = new FileWriter(filePath.toFile());
             CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, CSV_FORMAT)) {
            csvFilePrinter.printRecord(header);
            for (List<String> row : rows) {
                csvFilePrinter.printRecord(row);
            }
            LOG.info("File {} was filled with data successfully.", filePath);
        } catch (Exception exception) {
            LOG.error("Error writing to file:{}. Reason: {}", filePath.toAbsolutePath(), exception);
            throw new CsvWriteException(exception);
        }
    }

    private <T> List<List<String>> collectRows(List<T> toSerialize, Class<T> classToSerialize) throws CsvWriteException {
        try {
            Validate.notEmpty(toSerialize);
            List<List<String>> rows = Lists.newArrayList();
            for (T value : toSerialize) {
                rows.add(collectRow(value, classToSerialize));
            }
            return rows;
        } catch (IllegalAccessException exception) {
            LOG.error("Error happen during transforming data to write csv", exception);
            throw new CsvWriteException(exception);
        }
    }

    private boolean validateFilePath(Path filePath) throws CsvWriteException {
        try {
            if (!Files.exists(filePath)) {
                LOG.info("File {} do not exist, will be created", filePath.toAbsolutePath());
                return true;
            }
            return Files.isRegularFile(filePath) & Files.isWritable(filePath) & Files.isExecutable(filePath);
        } catch (SecurityException exception) {
            LOG.error("Can not write file{} due to security restrictions", filePath.toAbsolutePath());
            throw new CsvWriteException(exception);
        }
    }

    // Can not handle cyclic dependencies, collections, map, arrays, and inherited POJO fields, static fields
    private String collectHeader(Class<?> classToSerialize) {
        return collectHeadersForClass(classToSerialize, 0);
    }

    private String collectHeadersForClass(Class<?> classToSerialize, int fieldsDepth) {
        if (fieldsDepth > ALLOWED_FIELD_DEPTH) {
            throw new IllegalArgumentException(String.format("Can not serialize %d depth fields", ALLOWED_FIELD_DEPTH));
        }
        List<Field> fields = Arrays.asList(classToSerialize.getDeclaredFields());
        List<String> headers = Lists.newArrayList();
        for (Field field : fields) {
            String header;
            if (isFieldNestedObject(field)) {
                header = collectHeadersForClass(field.getType(), ++fieldsDepth);
            } else {
                header = field.getName();
            }
            headers.add(header);
        }
        return String.join(HEADER_DELIMITER + SPACE_DELIMITER, headers);
    }

    private boolean isFieldNestedObject(Field field) {
        Class<?> fieldType = field.getType();
        boolean isFieldValid = !fieldType.isArray() && !Iterable.class.isAssignableFrom(fieldType) &&
                !Map.class.isAssignableFrom(fieldType) && !fieldType.equals(Class.class)
                && !Modifier.isStatic(field.getModifiers());
        Validate.isTrue(isFieldValid,
                String.format("Collection and Map types could not be serialized to csv: your type %s", fieldType));
        return !ClassUtils.isPrimitiveOrWrapper(fieldType) && !fieldType.equals(String.class);
    }

    private List<String> collectRow(Object objectToWrite, Class<?> classToWrite) throws IllegalAccessException {
        List<Field> fields = Arrays.asList(classToWrite.getDeclaredFields());
        List<String> rowEntries = Lists.newArrayList();
        for (Field field : fields) {
            field.setAccessible(true);
            if (isFieldNestedObject(field)) {
                rowEntries.addAll(collectRow(field.get(objectToWrite), field.getType()));
            } else {
                rowEntries.add(extractFieldValue(field.get(objectToWrite), field.getType()));
            }
        }
        return rowEntries;
    }

    private String extractFieldValue(Object value, Class<?> classToSerialize) {
        if (classToSerialize.equals(String.class)) {
            return (String) value;
        } else if (ClassUtils.isPrimitiveOrWrapper(classToSerialize)) {
            return value != null ? String.valueOf(value) : StringUtils.EMPTY;
        } else {
            return value.toString();
        }
    }
}
