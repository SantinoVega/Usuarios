package com.demo.usuarios.service;

import com.demo.usuarios.dto.PedidosDTO;
import com.demo.usuarios.dto.UsuariosDTO;
import com.demo.usuarios.model.UsuariosRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.sql.SQLDataException;
import java.util.List;

public interface IUsuariosService {
    UsuariosRequest save(UsuariosRequest usuario);
    UsuariosDTO getById(Integer id);
    List<UsuariosDTO> getAll();
    UsuariosRequest update(UsuariosRequest usuario) throws SQLDataException;
    Integer deleteById(Integer id);
    List<PedidosDTO> getAllByUsuarioId(Integer usuarioId);
}
