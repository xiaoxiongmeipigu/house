package com.yigu.commom.result;

/**
 * Created by brain on 2016/12/17.
 */
public class MapiSizeResult extends MapiBaseResult{

    private String title;
    private String price;
    private String num;
    private String account;

    public MapiSizeResult(String account, String num, String price, String title) {
        this.account = account;
        this.num = num;
        this.price = price;
        this.title = title;
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
