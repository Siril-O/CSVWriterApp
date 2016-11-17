package com.ua.sample.services.impl;

import com.google.common.collect.Lists;
import com.ua.sample.exceptions.CsvWriteException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by KIRIL on 17.11.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class CsvWriterServiceImplTest {

    private static final String TEST_DESCRIPTION = "testDescription";
    private static final String INNER_FIELD_VALUE = "innerFieldValue";
    private static final long ID = 12L;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Spy
    private CsvWriterServiceImpl testingInstance = new CsvWriterServiceImpl();

    @Test
    public void shouldWriteObjectWithNestedObjects() throws CsvWriteException {
        TestData testData = new CsvWriterServiceImplTest.TestData(ID, TEST_DESCRIPTION, new CsvWriterServiceImplTest.TestDataInner(INNER_FIELD_VALUE));
        Path filePath = mock(Path.class);
        ArgumentCaptor<String> headerCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<List> rowsCaptor = ArgumentCaptor.forClass(List.class);
        doNothing().when(testingInstance).writeToCsv(any(Path.class), anyString(), Mockito.<List<List<String>>>any());

        testingInstance.writeCsv(Lists.newArrayList(testData), TestData.class, filePath);

        verify(testingInstance).writeToCsv(any(Path.class), headerCaptor.capture(), rowsCaptor.capture());
        String expectedHeader = "id, description, innerField";
        assertThat(headerCaptor.getValue(), is(expectedHeader));
        assertThat(rowsCaptor.getValue().size(), is(1));
        List<String> row = (List<String>) rowsCaptor.getValue().get(0);
        assertThat(row.size(), is(3));
        assertThat(row.get(0), is(String.valueOf(ID)));
        assertThat(row.get(1), is(TEST_DESCRIPTION));
        assertThat(row.get(2), is(INNER_FIELD_VALUE));

    }

    @Test
    public void shouldNotWriteObjectsWithCollectionField() throws CsvWriteException {
        WrongTestData wrongTestData = new WrongTestData(ID, Lists.newArrayList("1", "2"));

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(String.format("Collection and Map types could not be serialized to csv: your type %s", List.class));

        testingInstance.writeCsv(Lists.newArrayList(wrongTestData), WrongTestData.class, null);
    }


    private static class TestData {
        private Long id;
        private String description;
        private TestDataInner testDataInner;

        public TestData(Long id, String description, TestDataInner testDataInner) {
            this.id = id;
            this.description = description;
            this.testDataInner = testDataInner;
        }
    }

    private static class TestDataInner {
        private String innerField;

        public TestDataInner(String innerField) {
            this.innerField = innerField;
        }

    }

    private static class WrongTestData {
        private Long id;
        private List<String> strings;

        public WrongTestData(Long id, List<String> strings) {
            this.id = id;
            this.strings = strings;
        }
    }

}