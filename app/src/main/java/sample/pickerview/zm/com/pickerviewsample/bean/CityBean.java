package sample.pickerview.zm.com.pickerviewsample.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shake on 2017/9/5.
 */

public class CityBean implements Serializable{
    /**
     * name : 城市
     * area : ["东城区","西城区","崇文区","昌平区"]
     */

    private String name;
    private List<String> area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArea() {
        if(area==null)
            area = new ArrayList<>();
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }
}
