package com.yigu.commom.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brain on 2017/4/19.
 */
public class MapiOrderResult extends MapiBaseResult{

    private String order_id;
    private String total_amount;
    private String express_fee;
    private String goods_total;
    private String order_date;
    private List<MapiCarResult> list = new ArrayList<>();

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getExpress_fee() {
        return express_fee;
    }

    public void setExpress_fee(String express_fee) {
        this.express_fee = express_fee;
    }

    public List<MapiCarResult> getList() {
        return list;
    }

    public void setList(List<MapiCarResult> list) {
        this.list = list;
    }

    public String getGoods_total() {
        return goods_total;
    }

    public void setGoods_total(String goods_total) {
        this.goods_total = goods_total;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
}
