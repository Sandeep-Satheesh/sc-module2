package org.smallchange.backend.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A MutualFunds.
 */
@Entity
@Table(name = "mutual_funds")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MutualFunds implements Serializable {

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

    @Column(name = "mf_type")
    private String mfType;

    @Column(name = "interest_rate")
    private String interestRate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MutualFunds id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public MutualFunds code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public MutualFunds name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public MutualFunds quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCurrentPrice() {
        return this.currentPrice;
    }

    public MutualFunds currentPrice(Float currentPrice) {
        this.setCurrentPrice(currentPrice);
        return this;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getMfType() {
        return this.mfType;
    }

    public MutualFunds mfType(String mfType) {
        this.setMfType(mfType);
        return this;
    }

    public void setMfType(String mfType) {
        this.mfType = mfType;
    }

    public String getInterestRate() {
        return this.interestRate;
    }

    public MutualFunds interestRate(String interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MutualFunds)) {
            return false;
        }
        return id != null && id.equals(((MutualFunds) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MutualFunds{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", currentPrice=" + getCurrentPrice() +
            ", mfType='" + getMfType() + "'" +
            ", interestRate='" + getInterestRate() + "'" +
            "}";
    }
}
