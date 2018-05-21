package app.config;

import app.components.service.SimpleMessageListener;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.SystemException;

@Configuration
@Import({ TransactionConfig.class })
@ComponentScan(basePackages = { "app.components" })
@EnableTransactionManagement
public class JmsConfig {
    @Autowired
    TransactionConfig transactionConfig;
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public ActiveMQXAConnectionFactory connectionFactory(){
        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        return connectionFactory;
    }

    @Bean
    public AtomikosConnectionFactoryBean connectionFactoryBean(){
        AtomikosConnectionFactoryBean connectionFactoryBean = new AtomikosConnectionFactoryBean();
        connectionFactoryBean.setUniqueResourceName("weathertopic");
        connectionFactoryBean.setXaConnectionFactory(connectionFactory());
        return connectionFactoryBean;
    }

    @Bean
    public ActiveMQTopic weatherTopic(){
        return new ActiveMQTopic("weathertopic");
    }

    @Bean
    @Autowired
    public DefaultMessageListenerContainer listenerContainer(SimpleMessageListener messageListener) throws SystemException {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setTransactionManager(transactionConfig.transactionManager());
        container.setSessionTransacted(true);
        container.setPubSubDomain(true);
        container.setDestination(weatherTopic());
        container.setMessageListener(messageListener);
        return container;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setConnectionFactory(connectionFactoryBean());
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setDefaultDestination(weatherTopic());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }
}
