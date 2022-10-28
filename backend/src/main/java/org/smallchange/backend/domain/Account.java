package org.smallchange.backend.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A BankAccount.
 */
@Entity
@Table(name = "sc_account")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Account implements Serializable {

    @Column(name = "user_id")
    private String userId;


    @Id
    @Column(name = "acct_no")
    private String accNo;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "acct_type")
    private String accType;

    @Column(name = "acct_balance")
    private Float accBalance;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getUserId() {
        return this.userId;
    }

    public Account userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccNo() {
        return this.accNo;
    }

    public Account accNo(String accNo) {
        this.setAccNo(accNo);
        return this;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getBankName() {
        return this.bankName;
    }

    public Account bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccType() {
        return this.accType;
    }

    public Account accType(String accType) {
        this.setAccType(accType);
        return this;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public Float getAccBalance() {
        return this.accBalance;
    }

    public Account accBalance(Float accBalance) {
        this.setAccBalance(accBalance);
        return this;
    }

    public void setAccBalance(Float accBalance) {
        this.accBalance = accBalance;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        return userId != null && userId.equals(((Account) o).userId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Account{" +
            ", userId='" + getUserId() + "'" +
            ", accNo=" + getAccNo() +
            ", bankName='" + getBankName() + "'" +
            ", accType='" + getAccType() + "'" +
            ", accBalance=" + getAccBalance() +
            "}";
    }
}
