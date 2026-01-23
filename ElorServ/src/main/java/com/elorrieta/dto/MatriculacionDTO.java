package com.elorrieta. dto;

import java.time.LocalDate;

/**
 * DTO para Matriculaciones
 */
public class MatriculacionDTO {
    private Integer id;
    private Integer curso;
    private LocalDate fecha;
    private AlumnoDTO alumno;
    private CicloDTO ciclo;

    // Constructores
    public MatriculacionDTO() {
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public AlumnoDTO getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoDTO alumno) {
        this.alumno = alumno;
    }

    public CicloDTO getCiclo() {
        return ciclo;
    }

    public void setCiclo(CicloDTO ciclo) {
        this.ciclo = ciclo;
    }
}