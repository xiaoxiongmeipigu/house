package com.yigu.commom.result;

import java.util.List;

/**
 * Created by brain on 2016/12/5.
 */
public class MapiItemResult extends MapiBaseResult{

    private String price;
    private boolean isSel;

    private String goods_id;
    private String title;
    private String img;
    private String terms;
    private String inventory;
    private String sell_count;
    private String location;
    private String express_fee;
    private String conver_img;
    private String retail_price;
    private String old_price;
    private String output_time;
    private String duration_time;

    private String arrtStr;

    private String time;
    private String content;

    public String getArrtStr() {
        return arrtStr;
    }

    public void setArrtStr(String arrtStr) {
        this.arrtStr = arrtStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration_time() {
        return duration_time;
    }

    public void setDuration_time(String duration_time) {
        this.duration_time = duration_time;
    }

    private List<MapiResourceResult> imgs;

    public String getOutput_time() {
        return output_time;
    }

    public void setOutput_time(String output_time) {
        this.output_time = output_time;
    }

    public List<MapiResourceResult> getImgs() {
        return imgs;
    }

    public void setImgs(List<MapiResourceResult> imgs) {
        this.imgs = imgs;
    }

    public String getOld_price() {
        return old_price;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    public String getConver_img() {
        return conver_img;
    }

    public void setConver_img(String conver_img) {
        this.conver_img = conver_img;
    }

    public String getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(String retail_price) {
        this.retail_price = retail_price;
    }

    public String getExpress_fee() {
        return express_fee;
    }

    public void setExpress_fee(String express_fee) {
        this.express_fee = express_fee;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSell_count() {
        return sell_count;
    }

    public void setSell_count(String sell_count) {
        this.sell_count = sell_count;
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

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
