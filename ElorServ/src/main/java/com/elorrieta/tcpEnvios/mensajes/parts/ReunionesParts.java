package com.elorrieta.tcpEnvios.mensajes.parts;

import java.io.Serializable;
import java.util.List;

import com.elorrieta.entities.Reuniones;
import com.elorrieta.entities.User;

public class ReunionesParts implements Serializable {

	private static final long serialVersionUID = -8632164890217148L;
	private List<Reuniones> reuniones;
	private User profesor;

	public ReunionesParts() {
	}

	public ReunionesParts(List<Reuniones> reuniones, User profesor) {
		this.reuniones = reuniones;
		this.profesor = profesor;
	}

	public List<Reuniones> getReuniones() {
		return reuniones;
	}

	public void setReuniones(List<Reuniones> reuniones) {
		this.reuniones = reuniones;
	}

	public User getProfesor() {
		return profesor;
	}

	public void setProfesor(User profesor) {
		this.profesor = profesor;
	}
}
