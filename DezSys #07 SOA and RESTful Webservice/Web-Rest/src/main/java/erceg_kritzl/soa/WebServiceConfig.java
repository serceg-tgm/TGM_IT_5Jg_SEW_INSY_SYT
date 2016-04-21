package erceg_kritzl.soa;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
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
 * Diese Klasse ist noetig zum Betreiben eines SOAP-Services mit Spring.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    /**
     * Ermoeglicht es in Spring eine SOAP-Applikation auszufuehren.
     *
     * @param applicationContext Der Kontext der Applikation
     * @return Der Servlet-Dispatcher
     */
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/persons/search/*");
    }

    /**
     * Generiert WSDL Definition mit Hilfe des XSD-Schemas. Erreichbar unter "persons/search/personRecords.wsdl"
     *
     * @param dataRecordsSchema Das definierte XSD-Schema
     * @return Die WSDL Definition
     */
    @Bean(name = "personRecords")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema dataRecordsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PersonRecordsPort");
        wsdl11Definition.setLocationUri("/persons/search");
        wsdl11Definition.setTargetNamespace(PersonRecordEndpoint.NAMESPACE_URI);
        wsdl11Definition.setSchema(dataRecordsSchema);
        return wsdl11Definition;
    }

    /**
     * Ladet das XSD-File in ein Objekt
     *
     * @return Das geladene XSD-Schema
     */
    @Bean
    public XsdSchema dataRecordsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("/definitions/persondata.xsd"));
    }
}