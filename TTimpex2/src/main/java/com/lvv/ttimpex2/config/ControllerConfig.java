package com.lvv.ttimpex2.config;

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
