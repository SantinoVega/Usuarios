package com.demo.usuarios.controller;

import com.demo.usuarios.dto.PedidosDTO;
import com.demo.usuarios.response.ResponseGenerico;
import com.demo.usuarios.dto.UsuariosDTO;
import com.demo.usuarios.model.UsuariosRequest;
import com.demo.usuarios.service.IUsuariosService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLDataException;
import java.util.List;

/**
 * La clase UsuariosController contiene los
 * metodos para hacer CRUD con datos del usuario.
 *
 * @author Daniel Ivan Martinez R.
 */
@RestController
@RequestMapping("usuarios")
public class UsuariosController {

    @Autowired
    IUsuariosService usuariosService;

    /**
     * Guardar un usuario en la base de datos, El objeto request tiene validaciones.
     * El campo nombre debe ser String no mayor a 100 y no puede ser vacio o nulo.
     * El campo email debe ser String no mayor a 100 y debe tener formato de correo electronico.
     * @param usuario Es un objeto que contiene los atributos que se intentaran guardar en la BD, {@link UsuariosRequest}
     * @return Devuelve el objeto Request si se guardo con exito en la BD.
     * @author Daniel Ivan Martinez R.
     */
    @Operation(summary = "Guardar un usuario en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario guardado correctamente en base de datos.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class)) ),
            @ApiResponse(responseCode = "400", description = "Error email o datos duplicado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class)) ),
            @ApiResponse(responseCode = "500", description = "Error: Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class))) })
    @PostMapping
    public ResponseEntity<ResponseGenerico<UsuariosRequest>> saveUsuario(@Valid @RequestBody UsuariosRequest usuario) {
        UsuariosRequest response = usuariosService.save(usuario);
        return new ResponseGenerico<>("Usuario guardado exitosamente en BD.",Boolean.TRUE,response).success(HttpStatus.CREATED);
    }

    /**
     * Busca un usuario en BD por id
     * @param id valor del id del usuario
     * @return Devuelve el objeto {@link UsuariosDTO} que contiene los datos del usuario.
     * @author Daniel Ivan Martinez R.
     */
    @Operation(summary = "Obtener usuario por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success!!",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class)) ),
            @ApiResponse(responseCode = "500", description = "Error: Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class))) })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        UsuariosDTO response = usuariosService.getById(id);
        if(response == null){
            return new ResponseGenerico<>("Usuario no encontrado",Boolean.TRUE,null).success(HttpStatus.OK);
        }
        return new ResponseGenerico<>("Consulta correcta",Boolean.TRUE,response).success(HttpStatus.OK);
    }

    /**
     * Obtener todos los usuarios almacenados en la base de datos.
     * @return Devuelve una lista de objetos {@link UsuariosDTO} que contiene todos los usuarios guardados en BD.
     * @author Daniel Ivan Martinez R.
     */
    @Operation(summary = "Obtener todos los usuarios almacenados en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success!!",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class)) ),
            @ApiResponse(responseCode = "500", description = "Error: Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class))) })
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<UsuariosDTO> usuariosList = usuariosService.getAll();
        if(usuariosList.isEmpty()){
            return new ResponseGenerico<>("No se encontraron datos",Boolean.TRUE,null).success(HttpStatus.OK);
        }
        return new ResponseGenerico<>("Consulta exitosa",Boolean.TRUE,usuariosList).success(HttpStatus.OK);
    }

    /**
     * Modificar un usuario existente en la base de datos
     * @param usuario Objeto que se pretende actualizar en BD {@link UsuariosRequest}
     * @return Devuelve un objeto {@link UsuariosRequest} que contiene los usuarios actualizados en BD.
     * @author Daniel Ivan Martinez R.
     */
    @Operation(summary = "Modificar un usuario existente en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario modificado correctamente en base de datos.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class)) ),
            @ApiResponse(responseCode = "400", description = "Error email o datos duplicado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class)) ),
            @ApiResponse(responseCode = "500", description = "Error: Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class))) })
    @PutMapping
    public ResponseEntity<ResponseGenerico<UsuariosRequest>> updateUsuario(@Valid @RequestBody UsuariosRequest usuario) throws SQLDataException {
        UsuariosRequest response = usuariosService.update(usuario);
        if(response == null){
            return new ResponseGenerico<>("No se encontro al usuario",Boolean.TRUE,new UsuariosRequest()).success(HttpStatus.OK);
        }
        return new ResponseGenerico<>("Usuario modificado exitosamente",Boolean.TRUE,response).success(HttpStatus.OK);
    }

    /**
     * Eliminar un usuarios existente en la base de datos
     * @param id valor del id del usuario que se pretende actualizar en BD.
     * @return Devuelve 1 si se elimino correctamente en BD, 0 si no lo encontro en la BD.
     * @author Daniel Ivan Martinez R.
     */
    @Operation(summary = "Eliminar un usuario existente en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente en base de datos.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class)) ),
            @ApiResponse(responseCode = "400", description = "Error iemal o datos no encontrados.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class)) ),
            @ApiResponse(responseCode = "500", description = "Error: Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class))) })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseGenerico<Integer>> deleteUsuario(@PathVariable("id") int id) {
        Integer response = usuariosService.deleteById(id);

        if(response == 0){
            return new ResponseGenerico<>("No se encontro al usuario",Boolean.TRUE,0).success(HttpStatus.OK);
        }
        return new ResponseGenerico<>("Usuario Eliminado exitosamente",Boolean.TRUE,response).success(HttpStatus.OK);
    }

    /**
     * Consulta todos los pedidos de un usuario por medio del usuarioId guardados en la base de datos
     * @param usuarioId valor del usuarioId para filtrar los pedidos consultados en el microservicio "pedidos".
     * @return Devuelve una lista con todos los pedidos del usuario guardados en la BD.
     * @author Daniel Ivan Martinez R.
     */
    @Operation(summary = "Obtener todos los pedidos de un usuario consultando al microservicio Pedidos ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success!!",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class)) ),
            @ApiResponse(responseCode = "500", description = "Error: Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseGenerico.class))) })
    @GetMapping("/listaPedidos/{usuarioId}")
    public ResponseEntity<?> allPedidos(@PathVariable("usuarioId") Integer usuarioId) {
        List<PedidosDTO> usuariosList = usuariosService.getAllByUsuarioId(usuarioId);
        if(usuariosList.isEmpty()){
            return new ResponseGenerico<>("No se encontraron datos",Boolean.TRUE,null).success(HttpStatus.OK);
        }
        return new ResponseGenerico<>("Consulta exitosa",Boolean.TRUE,usuariosList).success(HttpStatus.OK);
    }

}
