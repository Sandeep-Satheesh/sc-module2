package org.smallchange.backend.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Bonds.
 */
@Entity
@Table(name = "sc_bonds")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bond extends Asset implements Serializable {
    @Column(name = "interest_rate")
    private Float interestRate;

    @Column(name = "duration_months")
    private Integer durationMonths;

    @Column(name = "bond_type")
    private String bondType;

    public Bond currentPrice(Float currentPrice) {
        this.setCurrentPrice(currentPrice);
        return this;
    }
    public Float getInterestRate() {
        return this.interestRate;
    }

    public Bond interestRate(Float interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getDurationMonths() {
        return this.durationMonths;
    }

    public Bond durationMonths(Integer durationMonths) {
        this.setDurationMonths(durationMonths);
        return this;
    }

    public void setDurationMonths(Integer durationMonths) {
        this.durationMonths = durationMonths;
    }

    public String getBondType() {
        return this.bondType;
    }

    public Bond bondType(String bondType) {
        this.setBondType(bondType);
        return this;
    }

    public void setBondType(String bondType) {
        this.bondType = bondType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bond)) {
            return false;
        }
        return getCode() != null && getCode().equals(((Bond) o).getCode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bond{" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", currentPrice=" + getCurrentPrice() +
            ", interestRate=" + getInterestRate() +
            ", durationMonths=" + getDurationMonths() +
            ", bondType='" + getBondType() + "'" +
            ", exchangeName='" + getExchangeName() + "'" +
            "}";
    }
}
