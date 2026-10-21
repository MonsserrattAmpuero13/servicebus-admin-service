package cl.duoc.pedidos360.servicebus_admin_service.service;

import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class ServiceBusAdminServiceTest {

    private final ServiceBusAdministrationClient admin = mock(ServiceBusAdministrationClient.class);
    private final ServiceBusAdminService service = new ServiceBusAdminService(admin);

    @Test
    void creaTopicConNombreValido() {
        service.crearTopic("topic-prueba");
        verify(admin).createTopic("topic-prueba");
    }

    @Test
    void rechazaNombreVacio() {
        assertThrows(IllegalArgumentException.class, () -> service.crearTopic(""));
        verifyNoInteractions(admin);
    }

    @Test
    void rechazaNombreConCaracteresInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> service.crearCola("mi cola rara!", 3));
        verifyNoInteractions(admin);
    }

    @Test
    void eliminaColaConNombreValido() {
        service.eliminarCola("cola-prueba");
        verify(admin).deleteQueue("cola-prueba");
    }
}
