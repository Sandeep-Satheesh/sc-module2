package org.smallchange.backend.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A BankAccount.
 */
@Entity
@Table(name = "bank_account")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "acc_no")
    private Integer accNo;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "acc_type")
    private String accType;

    @Column(name = "acc_balance")
    private Float accBalance;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public BankAccount userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAccNo() {
        return this.accNo;
    }

    public BankAccount accNo(Integer accNo) {
        this.setAccNo(accNo);
        return this;
    }

    public void setAccNo(Integer accNo) {
        this.accNo = accNo;
    }

    public String getBankName() {
        return this.bankName;
    }

    public BankAccount bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccType() {
        return this.accType;
    }

    public BankAccount accType(String accType) {
        this.setAccType(accType);
        return this;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public Float getAccBalance() {
        return this.accBalance;
    }

    public BankAccount accBalance(Float accBalance) {
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
        if (!(o instanceof BankAccount)) {
            return false;
        }
        return id != null && id.equals(((BankAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankAccount{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", accNo=" + getAccNo() +
            ", bankName='" + getBankName() + "'" +
            ", accType='" + getAccType() + "'" +
            ", accBalance=" + getAccBalance() +
            "}";
    }
}
