package com.ua.sample;

import com.ua.sample.domain.Position;
import com.ua.sample.services.CityPositionsService;
import com.ua.sample.services.impl.CsvWriterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by KIRIL on 15.11.2016.
 */
@Component
public class ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationRunner.class);

    @Autowired
    private CityPositionsService cityPositionsService;
    @Autowired
    private CsvWriterServiceImpl csvWriter;

    //todo move url to properties
    public void main(String... args) {
        final List<Position> cityPositions = cityPositionsService.sendGetPositionsRequest("Berlin");
        if (cityPositions == null) {
            return;
        }
        csvWriter.writeCsv(cityPositions, null, Position.class);
    }
}
