package com.yigu.commom.result;

/**
 * Created by brain on 2016/12/5.
 */
public class MapiItemResult extends MapiBaseResult{

    private String price;
    private boolean isSel;

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
