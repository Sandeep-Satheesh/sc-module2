package org.smallchange.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A User.
 */
@Entity
@Table(name = "sc_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "acc_no")
    private Integer accNo;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAccNo() {
        return accNo;
    }

    public void setAccNo(Integer accNo) {
        this.accNo = accNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
