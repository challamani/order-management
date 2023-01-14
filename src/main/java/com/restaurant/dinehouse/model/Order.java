package com.restaurant.dinehouse.model;

import com.restaurant.dinehouse.util.SystemConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "order_master")
public class Order implements Serializable {

    private static final long serialVersionUID = -1604333737340047541L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Order() {
    }

    @Column(name = "userId")
    @NotNull
    private String userId;

    @Column(name = "servedBy")
    private String servedBy;

    @Column(name = "phoneNo")
    private String phoneNo;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private SystemConstants.OrderStatus status;

    @Column(name = "type")
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private SystemConstants.OrderType type;

    @Column(name = "address")
    @NotNull
    private String address;

    @Column(name = "customerName")
    private String customerName;

    @Basic(optional = false)
    @Column(name="createdOn", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "price")
    @NotNull
    private Double price;

    @Column(name = "offerId")
    private Long offerId;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "payableAmount")
    @NotNull
    private Double payableAmount;

    @Column(name = "discAmount")
    private Double discAmount;

    @Column(name = "description")
    private String description;

    /**
     * Swiggy and Zomato orderIds */
    @Column(name = "externalOrderId")
    private String externalOrderId;

    @Transient
    private List<OrderItem> orderItems;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemConstants.OrderStatus getStatus() {
        return status;
    }

    public void setStatus(SystemConstants.OrderStatus status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(Double payableAmount) {
        this.payableAmount = payableAmount;
    }

    public Double getDiscAmount() {
        return discAmount;
    }

    public void setDiscAmount(Double discAmount) {
        this.discAmount = discAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SystemConstants.OrderType getType() {
        return type;
    }

    public void setType(SystemConstants.OrderType type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExternalOrderId() {
        return externalOrderId;
    }

    public void setExternalOrderId(String externalOrderId) {
        this.externalOrderId = externalOrderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getServedBy() {
        return servedBy;
    }

    public void setServedBy(String servedBy) {
        this.servedBy = servedBy;
    }
}
