package org.example.soap.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

@Configuration
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(
            org.example.soap.request.ObtenerProductosRequest.class,
            org.example.soap.response.ObtenerProductosResponse.class,
            org.example.soap.request.ImportarProductosMasivosRequest.class,
            org.example.soap.response.ImportarProductosMasivosResponse.class,
            org.example.soap.schema.ProductoSoap.class
        );
        return marshaller;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptorClient() {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setSecurementActions("UsernameToken");
        securityInterceptor.setSecurementUsername("admin");
        securityInterceptor.setSecurementPassword("secret");
        return securityInterceptor;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        template.setDefaultUri("http://localhost:8080/ws/productos");
        template.setInterceptors(new ClientInterceptor[]{securityInterceptorClient()});
        return template;
    }
}
