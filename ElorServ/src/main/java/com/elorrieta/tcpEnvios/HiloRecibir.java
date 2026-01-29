package com.elorrieta.tcpEnvios;

import com.elorrieta.config.ApplicationContextProvider;
import com.elorrieta.tcpEnvios.gestores.*;
import com.elorrieta.tcpEnvios.mensajes.Mensaje;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HiloRecibir extends Thread {

    Socket socket;

    public HiloRecibir(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        try {
            // Obtenemos el mensaje
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            Object receivedObject = objectInput.readObject();
            Mensaje mensaje = (Mensaje) receivedObject;

            // Procesamos el mensaje
            String tipo = mensaje.getTipoOperacion();

            switch (tipo) {
                case "LOGIN":
                    GestorMensajeLogin gestorMensajeLogin = ApplicationContextProvider.getBean(GestorMensajeLogin.class);
                    gestorMensajeLogin.gestionar(mensaje, socket);
                    break;
                case "RESET_PASSWORD":
                    GestorPassReset gestorPassReset = ApplicationContextProvider.getBean(GestorPassReset.class);
                    gestorPassReset.gestionar(mensaje, socket);
                    break;
                case "GET_ALUMNOS_PROFESOR":
                    GestorAlumnosProfesor gestorAlumnosProfesor = ApplicationContextProvider.getBean(GestorAlumnosProfesor.class);
                    gestorAlumnosProfesor.gestionar(mensaje, socket);
                    break;
                case "GET_CICLOS":
                    GestorCiclos gestorCiclos = ApplicationContextProvider.getBean(GestorCiclos.class);
                    gestorCiclos.gestionar(mensaje, socket);
                    break;
                case "GET_ALUMNOS_FILTRO":
                    GestorAlumnosFiltro gestorAlumnosFiltro = ApplicationContextProvider.getBean(GestorAlumnosFiltro.class);
                    gestorAlumnosFiltro.gestionar(mensaje, socket);
                    break;
                case "GET_HORARIO_PROFESOR":
                    GestorHorarios gestorHorarios = ApplicationContextProvider.getBean(GestorHorarios.class);
                    gestorHorarios.gestionar(mensaje, socket);
                    break;
                case "GET_PROFILE_IMG":
                    GestorImagenPerfil gestorImagenPerfil = ApplicationContextProvider.getBean(GestorImagenPerfil.class);
                    gestorImagenPerfil.gestionar(mensaje, socket);
                    break;
                case "GET_REUNIONES_PROFESOR":
                    GestorReuniones gestorReuniones = ApplicationContextProvider.getBean(GestorReuniones.class);
                    gestorReuniones.gestionar(mensaje, socket);
                    break;
                case "GET_PROFESORES":
                    GestorProfesores gestorProfesores = ApplicationContextProvider.getBean(GestorProfesores.class);
                    gestorProfesores.gestionar(mensaje, socket);
                    break;
                    case "UPDATE_REUNIONES_PROFESOR":
                    GestorReuniones gestorUpdateReuniones = ApplicationContextProvider.getBean(GestorReuniones.class);
                    gestorUpdateReuniones.updateReuniones(mensaje, socket);
                    break;
                default:
                    System.out.println("Error super chungo");
            }


        } catch (Exception e) {
            System.out.println("Servidor - Error al procesar cliente: " + e.getMessage());
        } finally {
            // CERRAR SOCKET
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Servidor - Error al cerrar el socket: " + e.getMessage());
                }
            }
        }


    }
}
