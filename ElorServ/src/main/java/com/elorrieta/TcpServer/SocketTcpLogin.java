//package com.elorrieta.TcpServer;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import com.elorrieta.entities.User;
//import com.elorrieta.dao.UserDAO;
//
//public class SocketTcpLogin {
//	/**
//	 * Crea un programa Cliente que le pase al Servidor un objeto tipo Persona. El
//	 * Servidor evaluará si la Persona se encuentra en el ORM, y si es así, le
//	 * responderá con otro objeto Persona con los datos del usuario. Si no,
//	 * responderá con un objeto nulo.
//	 */
//
//	public static void main(String[] args) {
//
//		ServerSocket serverSocket = null;
//		Socket socket = null;
//		int puertoServer = 49171; // Coge uno libre...
//
//		try {
//			
//			
//	  	  } catch (IOException ioe) {
//	 		  ioe.printStackTrace();
//		  } finally {
//			  // Cerramos en el orden inverso al que las hemos abierto
//			  System.out.println("Servidor - Cerrando conexiones...");
//
//			  try {
//				  serverSocket.close();
//			  } catch (IOException e) {
//				// No importa...
//			}
//		}
//
//		System.out.println("Servidor - Finalizado!");
//	}
//}
