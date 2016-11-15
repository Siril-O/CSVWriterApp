package com.ua.sample.integration;

import com.ua.sample.domain.Position;

import java.util.List;

import org.springframework.messaging.handler.annotation.Header;

/**
 * Created by KIRIL on 14.11.2016.
 */
public interface PositionGateway {

    List<Position> getPositionsByCityName(String cityName);
}
