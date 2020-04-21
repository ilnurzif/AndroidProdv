package com.naura.cityApp.ui.citylist;

import com.naura.cityApp.ui.theatherdata.TheatherData;
import java.util.List;

interface ICallData {
    public void execute(List<TheatherData> theatherData, String cityName);
    public String getLoadedData();
}
