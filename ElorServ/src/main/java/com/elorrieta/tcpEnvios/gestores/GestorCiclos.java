package com.elorrieta.tcpEnvios.gestores;

import com.elorrieta.entities.Ciclo;
import com.elorrieta.service.CicloService;
import com.elorrieta.tcpEnvios.mensajes.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

@Component
public class GestorCiclos {
    @Autowired
    private CicloService cicloService;

    public void gestionar(Mensaje mensaje, Socket socket) {
        Mensaje mensajeRespuesta = new Mensaje();
        try {
            // buscamos todos los ciclos existentes en la base de datos
            List<Ciclo> ciclos = cicloService.getAllEntities();
            // Generar la respuesta
            mensajeRespuesta.setTipoOperacion("RESPUESTA_CICLOS");
            mensajeRespuesta.setContenido(ciclos);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            // Enviar el objeto
            outputStream.writeObject(mensajeRespuesta);
            outputStream.flush();
            System.out.println("Servidor - Respuesta de ciclos enviada al cliente." + mensajeRespuesta.getContenido());
            outputStream.close();
        } catch (Exception e) {
            System.err.println("Servidor - Error al procesar ciclos: " + e.getMessage());
        }
    }
}
