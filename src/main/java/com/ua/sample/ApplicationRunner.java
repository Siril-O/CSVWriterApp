package com.ua.sample;

import com.ua.sample.domain.Position;
import com.ua.sample.exceptions.CsvWriteException;
import com.ua.sample.exceptions.GetPositionsException;
import com.ua.sample.services.CityPositionsService;
import com.ua.sample.services.impl.CsvWriterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by KIRIL on 15.11.2016.
 */
@Component
public class ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationRunner.class);
    private static final String DEFAULT_FILE_NAME = "cityPositions.csv";

    @Autowired
    private CityPositionsService cityPositionsService;
    @Autowired
    private CsvWriterServiceImpl csvWriter;

    public void main(String... args) {
        if (args.length == 0 || args[0].isEmpty()) {
            LOG.error("City name is not specified");
            System.out.println("Please specify city name to get positions");
            return;
        }
        try {
            String cityName = args[0];
            List<Position> cityPositions = cityPositionsService.sendGetPositionsRequest(cityName);
            if (cityPositions == null || cityPositions.isEmpty()) {
                System.out.printf("No positions found for city: %s", cityName);
                return;
            }
            Path filePath = FileSystems.getDefault().getPath(DEFAULT_FILE_NAME);
            csvWriter.writeCsv(cityPositions, Position.class, filePath);
            System.out.printf("File %s,was filled with positions for %s successfully. For mode details see log file",
                    filePath.toAbsolutePath(), cityName);
        } catch (GetPositionsException getPositionsException) {
            System.out.printf("Error happen while getting positions from API. Reason: %s. For mode details see log file",
                    getPositionsException.getMessage());
        } catch (CsvWriteException csvWriteException) {
            System.out.printf("Error happen while writing to file. Reason: %s. For mode details see log file",
                    csvWriteException.getMessage());
        } catch (Exception exception) {
            LOG.error("Some error happen. %s", exception);
            System.out.printf("Some error happen. Reason: %s. For mode details see log file", exception.getMessage());
        }
    }
}
