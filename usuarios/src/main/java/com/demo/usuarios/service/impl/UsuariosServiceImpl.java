package com.demo.usuarios.service.impl;

import com.demo.usuarios.dto.PedidosDTO;
import com.demo.usuarios.dto.UsuariosDTO;
import com.demo.usuarios.entity.UsuariosEntity;
import com.demo.usuarios.model.UsuariosRequest;
import com.demo.usuarios.repository.UsuariosRepository;
import com.demo.usuarios.response.ResponseGenerico;
import com.demo.usuarios.service.IUsuariosService;

import java.sql.SQLDataException;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * La clase UsuariosService contiene los
 * metodos con la logica para hacer CRUD con datos del usuario.
 *
 * @author Daniel Ivan Martinez R.
 */
@Service
@Slf4j
public class UsuariosServiceImpl implements IUsuariosService {

    @Value("${pedidos.api.url}")
    String pedidosApiUrl;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UsuariosRepository usuariosRepository;


    /**
     * Guarda un nuevo usuario con la informacion contenida en {@link UsuariosRequest}, sino puede guardarla devuleve una exception.
     *
     * @param usuario El objeto {@link UsuariosRequest} contiene la informacion necesaria para guaradar un nuevo objeto en BD.
     *
     * @return El mismo objeto {@link UsuariosRequest} que es pasado como parametro. Con esto indicamos que se guardo con exito en BD.
     * @version(1.0.0)
     * @author Daniel Ivan Martinez R.
     */
    public UsuariosRequest save(UsuariosRequest usuario) {
        try {
            UsuariosEntity entity = setData(usuario);
            usuariosRepository.save(entity);
            return usuario;
        }catch (DataIntegrityViolationException e){
            log.error("Error: correo duplicado en BD: ", e);
            throw e;
        }catch (Exception e){
            log.error("Error al guardar en la base de datos", e);
            throw e;
        }
    }


    /**
     * Este metodo regresa un obejto {@link UsuariosDTO} con el {@code id} de la BD.
     *
     * @param id Es el identificador unico que sera recibido.
     *
     * @return El objeto {@link UsuariosDTO} contien los datos existentes en BD.
     * @author Daniel Ivan Martinez R.
     */
    public UsuariosDTO getById(Integer id) {
        try {
            Optional<UsuariosEntity> entity = usuariosRepository.findById(id);
            return entity.map(usuariosEntity -> UsuariosDTO.builder()
                    .id(usuariosEntity.getId())
                    .nombre(usuariosEntity.getNombre())
                    .email(usuariosEntity.getEmail())
                    .registrationDate(usuariosEntity.getRegistrationDate())
                    .build()).orElse(null);
        }catch (Exception e){
            log.error("Error al consultar el usuario por Id: ", e);
            throw e;
        }
    }

    /**
     * Este metodo devuelve una lista de tipo {@link List<UsuariosDTO>} con todos los usuarios en la BD.
     *
     * @return devuelve una lista de tipo {@link List<UsuariosDTO>} con todos los usuarios de la BD.
     * @author Daniel Ivan Martinez R.
     */
    public List<UsuariosDTO> getAll(){
        try{
            return usuariosRepository.findAll().stream().map(x -> UsuariosDTO.builder()
                    .id(x.getId())
                    .nombre(x.getNombre())
                    .email(x.getEmail())
                    .registrationDate(x.getRegistrationDate())
                    .build()).toList();
        }
        catch(Exception e){
            log.error("Error al obtener los usuarios: ", e);
            throw e;
        }
    }

    /**
     * Este metodo actualiza los datos con el objeto {@link UsuariosRequest} en la base de datos.
     * Primero verifica que el usuario existe en BD. a continuacion se procede a actualizar en BD.
     * Devuelve mismo objeto request que indica que se actualizo correntamente en BD.
     *
     * @param usuario El objeto {@link UsuariosRequest} que sera actualizado. Contiene la informacion necesaria para actualizar un usuario.
     *
     * @return El mismo objeto {@link UsuariosRequest} que es pasado como parametro. Con esto indicamos que se actualizo con exito en BD.
     * @author Daniel Ivan Martinez R.
     */
    public  UsuariosRequest update(UsuariosRequest usuario) throws SQLDataException {
        try{
            Optional<UsuariosEntity> optional = usuariosRepository.findById(usuario.getId());
            if(optional.isPresent()) {
                UsuariosEntity entity = UsuariosEntity.builder()
                        .id(usuario.getId())
                        .nombre(usuario.getNombre())
                        .email(usuario.getEmail())
                        .registrationDate(optional.get().getRegistrationDate())
                        .build();
                usuariosRepository.save(entity);
                return usuario;
            }
            else {
                throw new SQLDataException("Usuario no encontrado en la BD.");
            }
        }catch (DataIntegrityViolationException e){
            log.error("Error: correo duplicado en BD: ", e);
            throw e;
        } catch (Exception e){
            log.error("Error al guardar en la base de datos", e);
            throw e;
        }
    }

    /**
     * Eliminar un usuairo existente en la base de datos
     * @param id valor del id del usuario que se pretende eliminar en BD.
     *
     * @return Devuelve 1 si se elimino correctamente en BD, 0 si no lo encontro en la BD.
     * @author Daniel Ivan Martinez R.
     */
    public Integer deleteById(Integer id) {
        try {
            Optional<UsuariosEntity> entity = usuariosRepository.findById(id);
            if(entity.isPresent()) {
                usuariosRepository.deleteById(entity.get().getId());
                return 1;
            }
            return 0;
        }catch (Exception e){
            log.error("Error al eliminar el usuario por Id: ", e);
            throw e;
        }
    }

    /**
     * Este metodo devuelve una lista de pedidos que pertenecen a un usuarioId consultado
     * el miroservicio "Usuarios" utilizando el usuarioId.
     *
     * @param usuarioId EL identificador unico del usuario que se filtrara de la lista de pedidos.
     *
     * @return A {@link UsuariosDTO} objeto que contiene la informacion del los pedidos que pertenen a un usuario.
     * @author Daniel Ivan Martinez R.
     */
    public List<PedidosDTO> getAllByUsuarioId(Integer usuarioId) {
        String url = pedidosApiUrl + "/all";
        ResponseGenerico<?> response = new RestTemplate().getForEntity(url, ResponseGenerico.class).getBody();
        assert response != null;
        List<PedidosDTO> listaPedidos = objectMapper.convertValue(response.getData(), new TypeReference<>() {});

        listaPedidos = listaPedidos.stream().filter(x-> Objects.equals(x.getUsuarioId(), usuarioId)).toList();
        log.info("listaPedidos: " + listaPedidos);
        return listaPedidos;
    }

    /**
     * Este metodo contiene un conjunto de datos de tipo {@link UsuariosRequest} que se guardara
     * en un objeto de tipo {@link UsuariosEntity}.
     *
     * @param usuario El objeto {@link UsuariosRequest} contiene los datos con los que se actualizara el registro en la entidad {@link UsuariosEntity}.
     * @return Devuelve un objeto nuevo {@link UsuariosEntity} con los datos actualizados de {@link UsuariosRequest}.
     * @author Daniel Ivan Martinez R.
     */
    public UsuariosEntity setData(UsuariosRequest usuario) {
         return UsuariosEntity.builder()
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .registrationDate(new Date())
                .build();
    }
}
