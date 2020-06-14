package com.naura.cityApp.observercode;

public interface Observer {
    public <T> void update(String eventName, T val);
}
