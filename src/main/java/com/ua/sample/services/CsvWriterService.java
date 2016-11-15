package com.ua.sample.services;

import java.util.List;

/**
 * Created by KIRIL on 15.11.2016.
 */
public interface CsvWriterService {

    <T> void writeCsv(List<T> objToWrite, String fileName, Class<T> classToWrite);

}
