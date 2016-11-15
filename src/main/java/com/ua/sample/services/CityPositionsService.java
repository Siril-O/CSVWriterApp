package com.ua.sample.services;

import com.ua.sample.domain.Position;

import java.util.List;

/**
 * Created by KIRIL on 15.11.2016.
 */
public interface CityPositionsService {

    List<Position>  sendGetPositionsRequest(String cityName);
}
