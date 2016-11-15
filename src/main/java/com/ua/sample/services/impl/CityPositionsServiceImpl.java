package com.ua.sample.services.impl;

import com.ua.sample.domain.Position;
import com.ua.sample.integration.PositionGateway;
import com.ua.sample.services.CityPositionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by KIRIL on 15.11.2016.
 */
@Service
public class CityPositionsServiceImpl implements CityPositionsService {

    private static final Logger LOG = LoggerFactory.getLogger(CityPositionsServiceImpl.class);

    @Autowired
    private PositionGateway positionGateway;

    @Override
    public List<Position> sendGetPositionsRequest(String cityName) {
        List<Position> positions = null;
        try {
            positions = positionGateway.getPositionsByCityName(cityName);
            if (positions.isEmpty()) {
                LOG.error("No positions found for cityName{}", cityName);
            }
        } catch (Exception exception) {
            LOG.error("Error happened when quering API. Reason {}", exception.getMessage());
        }
        return positions;
    }
}
