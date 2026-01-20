package com.elorrieta.dto;

/**
 * DTO para Ciclo
 */
public class CicloDTO {
    private Integer id;
    private String nombre;

    // Constructores
    public CicloDTO() {
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}