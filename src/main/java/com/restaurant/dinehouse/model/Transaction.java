package com.restaurant.dinehouse.model;

import com.restaurant.dinehouse.util.SystemConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

    private static final long serialVersionUID = -160433373734001L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Transaction() {
    }

    @Column(name = "userId")
    @NotNull
    private String userId;

    @Column(name = "orderId")
    private String orderId;

    @Column(name = "paymentMethod")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private SystemConstants.PaymentMethod paymentMethod;

    @Column(name = "tranGroup")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private SystemConstants.TranGroup tranGroup;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private SystemConstants.TranType type;

    @Basic(optional = false)
    @Column(name="createdOn", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "amount")
    @NotNull
    private Double amount;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public SystemConstants.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(SystemConstants.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public SystemConstants.TranGroup getTranGroup() {
        return tranGroup;
    }

    public void setTranGroup(SystemConstants.TranGroup tranGroup) {
        this.tranGroup = tranGroup;
    }

    public SystemConstants.TranType getType() {
        return type;
    }

    public void setType(SystemConstants.TranType type) {
        this.type = type;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
