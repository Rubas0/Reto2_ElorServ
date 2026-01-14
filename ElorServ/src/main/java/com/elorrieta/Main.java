package com.elorrieta;

import com.elorrieta.dao.TipoDAO;
import com.elorrieta.dao.UserDAO;
import com.elorrieta.entities.Tipo;
import com.elorrieta.entities.User;
import org.hibernate.Session;


import java.util.List;

public class Main {
    public static void main(String[] args) {

        // mostrar todos los usuarios
//        UserDAO userDAO = new UserDAO();
//        List<User> users = userDAO.getAll();
//        for (User user : users) {
//            System.out.println("User: " + user.getUsername());
//        }

        // buscar usuario por id
//        User user = UsersDAO.getUserById(16);
//        System.out.println(user.getNombre());

        // añadir usuario
//        User newUser = new User("pruebainsert@gmail.com", "pruebainsert", "1234", TipoDAO.getTipoById(1));
//        UsersDAO.addUser(newUser);

        // borrar usuario
        /*UsersDAO.deleteUser(18);*/

        // añadir tipo
//        TipoDAO tipoDAO = new TipoDAO();
//        Tipo newTipo = new Tipo("Tipo de prueba", "proba Tipoa");
//        tipoDAO.add(newTipo);

        //borrar tipo
//        tipoDAO.delete(5);
    }
}