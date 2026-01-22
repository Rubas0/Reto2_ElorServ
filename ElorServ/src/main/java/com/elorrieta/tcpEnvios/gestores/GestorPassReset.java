package com.elorrieta.tcpEnvios.gestores;

import com.elorrieta.service.UserService;
import com.elorrieta.tcpEnvios.mensajes.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Component
public class GestorPassReset {

    @Autowired
    private UserService userService;

    public void gestionar(Mensaje mensaje, Socket socket) {
        Mensaje mensajeRespuesta = new Mensaje();
        try {
            // Sacamos el correo del mensaje recibido
            String email = mensaje.getContenido().toString();
            // buscamos el usuario por email
            if(userService.getUserByEmail(email) != null) {
                // Si existe, enviamos un email con las instrucciones para resetear la contraseña
                userService.resetPassword(email);

            } else {
                // Si no existe, respondemos con error
                mensajeRespuesta.setTipoOperacion("RESPUESTA_PASS_RESET");
                mensajeRespuesta.setContenido("ERROR_EMAIL_NO_EXISTE");
            }
        }catch (Exception e) {
            System.out.println("error");
        }

    }
}
