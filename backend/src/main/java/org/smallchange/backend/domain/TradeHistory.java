package org.smallchange.backend.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A TradeHistory.
 */
@Entity
@Table(name = "sc_trade_history")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TradeHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "trade_id")
    private Integer tradeId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "asset_code")
    private String assetCode;

    @Column(name = "trade_price")
    private Float tradePrice;

    @Column(name = "trade_type")
    private String tradeType;

    @Column(name = "trade_quantity")
    private Integer tradeQuantity;

    @Column(name = "trade_date")
    private LocalDate tradeDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TradeHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTradeId() {
        return this.tradeId;
    }

    public TradeHistory tradeId(Integer tradeId) {
        this.setTradeId(tradeId);
        return this;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public String getUserId() {
        return this.userId;
    }

    public TradeHistory userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssetCode() {
        return this.assetCode;
    }

    public TradeHistory assetCode(String assetCode) {
        this.setAssetCode(assetCode);
        return this;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public Float getTradePrice() {
        return this.tradePrice;
    }

    public TradeHistory tradePrice(Float tradePrice) {
        this.setTradePrice(tradePrice);
        return this;
    }

    public void setTradePrice(Float tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradeType() {
        return this.tradeType;
    }

    public TradeHistory tradeType(String tradeType) {
        this.setTradeType(tradeType);
        return this;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getTradeQuantity() {
        return this.tradeQuantity;
    }

    public TradeHistory tradeQuantity(Integer tradeQuantity) {
        this.setTradeQuantity(tradeQuantity);
        return this;
    }

    public void setTradeQuantity(Integer tradeQuantity) {
        this.tradeQuantity = tradeQuantity;
    }

    public LocalDate getTradeDate() {
        return this.tradeDate;
    }

    public TradeHistory tradeDate(LocalDate tradeDate) {
        this.setTradeDate(tradeDate);
        return this;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TradeHistory)) {
            return false;
        }
        return id != null && id.equals(((TradeHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TradeHistory{" +
            "id=" + getId() +
            ", tradeId=" + getTradeId() +
            ", userId='" + getUserId() + "'" +
            ", assetCode='" + getAssetCode() + "'" +
            ", tradePrice=" + getTradePrice() +
            ", tradeType='" + getTradeType() + "'" +
            ", tradeQuantity=" + getTradeQuantity() +
            ", tradeDate='" + getTradeDate() + "'" +
            "}";
    }
}
