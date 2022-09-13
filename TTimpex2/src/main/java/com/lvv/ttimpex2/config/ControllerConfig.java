package com.lvv.ttimpex2.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author Vitalii Lypovetskyi
 */
public class ControllerConfig {}//implements WebApplicationInitializer {
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext root =
//                new AnnotationConfigWebApplicationContext();
//        root.register(SpringConfig.class);
//
//        root.refresh();
//        root.setServletContext(servletContext);
//
//        servletContext.addListener(new ContextLoaderListener(root));
//
//        DispatcherServlet dv =
//                new DispatcherServlet(new GenericWebApplicationContext());
//
//        ServletRegistration.Dynamic appServlet = servletContext.addServlet("test-mvc", dv);
//        appServlet.setLoadOnStartup(1);
//        appServlet.addMapping("/");
//    }
//}
