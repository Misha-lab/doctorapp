package com.misha.doctorapp.configs;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


/**
 * Конфигурационный класс для создания бинов, необходимых для работы SOAP
 */

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    private static final String URL_MAPPING = "/api/v1/timeslots/generate/*";
    private static final String LOCATION_URI = "/api/v1/timeslots/generate";
    private static final String GENERATOR_URL = "http://misha/doctorapp/generator";
    private static final String TIMESLOTS_PORT_NAME = "TimeslotsGeneratorPort";
    private static final String TIMESLOTS_XSD_NAME = "timeslots.xsd";

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, URL_MAPPING);
    }

    @Bean(name = "timeslots")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema timeslotsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(TIMESLOTS_PORT_NAME);
        wsdl11Definition.setLocationUri(LOCATION_URI);
        wsdl11Definition.setTargetNamespace(GENERATOR_URL);
        wsdl11Definition.setSchema(timeslotsSchema);
        return wsdl11Definition;
    }


    @Bean
    public XsdSchema timeslotsSchema() {
        return new SimpleXsdSchema(new ClassPathResource(TIMESLOTS_XSD_NAME));
    }

}