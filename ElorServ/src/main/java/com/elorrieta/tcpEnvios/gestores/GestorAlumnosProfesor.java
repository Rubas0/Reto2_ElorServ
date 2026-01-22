package com.elorrieta.tcpEnvios.gestores;

import com.elorrieta.entities.User;
import com.elorrieta.service.UserService;
import com.elorrieta.tcpEnvios.mensajes.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

@Component
public class GestorAlumnosProfesor {

    @Autowired
    private UserService userService;

    public void gestionar(Mensaje mensaje, Socket socket) {
        Mensaje mensajeRespuesta = new Mensaje();
        try {
            // Sacamos el User del mensaje recibido
            User profesor = (User) mensaje.getContenido();
            // Buscamos los alumnos del profesor
            List<User> listaAlumnos = userService.getStudentsByProfessorId(profesor.getId());

            // Generar la respuesta

            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            mensajeRespuesta.setTipoOperacion("RESPUESTA_ALUMNOS_PROFESOR");
            if (listaAlumnos != null) {
                // Busqueda correcta
                mensajeRespuesta.setContenido(listaAlumnos);
            } else {
                // Busqueda incorrecta
                mensajeRespuesta.setContenido("ERROR");
            }

            // Enviar el objeto
            objectOutput.writeObject(mensajeRespuesta);
            // Enviar el mensaje de respuesta
            System.out.println("Numero de alumnos del profesor " + profesor.getUsername() + ": " + listaAlumnos.size());

            objectOutput.flush();
            objectOutput.close();

        } catch (Exception e) {
            System.out.println("error con la lista de alumnos" + e.getMessage());
        }
    }
}
