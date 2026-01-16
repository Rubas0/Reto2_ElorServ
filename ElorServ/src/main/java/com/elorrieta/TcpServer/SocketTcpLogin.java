//package com.elorrieta.TcpServer;
//
//import com.elorrieta.controllers.ControladorJSON;
//import com.elorrieta.dao.UserDAO;
//import com.elorrieta.entities.User;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.lang.reflect.Type;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//
//public class SocketTcpLogin {
//    public static void main(String[] args) {
//        int puertoServer = 49171;
//
//        try (ServerSocket serverSocket = new ServerSocket(puertoServer)) {
//            System.out.println("Servidor - Iniciando en el puerto " + puertoServer);
//
//            while (true) {
//                try (Socket socket = serverSocket.accept()) {
//                    System.out.println("Servidor - Cliente conectado: " + socket.getInetAddress());
//
//                    // Recibir el archivo JSON del cliente
//                    try (FileOutputStream fos = new FileOutputStream("user.json")) {
//                        byte[] buffer = new byte[4096];
//                        int bytesRead;
//                        while ((bytesRead = socket.getInputStream().read(buffer)) != -1) {
//                            fos.write(buffer, 0, bytesRead);
//                            if (socket.getInputStream().available() == 0) break;
//                        }
//                    }
//                    System.out.println("Servidor - Archivo JSON recibido del cliente.");
//
//                    // Leer JSON y verificar credenciales con el ORM
//                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                    User user;
//                    try (FileReader reader = new FileReader("user.json")) {
//                        Type objectType = new TypeToken<User>() {
//                        }.getType();
//                        user = gson.fromJson(reader, objectType);
//                    }
//
//                    System.out.println("Servidor - Usuario recibido: " + user.getUsername());
//                    User loggedInUser = UserDAO.login(user);
//
//                    if (loggedInUser != null) {
//                        System.out.println("Servidor - Login correcto para el usuario: " + user.getUsername());
//                        ControladorJSON.UserToJSON(loggedInUser);
//                    } else {
//                        System.out.println("Servidor - Login incorrecto para el usuario: " + user.getUsername());
//                        User errorUser = new User();
//                        errorUser.setUsername("ERROR");
//                        ControladorJSON.UserToJSON(errorUser);
//                    }
//
//                    // Enviar respuesta al cliente
//                    try (FileInputStream fis = new FileInputStream("user.json")) {
//                        byte[] buffer = new byte[4096];
//                        int bytesRead;
//                        while ((bytesRead = fis.read(buffer)) != -1) {
//                            socket.getOutputStream().write(buffer, 0, bytesRead);
//                        }
//                        socket.getOutputStream().flush();
//                    }
//                    System.out.println("Servidor - Respuesta enviada al cliente.");
//
//                } catch (Exception e) {
//                    System.out.println("Servidor - Error al procesar cliente: " + e.getMessage());
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Servidor - Error: " + e.getMessage());
//        }
//    }
//}
//
