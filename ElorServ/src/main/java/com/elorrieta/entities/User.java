package com.elorrieta.entities;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = -8632164890217148L;
    @Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

    @Expose
	@Column(name = "email", nullable = false, length = 100)
	private String email;

    @Expose
	@Column(name = "username", nullable = false, length = 50)
	private String username;


	@Column(name = "password", nullable = false)
	private String password;

    @Expose
	@Column(name = "nombre", length = 50)
	private String nombre;

    @Expose
	@Column(name = "apellidos", length = 100)
	private String apellidos;

    @Expose
	@Column(name = "dni", length = 20)
	private String dni;

    @Expose
	@Column(name = "direccion")
	private String direccion;

    @Expose
	@Column(name = "telefono1", length = 20)
	private String telefono1;

    @Expose
	@Column(name = "telefono2", length = 20)
	private String telefono2;

    @Expose
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "tipo_id", nullable = false)
	private Tipo tipo;

    @Expose
	@Column(name = "argazkia_url")
	private String argazkiaUrl;

	public User() {
	}

	public User(String email, String username, String password, Tipo tipo, String nombre, String apellidos, String dni,
			String direccion, String telefono1, String telefono2, String argazkiaUrl) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.tipo = tipo;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.direccion = direccion;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.argazkiaUrl = argazkiaUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getArgazkiaUrl() {
		return argazkiaUrl;
	}

	public void setArgazkiaUrl(String argazkiaUrl) {
		this.argazkiaUrl = argazkiaUrl;
	}

}