package com.ua.sample.services;

import com.ua.sample.exceptions.CsvWriteException;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by KIRIL on 15.11.2016.
 */
public interface CsvWriterService {

    <T> void writeCsv(List<T> toSerialize, Class<T> classToSerialize, Path filePath) throws CsvWriteException;
}
