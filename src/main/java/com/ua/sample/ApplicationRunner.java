package com.ua.sample;

import com.ua.sample.domain.Position;
import com.ua.sample.integration.PositionGateway;
import com.ua.sample.services.CsvWriter;
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
    private PositionGateway positionGateway;
    @Autowired
    private CsvWriter csvWriter;

    public void main(String... args) {
        final List<Position> cityPositions = positionGateway.getPositionsByCityName("Berlin");
        LOG.info("Result from endpoint:" + cityPositions);
      //  String fileName = "newfile.csv";
        csvWriter.writeCsv(cityPositions, null, Position.class);
    }
}
