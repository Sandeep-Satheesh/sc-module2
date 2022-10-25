package org.smallchange.backend.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Bonds.
 */
@Entity
@Table(name = "bonds")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bonds implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "current_price")
    private Float currentPrice;

    @Column(name = "interest_rate")
    private Float interestRate;

    @Column(name = "duration_months")
    private Integer durationMonths;

    @Column(name = "bond_type")
    private String bondType;

    @Column(name = "jhi_exchange")
    private String exchange;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bonds id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Bonds code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Bonds name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Bonds quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCurrentPrice() {
        return this.currentPrice;
    }

    public Bonds currentPrice(Float currentPrice) {
        this.setCurrentPrice(currentPrice);
        return this;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Float getInterestRate() {
        return this.interestRate;
    }

    public Bonds interestRate(Float interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getDurationMonths() {
        return this.durationMonths;
    }

    public Bonds durationMonths(Integer durationMonths) {
        this.setDurationMonths(durationMonths);
        return this;
    }

    public void setDurationMonths(Integer durationMonths) {
        this.durationMonths = durationMonths;
    }

    public String getBondType() {
        return this.bondType;
    }

    public Bonds bondType(String bondType) {
        this.setBondType(bondType);
        return this;
    }

    public void setBondType(String bondType) {
        this.bondType = bondType;
    }

    public String getExchange() {
        return this.exchange;
    }

    public Bonds exchange(String exchange) {
        this.setExchange(exchange);
        return this;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bonds)) {
            return false;
        }
        return id != null && id.equals(((Bonds) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bonds{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", currentPrice=" + getCurrentPrice() +
            ", interestRate=" + getInterestRate() +
            ", durationMonths=" + getDurationMonths() +
            ", bondType='" + getBondType() + "'" +
            ", exchange='" + getExchange() + "'" +
            "}";
    }
}
