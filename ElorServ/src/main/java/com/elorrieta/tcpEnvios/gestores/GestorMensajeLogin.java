package com.elorrieta.tcpEnvios.gestores;

import com.elorrieta.entities.User;
import com.elorrieta.service.UserService;
import com.elorrieta.tcpEnvios.mensajes.Mensaje;
import com.elorrieta.tcpEnvios.mensajes.parts.LoginParts;
import com.elorrieta.encriptado.CryptSHA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ObjectOutputStream;
import java.net.Socket;

@Component
public class GestorMensajeLogin {

    @Autowired
    private UserService userService;

    public void gestionar(Mensaje mensaje, Socket socket) {
        Mensaje mensajeRespuesta = new Mensaje();
        try {
            // Sacamos el LOGINPARTS del mensaje recibido
            LoginParts loginParts = (LoginParts) mensaje.getContenido();

            // Buscar el usuario en la base de datos
            User userLogin = userService.getUserEntityByUsername(loginParts.getUsername());

            // Valida la contraseña cifrada
            if (userLogin !=null) {
            	CryptSHA sha = new CryptSHA();
            	String passwordCifrada = sha.cifrarTexto(loginParts.getPassword());
            	System.out.println("Servidor - Password cifrada recibida: " + passwordCifrada);
            	if (!passwordCifrada.equals(userLogin.getPassword())) {
					// Contraseña incorrecta
					userLogin = null;
				}
            }

            // Generar la respuesta

            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            mensajeRespuesta.setTipoOperacion("RESPUESTA_LOGIN");
            if (userLogin != null) {
                // Login correcto
                mensajeRespuesta.setContenido(userLogin);
            } else {
                // Login incorrecto
                mensajeRespuesta.setContenido("ERROR");
            }

            // Enviar el objeto
            objectOutput.writeObject(mensajeRespuesta);
            objectOutput.flush();
            System.out.println("Servidor - Respuesta de login enviada al cliente." + mensajeRespuesta.getContenido());
            objectOutput.close();

        } catch (Exception e) {
            System.err.println("Servidor - Error al procesar login: " + e.getMessage());
            e.printStackTrace();
            try {
                // Intentar enviar mensaje de error al cliente
                ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
                mensajeRespuesta.setContenido("ERROR");
                objectOutput.writeObject(mensajeRespuesta);
                objectOutput.flush();
                objectOutput.close();
            } catch (Exception ex) {
                System.err.println("Servidor - Error al enviar respuesta de error: " + ex.getMessage());
            }
        }
    }
}
