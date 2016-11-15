package com.ua.sample.integration;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.ua.sample.domain.Position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

/**
 * Created by Kyrylo_Kovalchuk on 11/15/2016.
 */
public class PositionTransformer {

    private static final Logger LOG = LoggerFactory.getLogger(PositionTransformer.class);

    @Autowired
    private ObjectMapper mapper;

    public Message<List<Position>> transform(Message<String> message) throws IOException {
        final CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, Position.class);
        final List<Position> result;
        result = mapper.readValue(message.getPayload(), collectionType);
        return new GenericMessage<>(result);
    }
}
