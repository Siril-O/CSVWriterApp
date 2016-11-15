package com.ua.sample.services.impl;

import com.google.common.collect.Lists;
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
import java.nio.file.FileSystems;
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
    private static final String NESTED_OBJECT_HEADER_DELIMITER = ".";
    private static final String HEADER_DELIMITER = ",";
    private static final String DEFAULT_FILE_NAME = "cityPositions.csv";
    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

    private static final int ALLOWED_FIELD_DEPTH = 3;

    @Override
    public <T> void writeCsv(List<T> toSerialize, String fileName, Class<T> classToSerialize) {
        Validate.notEmpty(toSerialize);
        Path filePath = createFilePath(fileName);
        if (!isFilePathValid(filePath)) {
            LOG.error("File {} is not accessible", filePath.toAbsolutePath());
            return;
        }
        try (FileWriter fileWriter = new FileWriter(filePath.toFile());
             CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, CSV_FORMAT)) {

            String header = collectHeader(classToSerialize);
            csvFilePrinter.printRecord(header);
            for (T value : toSerialize) {
                csvFilePrinter.printRecord(collectRow(value, classToSerialize));
            }
            LOG.info("File {} was filled with data successfully.", filePath);
        } catch (Exception e) {
            LOG.error("Error writing to file:{}. Reason: {}", filePath.toAbsolutePath(), e.getMessage());
        }
    }

    private boolean isFilePathValid(Path filePath) {
        try {
            if (!Files.exists(filePath)) {
                LOG.info("File {} do not exist, will be created", filePath.toAbsolutePath());
                return true;
            }
            return Files.isRegularFile(filePath) & Files.isWritable(filePath) & Files.isExecutable(filePath);
        } catch (SecurityException exception) {
            LOG.error("Can not write file{} due to security restrictions", filePath.toAbsolutePath());
            return false;
        }
    }

    private Path createFilePath(String fileName) {
        if (fileName == null) {
            fileName = DEFAULT_FILE_NAME;
            LOG.info("Using default file path");
        }
        return FileSystems.getDefault().getPath(fileName);
    }

    // Can not handle cyclic dependencies, collections, map, arrays, and inherited POJO fields, static fields
    private String collectHeader(Class<?> classToSerialize) {
        return collectHeadersForClass(classToSerialize, null, 0);
    }

    private String collectHeadersForClass(Class<?> classToSerialize, Field parentField, int fieldsDepth) {
        if (fieldsDepth > ALLOWED_FIELD_DEPTH) {
            throw new IllegalArgumentException(String.format("Can not serialize %d depth fields", ALLOWED_FIELD_DEPTH));
        }
        List<Field> fields = Arrays.asList(classToSerialize.getDeclaredFields());
        List<String> headers = Lists.newArrayList();
        for (Field field : fields) {
            final String header;
            if (isFieldNestedObject(field)) {
                header = collectHeadersForClass(field.getType(), field, ++fieldsDepth);
            } else {
                header = parentField == null ? field.getName() : parentField.getName() +
                        NESTED_OBJECT_HEADER_DELIMITER + field.getName();
            }
            headers.add(header);
        }
        return String.join(HEADER_DELIMITER + " ", headers);
    }

    private boolean isFieldNestedObject(Field field) {
        Class fieldType = field.getType();
        boolean isFieldValid = !fieldType.isArray() && !fieldType.equals(Iterable.class) &&
                !fieldType.equals(Map.class) && !fieldType.equals(Class.class) && !Modifier.isStatic(field.getModifiers());
        Validate.isTrue(isFieldValid,
                String.format("Collection and Map types could not be serialized to csv: your type %s", fieldType));
        return !ClassUtils.isPrimitiveOrWrapper(fieldType) && !fieldType.equals(String.class);
    }

    private List<String> collectRow(final Object objectToWrite, final Class<?> classToWrite) throws IllegalAccessException {
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

    private String extractFieldValue(Object value, final Class<?> classToSerialize) {
        if (classToSerialize.equals(String.class)) {
            return (String) value;
        } else if (ClassUtils.isPrimitiveOrWrapper(classToSerialize)) {
            return value != null ? String.valueOf(value) : StringUtils.EMPTY;
        } else {
            return value.toString();
        }
    }
}
