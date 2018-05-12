package app.config;

import app.components.service.SimpleMessageListener;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import org.apache.activemq.spring.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;


@Configuration
@ComponentScan(basePackages = { "app.components" })
public class RootConfig {
    //Service and Repository beans configuration
    //....



}
