package com.elorrieta.tcpEnvios.gestores;

import com.elorrieta.entities.User;
import com.elorrieta.tcpEnvios.mensajes.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class GestorImagenPerfil {

    @Value("${upload.path}")
    private String uploadPath;

    public void gestionar(Mensaje mensaje, Socket socket) {
        Mensaje mensajeRespuesta = new Mensaje();
        try {
            // Sacamos el usuario del mensaje recibido
            User user = (User) mensaje.getContenido();

            // Obtenemos la imagen del usuario
            String rutaImagen = uploadPath + user.getArgazkiaUrl();
            System.out.println("Servidor - Ruta imagen de perfil: " + rutaImagen);
            System.out.println("Seridor - Upload path: " + uploadPath);
            System.out.println("Servidor - Argazkia URL: " + user.getArgazkiaUrl());

            // Leer imagen
            Path path = Paths.get(rutaImagen);
            BufferedImage imagen = ImageIO.read(path.toFile());

            // Convertir a bytes
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(imagen, "jpg", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Preparamos la respuesta
            mensajeRespuesta.setTipoOperacion("RESPUESTA_PROFILE_IMG");
            mensajeRespuesta.setContenido(imageBytes);

            // Enviar el objeto
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(mensajeRespuesta);
            objectOutputStream.flush();
            System.out.println("Servidor - Respuesta de imagen de perfil enviada al cliente.");

            // Cerramos streams
            objectOutputStream.close();
            byteArrayOutputStream.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Servidor - Error al procesar imagen de perfil: " + e.getMessage());
        }
    }

}
