package com.example.dearsanta.gift.models;

import com.example.dearsanta.list.models.GiftList;
import com.example.dearsanta.relatives.models.Relative;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private GiftList giftList;

    @ManyToOne
    @JoinColumn(name = "relative_id", nullable = false)
    private Relative relative;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal price;
    private String url;

    public enum Status {
        POR_COMPRAR, PENDIENTE_DE_RECIBIR, RECIBIDO
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GiftList getGiftList() {
        return giftList;
    }

    public void setGiftList(GiftList giftList) {
        this.giftList = giftList;
    }

    public Relative getRelative() {
        return relative;
    }

    public void setRelative(Relative relative) {
        this.relative = relative;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
