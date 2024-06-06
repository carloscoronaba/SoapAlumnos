package com.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        // Crea una instancia del servlet MessageDispatcherServlet
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext); // Establece el contexto de la aplicación
        servlet.setTransformWsdlLocations(true); // Transforma las ubicaciones WSDL
        // Registra el servlet en la ruta /ws/*
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    // Define el WSDL para el servicio web
    @Bean(name = "alumnos")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema productsSchema) {
        // Crea una definición WSDL 1.1 por defecto
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AlumnoPort"); // Define el nombre del puerto
        wsdl11Definition.setLocationUri("/ws"); // Establece la URI del servicio
        wsdl11Definition.setTargetNamespace("http://www.soap.com/gen"); // Define el espacio de nombres objetivo
        wsdl11Definition.setSchema(productsSchema); // Establece el esquema XSD
        return wsdl11Definition;
    }

    // Carga el esquema XSD desde el classpath
    @Bean
    public XsdSchema productsSchema() {
        // Crea un esquema XSD simple basado en el archivo products.xsd
        return new SimpleXsdSchema(new ClassPathResource("xsd/alumnos.xsd"));
    }
}
