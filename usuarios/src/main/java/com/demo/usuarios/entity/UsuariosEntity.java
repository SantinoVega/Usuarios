package com.demo.usuarios.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


/**
 * Esta clase representa la entidad UsuariosEntity en la base de datos. Es utilizada para guardar la informacion de los usuarios.
 *
 * @author Daniel Ivan Martinez R.
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuariosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;

    @Column(name = "name", length = 100, nullable = false)
    private String nombre;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "registration_date")
    private Date registrationDate;
}
