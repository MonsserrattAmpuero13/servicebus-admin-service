package cl.duoc.pedidos360.servicebus_admin_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record QueueRequest(
        @NotBlank(message = "El nombre no puede estar vacio")
        @Pattern(regexp = "^[a-zA-Z0-9._-]{1,50}$",
                 message = "El nombre solo admite letras, numeros, punto, guion y guion bajo (maximo 50)")
        String name,
        @Min(value = 1, message = "El maximo de entregas debe ser al menos 1")
        @Max(value = 100, message = "El maximo de entregas no puede superar 100")
        int maxDeliveryCount) {}
