package com.example.dearsanta.list.models;

import com.example.dearsanta.gift.models.Gift;
import com.example.dearsanta.users.models.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class GiftList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "giftList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gift> gifts = new ArrayList<>();

    public enum Status {
        CERRADA, PENDIENTE
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }
}
