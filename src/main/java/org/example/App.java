package org.example;

import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        System.out.println("Test");

        Session session = HibernateUtil.get().openSession();

        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }
}
