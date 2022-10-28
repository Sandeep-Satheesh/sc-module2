package org.smallchange.backend.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Asset {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "current_price")
    private Float currentPrice;

    @Column(name = "exchange")
    private String exchangeName;

    public String getCode() {
        return this.code;
    }

    public Asset code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Asset name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Asset quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCurrentPrice() {
        return this.currentPrice;
    }

    public Asset currentPrice(Float currentPrice) {
        this.setCurrentPrice(currentPrice);
        return this;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getExchangeName() {
        return this.exchangeName;
    }

    public Asset exchange(String exchange) {
        this.setExchangeName(exchange);
        return this;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

}
