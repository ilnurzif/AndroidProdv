package com.naura.cityApp.ui.citylist.model;

import com.naura.cityApp.ui.theatherdata.TheatherData;
import java.util.List;

interface ICallData {
    public void execute(List<TheatherData> theatherData, String cityName);
    public String errorTextReturn(String errMsg);
}
