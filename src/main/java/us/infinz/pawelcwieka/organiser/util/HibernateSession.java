package us.infinz.pawelcwieka.organiser.util;

import org.hibernate.Session;

public class HibernateSession {

    private static HibernateSession instance = null;

    Session session;

    public static HibernateSession getInstance(){

        if(instance ==null){

            instance = new HibernateSession();

        }

        return instance;

    }

    private  HibernateSession(){

        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Session created.");

    }

    public Session getSession() {
        return session;
    }

    public void closeSession() {

        this.session.close();
    }



}
