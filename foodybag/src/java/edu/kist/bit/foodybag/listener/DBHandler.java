/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist.bit.foodybag.listener;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author Dell
 */
@WebListener
public class DBHandler implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
         
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("foodybagPU");
        
        sce.getServletContext().setAttribute("foodybagemf", emf);
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("foodybagemf");
        emf.close();
    }
}
