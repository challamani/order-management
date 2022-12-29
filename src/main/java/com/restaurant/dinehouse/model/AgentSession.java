package com.restaurant.dinehouse.model;

import javax.persistence.*;

@Entity
@Table(name = "agent_session")
public class AgentSession {
    private static final long serialVersionUID = -1604333737340047500L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agentId")
    private String agentId;

    @Column(name = "token")
    private String token;

    @Column(name = "liftTime")
    private Integer liftTime;

    @Column(name = "status")
    private String status;

    public AgentSession() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

