package cl.duoc.pedidos360.servicebus_admin_service.service;

import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClient;
import com.azure.messaging.servicebus.administration.models.CreateQueueOptions;
import com.azure.messaging.servicebus.administration.models.CreateSubscriptionOptions;
import com.azure.messaging.servicebus.administration.models.QueueProperties;
import com.azure.messaging.servicebus.administration.models.SubscriptionProperties;
import com.azure.messaging.servicebus.administration.models.TopicProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ServiceBusAdminService {

    private static final Logger log = LoggerFactory.getLogger(ServiceBusAdminService.class);
    private static final Pattern NOMBRE_VALIDO = Pattern.compile("^[a-zA-Z0-9._-]{1,50}$");

    private final ServiceBusAdministrationClient admin;

    public ServiceBusAdminService(ServiceBusAdministrationClient admin) {
        this.admin = admin;
    }

    // ---- Temas (equivalen a los exchanges) ----
    public void crearTopic(String name) {
        validarNombre(name);
        admin.createTopic(name);
        log.info("Tema creado: {}", name);
    }

    public List<String> listarTopics() {
        return admin.listTopics().stream().map(TopicProperties::getName).toList();
    }

    public void eliminarTopic(String name) {
        validarNombre(name);
        admin.deleteTopic(name);
        log.info("Tema eliminado: {}", name);
    }

    // ---- Suscripciones (equivalen a los bindings) ----
    public void crearSubscription(String topic, String name, int maxDeliveryCount) {
        validarNombre(topic);
        validarNombre(name);
        admin.createSubscription(topic, name,
                new CreateSubscriptionOptions().setMaxDeliveryCount(maxDeliveryCount));
        log.info("Suscripcion creada: {}/{}", topic, name);
    }

    public List<String> listarSubscriptions(String topic) {
        validarNombre(topic);
        return admin.listSubscriptions(topic).stream()
                .map(SubscriptionProperties::getSubscriptionName).toList();
    }

    public void eliminarSubscription(String topic, String name) {
        validarNombre(topic);
        validarNombre(name);
        admin.deleteSubscription(topic, name);
        log.info("Suscripcion eliminada: {}/{}", topic, name);
    }

    // ---- Colas ----
    public void crearCola(String name, int maxDeliveryCount) {
        validarNombre(name);
        admin.createQueue(name, new CreateQueueOptions().setMaxDeliveryCount(maxDeliveryCount));
        log.info("Cola creada: {}", name);
    }

    public List<String> listarColas() {
        return admin.listQueues().stream().map(QueueProperties::getName).toList();
    }

    public void eliminarCola(String name) {
        validarNombre(name);
        admin.deleteQueue(name);
        log.info("Cola eliminada: {}", name);
    }

    private void validarNombre(String nombre) {
        if (nombre == null || !NOMBRE_VALIDO.matcher(nombre).matches()) {
            throw new IllegalArgumentException(
                    "Nombre invalido: solo letras, numeros, punto, guion y guion bajo (maximo 50)");
        }
    }
}
