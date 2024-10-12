package com.DDN.login.payload.response;

import java.util.Date;

public class OrderResponse {
    private Integer sum;
    private Number extract;

    public OrderResponse() {

    }


    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Number getExtract() {
        return extract;
    }

    public void setExtract(Number extract) {
        this.extract = extract;
    }
}
