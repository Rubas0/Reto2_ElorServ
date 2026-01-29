package com.elorrieta.tcpEnvios.gestores;

import com.elorrieta.entities.Reuniones;
import com.elorrieta.entities.User;
import com.elorrieta.service.ReunionService;
import com.elorrieta.tcpEnvios.mensajes.Mensaje;
import com.elorrieta.tcpEnvios.mensajes.parts.ReunionesParts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

@Component
public class GestorReuniones {

    @Autowired
    private ReunionService reunionService;

    public void gestionar(Mensaje mensaje, Socket socket) {
        Mensaje mensajeRespuesta = new Mensaje();
        try {
            // Recoger el usuario del mensaje
            User usuario = (User) mensaje.getContenido();
            // Buscar las reuniones asignadas al profesor
            List<Reuniones> reuniones = reunionService.getReunionesProfesorEntity(usuario.getId());
            // Preparar el mensaje de respuesta
            mensajeRespuesta.setTipoOperacion("REUNIONES_PROFESOR_RESPONSE");
            mensajeRespuesta.setContenido(reuniones);
            // Enviar el mensaje de respuesta
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(mensajeRespuesta);
            objectOutput.flush();
            // Cerrar el stream de salida y el socket
            objectOutput.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Error en GestorReuniones: " + e.getMessage());
        }
    }

    public void updateReuniones(Mensaje mensaje, Socket socket) {
        Mensaje mensajeRespuesta = new Mensaje();
        try {
            // Recoger el contenido del mensaje
            ReunionesParts reunionParts = (ReunionesParts) mensaje.getContenido();
            // separar los datos de loginparts
            User usuario = reunionParts.getProfesor();
            List<Reuniones> reuniones = reunionParts.getReuniones();

            // Actualizar las reuniones del profesor
            for (Reuniones reunion : reuniones) {
                reunionService.updateEntity(reunion);
            }
            // Preparar el mensaje de respuesta
            mensajeRespuesta.setTipoOperacion("EXITO_UPDATE_REUNIONES_PROFESOR");
            mensajeRespuesta.setContenido(reuniones);
            // Enviar el mensaje de respuesta
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(mensajeRespuesta);
            objectOutput.flush();
            // Cerrar el stream de salida y el socket
            objectOutput.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Error en GestorReuniones: " + e.getMessage());
        }
    }
}
