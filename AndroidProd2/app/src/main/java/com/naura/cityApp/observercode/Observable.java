package com.naura.cityApp.observercode;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private static Observable instance = null;
    private static List<Observer> observers;

    public Observable() {
    }

    public static Observable getInstance() {
        if (instance == null) {
            instance = new Observable();
            observers = new ArrayList<>();
        }
        return instance;
    }

    public <T> void notify(String eventName, T val) {
        for (Observer obs : observers) {
            obs.update(eventName, val);
        }
    }

    public void subscribe(Observer obs) {
        observers.add(obs);
    }
}
