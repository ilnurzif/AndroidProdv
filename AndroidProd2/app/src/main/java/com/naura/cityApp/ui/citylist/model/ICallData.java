package com.naura.cityApp.ui.citylist.model;

import com.naura.cityApp.ui.theatherdata.TheatherData;
import java.util.List;

public interface ICallData {
    public void execute(List<TheatherData> theatherData, String cityName, boolean saveData);
    public String errorTextReturn(String errMsg);
}
