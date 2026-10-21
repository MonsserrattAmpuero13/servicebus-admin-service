# servicebus-admin-service

Microservicio **administrador** del sistema Pedidos 360. Permite crear, listar y eliminar temas, suscripciones y colas de Azure Service Bus mediante una API REST.

## Endpoints

Base: `/api/servicebus` (puerto `8082`). Todos exigen JWT (Auth0).

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/topics` | Crear tema |
| `GET` | `/topics` | Listar temas |
| `DELETE` | `/topics/{name}` | Eliminar tema |
| `POST` | `/topics/{topic}/subscriptions` | Crear suscripción |
| `GET` | `/topics/{topic}/subscriptions` | Listar suscripciones |
| `DELETE` | `/topics/{topic}/subscriptions/{name}` | Eliminar suscripción |
| `POST` | `/queues` | Crear cola |
| `GET` | `/queues` | Listar colas |
| `DELETE` | `/queues/{name}` | Eliminar cola |

## Validación

- `name`: obligatorio, solo letras, números, punto, guion y guion bajo (máximo 50).
- `maxDeliveryCount`: entre 1 y 100.
- Errores: `400` con detalle de los campos inválidos, `409` si el recurso ya existe, `404` si no existe.

## Estructura

```
config/      ServiceBusAdminConfig, SecurityConfig
controller/  ServiceBusAdminController    (solo recibe y delega)
service/     ServiceBusAdminService       (toda la lógica de administración)
dto/         TopicRequest, SubscriptionRequest, QueueRequest
exception/   ApiExceptionHandler
```

## Configuración

| Variable | Uso |
|---|---|
| `SERVICEBUS_CONNECTION_STRING` | Cadena de conexión del namespace (obligatoria, no se sube al repositorio) |

## Ejecución y ejemplo

```
export SERVICEBUS_CONNECTION_STRING="<cadena de conexión>"
./mvnw spring-boot:run

curl -X POST http://localhost:8082/api/servicebus/topics \
  -H "Authorization: Bearer <token>" -H "Content-Type: application/json" \
  -d '{"name":"prueba-topic"}'
```

Sin token, la API responde `401`.

## Pruebas

```
./mvnw test
```

Las pruebas usan un cliente simulado y no requieren conexión a Azure.

## Equivalencias RabbitMQ → Azure Service Bus

| Concepto de la pauta (RabbitMQ) | Equivalente en Azure Service Bus |
|---|---|
| Exchange | Topic |
| Queue | Queue / Suscripción |
| Binding | Suscripción asociada al topic |
| `RabbitAdminService` | `ServiceBusAdminService` |
| `POST /queues`, `DELETE /queues/{name}` | `POST /queues`, `DELETE /queues/{name}` |
