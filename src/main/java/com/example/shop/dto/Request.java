package com.example.shop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Request {

    private Long id;
    private User user;
    private Car car;
    private LocalDateTime requestTime;

    public Request() {
    }

    public static RequestBuilder requestBuilder() {
        return new Request().new RequestBuilder();
    }

    public class RequestBuilder {

        private RequestBuilder() {
        }

        public RequestBuilder id(Long id) {
            Request.this.id = id;
            return this;
        }

        public RequestBuilder user(User user) {
            Request.this.user = user;
            return this;
        }

        public RequestBuilder car(Car car) {
            Request.this.car = car;
            return this;
        }

        public Request build() {
            return Request.this;
        }
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", user=" + user +
                ", car=" + car +
                ", requestTime=" + requestTime +
                '}';
    }
}
