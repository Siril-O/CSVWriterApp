package com.ua.sample.domain;

import java.util.List;

/**
 * Created by Kyrylo_Kovalchuk on 11/15/2016.
 */
public class PositionsResponse {

    List<Position> positions;

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(final List<Position> positions) {
        this.positions = positions;
    }
}
