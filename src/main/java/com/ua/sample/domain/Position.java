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
}
