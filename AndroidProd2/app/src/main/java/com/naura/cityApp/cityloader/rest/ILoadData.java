package com.naura.cityApp.cityloader.rest;

public interface ILoadData {
    public void request(final String cityName);
    public void  request(final double latitude, final double longitude);
}
