package cl.duoc.pedidos360.servicebus_admin_service.controller;

import cl.duoc.pedidos360.servicebus_admin_service.dto.QueueRequest;
import cl.duoc.pedidos360.servicebus_admin_service.dto.SubscriptionRequest;
import cl.duoc.pedidos360.servicebus_admin_service.dto.TopicRequest;
import cl.duoc.pedidos360.servicebus_admin_service.service.ServiceBusAdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicebus")
public class ServiceBusAdminController {

    private final ServiceBusAdminService service;

    public ServiceBusAdminController(ServiceBusAdminService service) {
        this.service = service;
    }

    // ---- Temas ----
    @PostMapping("/topics")
    public ResponseEntity<Void> crearTopic(@Valid @RequestBody TopicRequest request) {
        service.crearTopic(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/topics")
    public ResponseEntity<List<String>> listarTopics() {
        return ResponseEntity.ok(service.listarTopics());
    }

    @DeleteMapping("/topics/{name}")
    public ResponseEntity<Void> eliminarTopic(@PathVariable String name) {
        service.eliminarTopic(name);
        return ResponseEntity.noContent().build();
    }

    // ---- Suscripciones ----
    @PostMapping("/topics/{topic}/subscriptions")
    public ResponseEntity<Void> crearSubscription(@PathVariable String topic,
                                                  @Valid @RequestBody SubscriptionRequest request) {
        service.crearSubscription(topic, request.name(), request.maxDeliveryCount());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/topics/{topic}/subscriptions")
    public ResponseEntity<List<String>> listarSubscriptions(@PathVariable String topic) {
        return ResponseEntity.ok(service.listarSubscriptions(topic));
    }

    @DeleteMapping("/topics/{topic}/subscriptions/{name}")
    public ResponseEntity<Void> eliminarSubscription(@PathVariable String topic, @PathVariable String name) {
        service.eliminarSubscription(topic, name);
        return ResponseEntity.noContent().build();
    }

    // ---- Colas ----
    @PostMapping("/queues")
    public ResponseEntity<Void> crearCola(@Valid @RequestBody QueueRequest request) {
        service.crearCola(request.name(), request.maxDeliveryCount());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/queues")
    public ResponseEntity<List<String>> listarColas() {
        return ResponseEntity.ok(service.listarColas());
    }

    @DeleteMapping("/queues/{name}")
    public ResponseEntity<Void> eliminarCola(@PathVariable String name) {
        service.eliminarCola(name);
        return ResponseEntity.noContent().build();
    }
}
