package com.ua.sample.services.impl;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.ua.sample.domain.Position;
import com.ua.sample.exceptions.GetPositionsException;
import com.ua.sample.integration.PositionGateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by Kyrylo_Kovalchuk on 11/25/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class CityPositionsServiceImplTest {

    private static final String CITY_NAME = "Berlin";

    @Mock
    private PositionGateway positionGateway;

    @InjectMocks
    private CityPositionsServiceImpl testingInstance = new CityPositionsServiceImpl();

    @Test
    public void shouldSendGetPositionsRequest() throws GetPositionsException {
        final ArrayList<Position> positions = Lists.newArrayList(new Position());
        when(positionGateway.getPositionsByCityName(CITY_NAME)).thenReturn(positions);

        assertThat(testingInstance.sendGetPositionsRequest(CITY_NAME), is(positions));
    }

    @Test(expected = GetPositionsException.class)
    public void shouldReThrowExceptionWhenGetPositionsRequest() throws GetPositionsException {
        final RuntimeException exception = new RuntimeException();
        doThrow(exception).when(positionGateway).getPositionsByCityName(CITY_NAME);
        testingInstance.sendGetPositionsRequest(CITY_NAME);
    }

}