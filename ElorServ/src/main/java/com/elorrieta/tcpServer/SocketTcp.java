package com.elorrieta.tcpServer;

import com.elorrieta.threads.HiloRecibir;

import java.net.ServerSocket;
import java.net.Socket;


public class SocketTcp extends Thread {
    ServerSocket serverSocket = null;
    int puertoServer = 49171;

    public void run() {

        try {
            serverSocket = new ServerSocket(puertoServer);
            System.out.println("Servidor - Iniciando en el puerto " + puertoServer);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Servidor - Cliente conectado: " + socket.getInetAddress());

                    HiloRecibir hilo = new HiloRecibir(socket);
                    hilo.start();

                } catch (Exception e) {
                    System.out.println("Servidor - Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Servidor - Error al iniciar el servidor: " + e.getMessage());
        }
    }
}


