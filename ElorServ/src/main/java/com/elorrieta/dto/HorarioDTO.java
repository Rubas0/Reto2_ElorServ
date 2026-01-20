package com.elorrieta.dto;

/**
 * DTO para Horario
 */
public class HorarioDTO {
    private Integer id;
    private String dia;
    private Integer hora;
    private String aula;
    private String observaciones;
    private ProfesorDTO profe;
    private ModuloDTO modulo;

    // Constructores
    public HorarioDTO() {
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer integer) {
        this.hora = integer;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public ProfesorDTO getProfe() {
        return profe;
    }

    public void setProfe(ProfesorDTO profe) {
        this.profe = profe;
    }

    public ModuloDTO getModulo() {
        return modulo;
    }

    public void setModulo(ModuloDTO modulo) {
        this.modulo = modulo;
    }
}