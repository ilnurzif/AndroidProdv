package com.naura.cityApp.rest;

public interface ILoadData {
    void request(final String cityName);
    void  request(final double latitude, final double longitude);
}
