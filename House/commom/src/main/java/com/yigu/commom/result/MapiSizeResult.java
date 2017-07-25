package com.yigu.commom.result;

import java.util.List;

/**
 * Created by brain on 2016/12/17.
 */
public class MapiSizeResult extends MapiBaseResult{

    private String title;
    private String price;
    private String num="0";
    private String account;
    private String colour;
    private String attr_id;
    private String size;
    private String count;
    private String add_price;
    private List<MapiSizeResult> size_list;

   /* public MapiSizeResult(String account, String num, String price, String title) {
        this.account = account;
        this.num = num;
        this.price = price;
        this.title = title;
    }*/

    public String getAdd_price() {
        return add_price;
    }

    public void setAdd_price(String add_price) {
        this.add_price = add_price;
    }

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<MapiSizeResult> getSize_list() {
        return size_list;
    }

    public void setSize_list(List<MapiSizeResult> size_list) {
        this.size_list = size_list;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
