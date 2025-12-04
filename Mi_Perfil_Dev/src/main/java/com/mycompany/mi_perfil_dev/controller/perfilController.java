/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Servlets/ServletTemplate.java to edit this template
 */
package com.mycompany.mi_perfil_dev.controller;

import com.mycompany.mi_perfil_dev.dao.perfilDAO;
import com.mycompany.mi_perfil_dev.model.Perfil;
import com.mycompany.mi_perfil_dev.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;

/**
 * Servlet controlador para gestionar operaciones sobre el perfil
 * Maneja peticiones GET (obtener), PUT (actualizar) y DELETE (eliminar)
 *
 * @author default
 */
@WebServlet("/perfil")
public class perfilController extends HttpServlet {
    
    private perfilDAO perfilDAO;
    
    /**
     * Inicialización del servlet
     * Crea instancia de perfilDAO
     */
    @Override
    public void init() throws ServletException {
        super.init();
        this.perfilDAO = new perfilDAO();
    }
    
    /**
     * Maneja peticiones GET
     * Retorna el perfil en formato JSON
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            Perfil perfil = perfilDAO.obtenerPerfil();
            
            if (perfil != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(JsonUtil.toJson(perfil));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(JsonUtil.toJson(crearRespuestaError("Perfil no encontrado")));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(JsonUtil.toJson(crearRespuestaError("Error al obtener el perfil: " + e.getMessage())));
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
    
    /**
     * Maneja peticiones PUT
     * Actualiza los datos del perfil
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
            
            // Deserializar JSON a objeto Perfil
            Perfil perfil = JsonUtil.fromJson(jsonData, Perfil.class);
            
            // Actualizar el perfil
            if (perfil != null && perfilDAO.actualizarPerfil(perfil)) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(JsonUtil.toJson(crearRespuestaExito("Perfil actualizado correctamente")));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(JsonUtil.toJson(crearRespuestaError("Error al actualizar el perfil")));
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
     * Elimina el perfil guardado
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            if (perfilDAO.eliminarPerfil()) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(JsonUtil.toJson(crearRespuestaExito("Perfil eliminado correctamente")));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(JsonUtil.toJson(crearRespuestaError("Error al eliminar el perfil")));
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
     * Verifica si existe un perfil
     */
    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        
        if (perfilDAO.existePerfil()) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
}

