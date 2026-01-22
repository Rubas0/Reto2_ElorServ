package com.elorrieta.tcpEnvios.mensajes;

import java.io.Serializable;

public class Mensaje implements Serializable {

    private static final long serialVersionUID = -1981368956803217148L;

    protected String tipoOperacion = null;
    protected Object contenido = null;

    public Mensaje() {
        super();
    }

    public Mensaje(String tipoOperacion, Object contenido) {
        this.tipoOperacion = tipoOperacion;
        this.contenido = contenido;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public Object getContenido() {
        return contenido;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }
}
