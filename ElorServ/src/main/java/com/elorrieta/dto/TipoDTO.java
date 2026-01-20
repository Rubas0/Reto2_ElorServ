package com.elorrieta.dto;

/**
 * DTO para Tipo
 */
public class TipoDTO {
    private Integer id;
    private String name;
    private String nameEu;

    // Constructores
    public TipoDTO() {
    }

    // Getters y Setters
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