package com.elorrieta.tcpEnvios.gestores;

import com.elorrieta.entities.User;
import com.elorrieta.service.UserService;
import com.elorrieta.tcpEnvios.mensajes.Mensaje;
import com.elorrieta.tcpEnvios.mensajes.parts.FilterParts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
public class GestorAlumnosFiltro {
    @Autowired
    private UserService userService;

    public void gestionar(Mensaje mensaje, Socket socket) {
        Mensaje mensajeRespuesta = new Mensaje();
        try {
            // sacamos el FilterParts del mensaje recibido
            FilterParts filterParts = (FilterParts) mensaje.getContenido();
            // buscamos los alumnos que cumplan con ambos filtros
            List<User> listaAlumnos = new ArrayList<User>();
            listaAlumnos = userService.getUserByFilter(filterParts);
            // generar la respuesta
            mensajeRespuesta.setTipoOperacion("RESPUESTA_ALUMNOS_FILTRO");
            mensajeRespuesta.setContenido(listaAlumnos);
            // enviar el objeto
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(mensajeRespuesta);
            // enviar el mensaje de respuesta
            System.out.println("Numero de alumnos con los filtros aplicados: " + listaAlumnos.size());
            objectOutput.flush();
            objectOutput.close();

        } catch (Exception e) {
            System.out.println("error con la lista de alumnos filtrados: " + e.getMessage());
        }
    }
}
