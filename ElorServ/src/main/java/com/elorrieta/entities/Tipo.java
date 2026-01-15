package com.elorrieta.entities;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

@Entity
@Table(name = "tipos")
public class Tipo {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Expose
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Expose
    @Column(name = "name_eu", length = 50)
    private String nameEu;

    public Tipo() {}

    public Tipo(String name, String nameEu) {
        this.name = name;
        this.nameEu = nameEu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEu() {
        return nameEu;
    }

    public void setNameEu(String nameEu) {
        this.nameEu = nameEu;
    }

}