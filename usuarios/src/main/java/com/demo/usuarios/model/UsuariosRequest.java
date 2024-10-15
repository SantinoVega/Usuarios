package com.demo.usuarios.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * Esta clase requesenta el request para crear o actualizar un Usuario.
 * Contiene los atributos y algunas validaciones para un Pedido.
 * el campo nombre debe ser String no mayor a 100, no puede eser vacio o en blanco,
 * el campo email deve ser String no mayor a 100, debe tener formato de correo electronico
 */
@Data
public class UsuariosRequest {
    private Integer id;
    @NotBlank(message = "El nombre no puede ser vacio o en blanco")
    @Size(max = 100)
    private String nombre;
    @Email(message = "El email debe contener formato de email")
    @Size(max = 100)
    private String email;
}
