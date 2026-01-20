package com.elorrieta.dto;

/**
 * DTO para Reuniones
 */
public class ReunionDTO {
    private Integer id;
    private String estado;
    private String estadoEus;
    private String titulo;
    private String asunto;
    private String dia;
    private Byte semana;
    private Byte hora;
    private String aula;
    private String idCentro;
    private ProfesorDTO profesor;
    private AlumnoDTO alumno;

    // Constructores
    public ReunionDTO() {
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoEus() {
        return estadoEus;
    }

    public void setEstadoEus(String estadoEus) {
        this.estadoEus = estadoEus;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Byte getSemana() {
        return semana;
    }

    public void setSemana(Byte semana) {
        this.semana = semana;
    }

    public Byte getHora() {
        return hora;
    }

    public void setHora(Byte hora) {
        this.hora = hora;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
    }

    public ProfesorDTO getProfesor() {
        return profesor;
    }

    public void setProfesor(ProfesorDTO profesor) {
        this.profesor = profesor;
    }

    public AlumnoDTO getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoDTO alumno) {
        this.alumno = alumno;
    }
}