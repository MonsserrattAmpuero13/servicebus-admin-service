package cl.duoc.pedidos360.servicebus_admin_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TopicRequest(
        @NotBlank(message = "El nombre no puede estar vacio")
        @Pattern(regexp = "^[a-zA-Z0-9._-]{1,50}$",
                 message = "El nombre solo admite letras, numeros, punto, guion y guion bajo (maximo 50)")
        String name) {}
