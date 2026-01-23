package com.elorrieta.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "matriculaciones")
public class Matriculaciones implements Serializable {

    private static final long serialVersionUID = -8632164890217148L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "alum_id", nullable = false)
    private User alum;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ciclo_id", nullable = false)
    private Ciclo ciclo;

    @Column(name = "curso", nullable = false)
    private Integer curso;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    public Matriculaciones() {}

    public Matriculaciones(User alum, Ciclo ciclo, Integer curso, LocalDate fecha) {
        this.alum = alum;
        this.ciclo = ciclo;
        this.curso = curso;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getAlum() {
        return alum;
    }

    public void setAlum(User alum) {
        this.alum = alum;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public Integer getCurso() {
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}