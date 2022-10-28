package org.smallchange.backend.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A MutualFunds.
 */
@Entity
@Table(name = "sc_mutualfunds")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MutualFund extends Asset implements Serializable {

    @Column(name = "mf_type")
    private String mfType;

    @Column(name = "interest_rate")
    private String interestRate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getMfType() {
        return this.mfType;
    }

    public MutualFund mfType(String mfType) {
        this.setMfType(mfType);
        return this;
    }

    public void setMfType(String mfType) {
        this.mfType = mfType;
    }

    public String getInterestRate() {
        return this.interestRate;
    }

    public MutualFund interestRate(String interestRate) {
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
        if (!(o instanceof MutualFund)) {
            return false;
        }
        return getCode() != null && getCode().equals(((MutualFund) o).getCode());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MutualFund{" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", currentPrice=" + getCurrentPrice() +
            ", mfType='" + getMfType() + "'" +
            ", interestRate='" + getInterestRate() + "'" +
            "}";
    }
}
