package com.elorrieta.dto;

/**
 * DTO para Modulo
 */
public class ModuloDTO {
    private Integer id;
    private String nombre;
    private String nombreEus;
    private Integer horas;
    private Byte curso;
    private CicloDTO ciclo;

    // Constructores
    public ModuloDTO() {
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

    public String getNombreEus() {
        return nombreEus;
    }

    public void setNombreEus(String nombreEus) {
        this.nombreEus = nombreEus;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Byte getCurso() {
        return curso;
    }

    public void setCurso(Byte curso) {
        this.curso = curso;
    }

    public CicloDTO getCiclo() {
        return ciclo;
    }

    public void setCiclo(CicloDTO ciclo) {
        this.ciclo = ciclo;
    }
}