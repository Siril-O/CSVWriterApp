package com.ua.sample;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

import com.google.common.collect.Lists;
import com.ua.sample.domain.Position;
import com.ua.sample.exceptions.CsvWriteException;
import com.ua.sample.exceptions.GetPositionsException;
import com.ua.sample.services.CityPositionsService;
import com.ua.sample.services.impl.CsvWriterServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Kyrylo_Kovalchuk on 11/25/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationRunnerTest {

    public static final String CITY_NAME = "Berlin";
    @Mock
    private CityPositionsService cityPositionsService;
    @Mock
    private CsvWriterServiceImpl csvWriter;

    @Spy
    @InjectMocks
    private ApplicationRunner testingInstance = new ApplicationRunner();


    @Before
    public void setUp() {
        final Path path = mock(Path.class);
        doReturn(path).when(testingInstance).getFilePath();
    }

    @Test
    public void shouldNotSendRequestWhenNoCityName() throws GetPositionsException {
        testingInstance.main();
        verify(cityPositionsService, never()).sendGetPositionsRequest(anyString());
    }

    @Test
    public void shouldNotSendRequestWhenCityNameIsEmpty() throws GetPositionsException {
        testingInstance.main(StringUtils.EMPTY);
        verify(cityPositionsService, never()).sendGetPositionsRequest(anyString());
    }

    @Test
    public void shouldNotWriteCsvWhenNullPositions() throws GetPositionsException, CsvWriteException {
        when(cityPositionsService.sendGetPositionsRequest(CITY_NAME)).thenReturn(null);

        testingInstance.main(CITY_NAME);

        verify(csvWriter, never()).writeCsv(anyListOf(Position.class), eq(Position.class), any(Path.class));
    }

    @Test
    public void shouldNotWriteCsvWhenEmptyPositions() throws GetPositionsException, CsvWriteException {
        when(cityPositionsService.sendGetPositionsRequest(CITY_NAME)).thenReturn(Collections.emptyList());

        testingInstance.main(CITY_NAME);

        verify(csvWriter, never()).writeCsv(anyListOf(Position.class), eq(Position.class), any(Path.class));
    }


    @Test
    public void shouldWriteCsv() throws GetPositionsException, CsvWriteException {
        final ArrayList<Position> positions = Lists.newArrayList(new Position());
        when(cityPositionsService.sendGetPositionsRequest(CITY_NAME)).thenReturn(positions);

        testingInstance.main(CITY_NAME);

        verify(csvWriter).writeCsv(eq(positions), eq(Position.class), any(Path.class));
    }

    @Test
    public void shouldCatchException() throws GetPositionsException {
        final RuntimeException exception = new RuntimeException();
        doThrow(exception).when(cityPositionsService).sendGetPositionsRequest(CITY_NAME);

        testingInstance.main(CITY_NAME);
    }


}