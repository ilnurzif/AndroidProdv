package com.naura.cityApp.cityloader;

import com.naura.cityApp.ui.theatherdata.TheatherData;
import java.util.List;

public interface ICallData {
    public void execute(List<TheatherData> theatherData, String cityName, boolean saveData);
    public void execute(List<TheatherData> theatherData, String cityName);
    public String errorTextReturn(String errMsg);
}
