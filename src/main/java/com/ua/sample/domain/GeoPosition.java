package com.ua.sample.domain;

/**
 * Created by KIRIL on 14.11.2016.
 */
public class GeoPosition {

    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final GeoPosition that = (GeoPosition) o;

        if (!latitude.equals(that.latitude)) return false;
        return longitude.equals(that.longitude);
    }

    @Override
    public int hashCode() {
        int result = latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        return result;
    }
}
