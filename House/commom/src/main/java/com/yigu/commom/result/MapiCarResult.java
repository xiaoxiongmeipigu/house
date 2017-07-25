package com.yigu.commom.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brain on 2016/12/29.
 */
public class MapiCarResult implements Serializable{
    private boolean isSel;

    private String goods_id;
    private String img;
    private String title;
    private String goods_type;
    private String price;
    private String count;
    private String order_date;
    private String order_id;
    private String pic_url;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private List<MapiCartItemResult> list = new ArrayList<>();

    public List<MapiCartItemResult> getList() {
        return list;
    }

    public void setList(List<MapiCartItemResult> list) {
        this.list = list;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }
}
