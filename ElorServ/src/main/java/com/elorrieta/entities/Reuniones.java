package com.elorrieta.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "reuniones")
public class Reuniones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reunion", nullable = false)
    private Integer id;

    @ColumnDefault("'pendiente'")
    @Lob
    @Column(name = "estado")
    private String estado;

    @Lob
    @Column(name = "estado_eus")
    private String estadoEus;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "profesor_id")
    private User profesor;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "alumno_id")
    private User alumno;

    @ColumnDefault("'15112'")
    @Column(name = "id_centro", length = 20)
    private String idCentro;

    @Column(name = "titulo", length = 150)
    private String titulo;

    @Column(name = "asunto", length = 200)
    private String asunto;

    @Column(name = "aula", length = 20)
    private String aula;

    @Column(name = "dia")
    private Integer dia;

    @Column(name = "semana")
    private Integer semana;

    @Column(name = "hora")
    private Integer hora;

    public  Reuniones() {}

    public Reuniones(String estado, String estadoEus, User profesor, User alumno, String idCentro, String titulo, String asunto, String aula, Integer dia, Integer semana, Integer hora) {
        this.estado = estado;
        this.estadoEus = estadoEus;
        this.profesor = profesor;
        this.alumno = alumno;
        this.idCentro = idCentro;
        this.titulo = titulo;
        this.asunto = asunto;
        this.aula = aula;
        this.dia = dia;
        this.semana = semana;
        this.hora = hora;
    }

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

    public User getProfesor() {
        return profesor;
    }

    public void setProfesor(User profesor) {
        this.profesor = profesor;
    }

    public User getAlumno() {
        return alumno;
    }

    public void setAlumno(User alumno) {
        this.alumno = alumno;
    }

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
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

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

}