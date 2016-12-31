package com.yigu.commom.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brain on 2016/12/29.
 */
public class MapiCarResult implements Serializable{

    private List<MapiItemResult> items = new ArrayList<>();

    public List<MapiItemResult> getItems() {
        return items;
    }

    public void setItems(List<MapiItemResult> items) {
        this.items = items;
    }
}
