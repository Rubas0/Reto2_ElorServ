package com.elorrieta.tcpEnvios.gestores;

import com.elorrieta.entities.Horario;
import com.elorrieta.entities.User;
import com.elorrieta.service.HorarioService;
import com.elorrieta.tcpEnvios.mensajes.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

@Component
public class GestorHorarios {
    @Autowired
    private HorarioService horarioService;

    public void gestionar(Mensaje mensaje, Socket socket) {
        Mensaje mensajeRespuesta = new Mensaje();
        try {
            User profesor = (User) mensaje.getContenido();
            List<Horario> horarios = horarioService.getHorariosProfesor(profesor);
            mensajeRespuesta.setTipoOperacion("RESPUESTA_HORARIOS_PROFESOR");
            mensajeRespuesta.setContenido(horarios);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // Enviar el objeto
            objectOutputStream.writeObject(mensajeRespuesta);
            objectOutputStream.flush();
            System.out.println("Servidor - Respuesta de horarios enviada al cliente." + mensajeRespuesta.getContenido());
            objectOutputStream.close();
        } catch (Exception e) {
            System.out.println("Error al obtener los horarios: " + e.getMessage());
        }
    }
}
