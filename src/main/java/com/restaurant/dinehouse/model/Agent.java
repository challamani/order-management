package com.restaurant.dinehouse.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "agent")
public class Agent implements Serializable {

    private static final long serialVersionUID = -1604333737340047541L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    public Agent() {
    }

    @Column(name = "agentId")
    private String agentId;

    @Column(name = "phoneNo")
    private String phoneNo;

    @Column(name = "status")
    private String status;

    @Column(name = "email")
    private String email;

    @Column(name = "dob")
    private String dob;

    @Column(name = "address")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName=" + lastName +
                ", agentId='" + agentId + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
