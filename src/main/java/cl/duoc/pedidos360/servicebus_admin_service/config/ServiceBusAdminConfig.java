package cl.duoc.pedidos360.servicebus_admin_service.config;

import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClient;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBusAdminConfig {

    @Value("${spring.cloud.azure.servicebus.connection-string}")
    private String connectionString;

    @Bean
    public ServiceBusAdministrationClient adminClient() {
        return new ServiceBusAdministrationClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }
}
