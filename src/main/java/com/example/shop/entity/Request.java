package com.example.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @Temporal(TemporalType.DATE)
    @Column(name = "request_time")
    private Date requestTime;

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", isAccepted=" + isAccepted +
                ", user=" + user +
                ", car=" + car +
                ", requestTime=" + requestTime +
                '}';
    }
}
