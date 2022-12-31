package com.restaurant.dinehouse.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_session")
public class UserSession {
    private static final long serialVersionUID = -1604333737340047500L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "token")
    private String token;

    @Column(name = "liftTime")
    private Integer liftTime;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdOn", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date createdOn;

    @Column(name = "expireOn")
    private Date expireOn;


    public UserSession() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getLiftTime() {
        return liftTime;
    }

    public void setLiftTime(Integer liftTime) {
        this.liftTime = liftTime;
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

    public Date getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(Date expireOn) {
        this.expireOn = expireOn;
    }


}

