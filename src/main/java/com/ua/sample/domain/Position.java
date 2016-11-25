package com.ua.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by KIRIL on 14.11.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Position {

    private Long _id;
    private String key;
    private String name;
    private String fullName;
    private String iata_airport_code;
    private String type;
    private String country;
    private GeoPosition geo_position;
    private Long location_id;
    private Boolean inEurope;
    private String countryCode;
    private Boolean coreCountry;
    private Long distance;

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getIata_airport_code() {
        return iata_airport_code;
    }

    public void setIata_airport_code(final String iata_airport_code) {
        this.iata_airport_code = iata_airport_code;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public GeoPosition getGeo_position() {
        return geo_position;
    }

    public void setGeo_position(final GeoPosition geo_position) {
        this.geo_position = geo_position;
    }

    public Long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(final Long location_id) {
        this.location_id = location_id;
    }

    public Boolean getInEurope() {
        return inEurope;
    }

    public void setInEurope(final Boolean inEurope) {
        this.inEurope = inEurope;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public Boolean getCoreCountry() {
        return coreCountry;
    }

    public void setCoreCountry(final Boolean coreCountry) {
        this.coreCountry = coreCountry;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(final Long distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Position position = (Position) o;

        if (!_id.equals(position._id)) return false;
        if (key != null ? !key.equals(position.key) : position.key != null) return false;
        if (name != null ? !name.equals(position.name) : position.name != null) return false;
        if (fullName != null ? !fullName.equals(position.fullName) : position.fullName != null) return false;
        if (iata_airport_code != null ? !iata_airport_code.equals(position.iata_airport_code) : position.iata_airport_code != null)
            return false;
        if (type != null ? !type.equals(position.type) : position.type != null) return false;
        if (country != null ? !country.equals(position.country) : position.country != null) return false;
        if (geo_position != null ? !geo_position.equals(position.geo_position) : position.geo_position != null)
            return false;
        if (location_id != null ? !location_id.equals(position.location_id) : position.location_id != null)
            return false;
        if (inEurope != null ? !inEurope.equals(position.inEurope) : position.inEurope != null) return false;
        if (countryCode != null ? !countryCode.equals(position.countryCode) : position.countryCode != null)
            return false;
        if (coreCountry != null ? !coreCountry.equals(position.coreCountry) : position.coreCountry != null)
            return false;
        return distance != null ? distance.equals(position.distance) : position.distance == null;
    }

    @Override
    public int hashCode() {
        int result = _id.hashCode();
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (iata_airport_code != null ? iata_airport_code.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (geo_position != null ? geo_position.hashCode() : 0);
        result = 31 * result + (location_id != null ? location_id.hashCode() : 0);
        result = 31 * result + (inEurope != null ? inEurope.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (coreCountry != null ? coreCountry.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        return result;
    }
}
