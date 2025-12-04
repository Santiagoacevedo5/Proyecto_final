package com.mycompany.mi_perfil_dev.controller;

import com.mycompany.mi_perfil_dev.dao.perfilDAO;
import com.mycompany.mi_perfil_dev.dao.habilidadDAO;
import com.mycompany.mi_perfil_dev.model.Habilidad;
import com.mycompany.mi_perfil_dev.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.List;

/**
 * Servlet controlador para gestionar operaciones CRUD de habilidades
 * Maneja peticiones GET (obtener), POST (crear), PUT (actualizar) y DELETE (eliminar)
 *
 * @author default
 */
@WebServlet("/habilidades")
public class habilidadController extends HttpServlet {
    
    private perfilDAO perfilDAO;
    private habilidadDAO habilidadDAO;
    
    /**
     * Inicialización del servlet
     * Crea instancias de los DAOs
     */
    @Override
    public void init() throws ServletException {
        super.init();
        this.perfilDAO = new perfilDAO();
        this.habilidadDAO = new habilidadDAO(this.perfilDAO);
    }
    
    /**
     * Maneja peticiones GET
     * Obtiene todas las habilidades o una específica por ID
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            String id = request.getParameter("id");
            
            if (id != null && !id.isEmpty()) {
                // Obtener habilidad específica por ID
                Habilidad habilidad = habilidadDAO.obtenerHabilidadPorId(id);
                
                if (habilidad != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(JsonUtil.toJson(habilidad));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(JsonUtil.toJson(crearRespuestaError("Habilidad no encontrada")));
                }
            } else {
                // Obtener todas las habilidades
                List<Habilidad> habilidades = habilidadDAO.obtenerTodasLasHabilidades();
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(JsonUtil.toJson(habilidades));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(JsonUtil.toJson(crearRespuestaError("Error al obtener habilidades: " + e.getMessage())));
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
    
    /**
     * Maneja peticiones POST
     * Crea una nueva habilidad
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            // Leer el cuerpo de la petición
            StringBuilder jsonBuffer = new StringBuilder();
            String linea;
            BufferedReader reader = request.getReader();
            
            while ((linea = reader.readLine()) != null) {
                jsonBuffer.append(linea);
            }
            
            String jsonData = jsonBuffer.toString();
            
            if (jsonData.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(JsonUtil.toJson(crearRespuestaError("El cuerpo de la petición no puede estar vacío")));
                out.flush();
                return;
            }
            
            // Deserializar JSON a objeto Habilidad
            Habilidad habilidad = JsonUtil.fromJson(jsonData, Habilidad.class);
            
            if (habilidad == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(JsonUtil.toJson(crearRespuestaError("Datos de habilidad inválidos")));
                out.flush();
                return;
            }
            
            // Agregar la habilidad
            if (habilidadDAO.agregarHabilidad(habilidad)) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                RespuestaCreada respuesta = new RespuestaCreada(true, "Habilidad creada correctamente", habilidad.getId());
                out.print(JsonUtil.toJson(respuesta));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(JsonUtil.toJson(crearRespuestaError("Error al crear la habilidad")));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(JsonUtil.toJson(crearRespuestaError("Error al procesar la petición: " + e.getMessage())));
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
    
    /**
     * Maneja peticiones PUT
     * Actualiza una habilidad existente
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            // Leer el cuerpo de la petición
            StringBuilder jsonBuffer = new StringBuilder();
            String linea;
            BufferedReader reader = request.getReader();
            
            while ((linea = reader.readLine()) != null) {
                jsonBuffer.append(linea);
            }
            
            String jsonData = jsonBuffer.toString();
            
            if (jsonData.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(JsonUtil.toJson(crearRespuestaError("El cuerpo de la petición no puede estar vacío")));
                out.flush();
                return;
            }
            
            // Deserializar JSON a objeto Habilidad
            Habilidad habilidad = JsonUtil.fromJson(jsonData, Habilidad.class);
            
            if (habilidad == null || habilidad.getId() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(JsonUtil.toJson(crearRespuestaError("Datos de habilidad inválidos o ID faltante")));
                out.flush();
                return;
            }
            
            // Actualizar la habilidad
            if (habilidadDAO.actualizarHabilidad(habilidad)) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(JsonUtil.toJson(crearRespuestaExito("Habilidad actualizada correctamente")));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(JsonUtil.toJson(crearRespuestaError("Error al actualizar la habilidad")));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(JsonUtil.toJson(crearRespuestaError("Error al procesar la petición: " + e.getMessage())));
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
    
    /**
     * Maneja peticiones DELETE
     * Elimina una habilidad por su ID
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            String id = request.getParameter("id");
            
            if (id == null || id.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(JsonUtil.toJson(crearRespuestaError("ID de habilidad requerido")));
                out.flush();
                return;
            }
            
            // Eliminar la habilidad
            if (habilidadDAO.eliminarHabilidad(id)) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(JsonUtil.toJson(crearRespuestaExito("Habilidad eliminada correctamente")));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(JsonUtil.toJson(crearRespuestaError("Error al eliminar la habilidad")));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(JsonUtil.toJson(crearRespuestaError("Error al procesar la petición: " + e.getMessage())));
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
    
    /**
     * Método auxiliar para crear respuestas de error
     */
    private RespuestaAPI crearRespuestaError(String mensaje) {
        return new RespuestaAPI(false, mensaje);
    }
    
    /**
     * Método auxiliar para crear respuestas de éxito
     */
    private RespuestaAPI crearRespuestaExito(String mensaje) {
        return new RespuestaAPI(true, mensaje);
    }
    
    /**
     * Clase interna para estructurar respuestas API
     */
    public static class RespuestaAPI {
        public boolean success;
        public String message;
        
        public RespuestaAPI(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
    
    /**
     * Clase interna para respuestas de creación con ID
     */
    public static class RespuestaCreada extends RespuestaAPI {
        public String id;
        
        public RespuestaCreada(boolean success, String message, String id) {
            super(success, message);
            this.id = id;
        }
    }
}