package br.com.crud.gestaousuario.config;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private int port;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Bean
    public JmsConnectionFactory jmsConnectionFactory() {
        String remoteUri = String.format("amqp://%s:%d", host, port);
        JmsConnectionFactory factory = new JmsConnectionFactory();
        factory.setRemoteURI(remoteUri);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory;
    }
}
