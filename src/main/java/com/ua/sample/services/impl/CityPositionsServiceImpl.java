package com.ua.sample.services.impl;

import java.util.List;

import com.ua.sample.domain.Position;
import com.ua.sample.exceptions.GetPositionsException;
import com.ua.sample.integration.PositionGateway;
import com.ua.sample.services.CityPositionsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by KIRIL on 15.11.2016.
 */
@Service
public class CityPositionsServiceImpl implements CityPositionsService {

    private static final Logger LOG = LoggerFactory.getLogger(CityPositionsServiceImpl.class);

    @Autowired
    private PositionGateway positionGateway;

    @Override
    public List<Position> sendGetPositionsRequest(String cityName) throws GetPositionsException {
        List<Position> positions;
        try {
            positions = positionGateway.getPositionsByCityName(cityName);
            if (positions.isEmpty()) {
                LOG.warn("No positions found for cityName:{}", cityName);
            }
        } catch (Exception exception) {
            LOG.error("Error happened when querying API", exception);
            throw new GetPositionsException(exception);
        }
        return positions;
    }
}
