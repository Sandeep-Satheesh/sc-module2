package org.smallchange.backend.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Stocks.
 */
@Entity
@Table(name = "sc_stocks")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Stock extends Asset implements Serializable {

    @Column(name = "stock_type")
    private String stockType;

    @Override
    public String getExchangeName() {
        return exchangeName;
    }

    @Override
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    @Column(name = "exchange_name")
    private String exchangeName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getStockType() {
        return this.stockType;
    }

    public Stock stockType(String stockType) {
        this.setStockType(stockType);
        return this;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stock)) {
            return false;
        }
        return getCode() != null && getCode().equals(((Stock) o).getCode());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stock{" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", currentPrice=" + getCurrentPrice() +
            ", stockType='" + getStockType() + "'" +
            ", exchange='" + getExchangeName() + "'" +
            "}";
    }
}
