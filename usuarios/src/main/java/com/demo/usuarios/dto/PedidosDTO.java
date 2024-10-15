package com.demo.usuarios.dto;

import com.demo.usuarios.EstadoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidosDTO {
    private Integer id;
    private Integer usuarioId;
    private EstadoEnum estado;
    private Date fechaCreacion;
    private Double total;
}
