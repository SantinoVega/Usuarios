package com.demo.usuarios.response;

import com.demo.usuarios.dto.PedidosDTO;
import com.demo.usuarios.dto.UsuariosDTO;
import lombok.Data;

@Data
public class ResponseData {
    private PedidosDTO pedido;
    private UsuariosDTO usuario;
}
