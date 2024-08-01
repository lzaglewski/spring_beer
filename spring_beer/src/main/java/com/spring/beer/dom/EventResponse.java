package com.spring.beer.dom;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class EventResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private ResponseType response;

    @Temporal(TemporalType.TIMESTAMP)
    private Date proposedDate;

    public enum ResponseType {
        CONFIRMED,
        DECLINED,
        PROPOSED_DATE
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ResponseType getResponse() {
        return response;
    }

    public void setResponse(ResponseType response) {
        this.response = response;
    }

    public Date getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(Date proposedDate) {
        this.proposedDate = proposedDate;
    }

    @Override
    public String toString() {
        return "EventResponse{" +
                "id=" + id +
                ", event=" + event +
                ", user=" + user +
                ", response=" + response +
                ", proposedDate=" + proposedDate +
                '}';
    }
}
