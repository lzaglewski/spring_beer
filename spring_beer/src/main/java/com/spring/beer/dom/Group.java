package com.spring.beer.dom;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "user_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User admin;

    @OneToMany(mappedBy = "group")
    private Set<GroupMembership> groupMemberships;

    @OneToMany(mappedBy = "group")
    private Set<Event> events;

    public Group() {
    }

    public Group(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
