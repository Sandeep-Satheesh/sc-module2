package org.smallchange.backend.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Preferences.
 */
@Entity
@Table(name = "preferences")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Preferences implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "investment_purpose")
    private String investmentPurpose;

    @Column(name = "risk_tolerance")
    private Integer riskTolerance;

    @Column(name = "income_category")
    private String incomeCategory;

    @Column(name = "investment_length")
    private String investmentLength;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Preferences id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public Preferences userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInvestmentPurpose() {
        return this.investmentPurpose;
    }

    public Preferences investmentPurpose(String investmentPurpose) {
        this.setInvestmentPurpose(investmentPurpose);
        return this;
    }

    public void setInvestmentPurpose(String investmentPurpose) {
        this.investmentPurpose = investmentPurpose;
    }

    public Integer getRiskTolerance() {
        return this.riskTolerance;
    }

    public Preferences riskTolerance(Integer riskTolerance) {
        this.setRiskTolerance(riskTolerance);
        return this;
    }

    public void setRiskTolerance(Integer riskTolerance) {
        this.riskTolerance = riskTolerance;
    }

    public String getIncomeCategory() {
        return this.incomeCategory;
    }

    public Preferences incomeCategory(String incomeCategory) {
        this.setIncomeCategory(incomeCategory);
        return this;
    }

    public void setIncomeCategory(String incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public String getInvestmentLength() {
        return this.investmentLength;
    }

    public Preferences investmentLength(String investmentLength) {
        this.setInvestmentLength(investmentLength);
        return this;
    }

    public void setInvestmentLength(String investmentLength) {
        this.investmentLength = investmentLength;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Preferences)) {
            return false;
        }
        return id != null && id.equals(((Preferences) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Preferences{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", investmentPurpose='" + getInvestmentPurpose() + "'" +
            ", riskTolerance=" + getRiskTolerance() +
            ", incomeCategory='" + getIncomeCategory() + "'" +
            ", investmentLength='" + getInvestmentLength() + "'" +
            "}";
    }
}
