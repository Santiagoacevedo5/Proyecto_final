/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mi_perfil_dev.dao;

import com.mycompany.mi_perfil_dev.model.Habilidad;
import com.mycompany.mi_perfil_dev.model.Perfil;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar operaciones CRUD sobre Habilidades
 * Trabaja con la persistencia de perfiles que contienen habilidades
 *
 * @author default
 */
public class habilidadDAO {
    
    private perfilDAO perfilDAO;
    
    /**
     * Constructor que recibe la dependencia de perfilDAO
     * 
     * @param perfilDAO Instancia de DAO para gestionar perfiles
     */
    public habilidadDAO(perfilDAO perfilDAO) {
        this.perfilDAO = perfilDAO;
    }
    
    /**
     * Agrega una nueva habilidad al perfil
     * 
     * @param habilidad Habilidad a agregar
     * @return true si se agrega correctamente, false en caso contrario
     */
    public boolean agregarHabilidad(Habilidad habilidad) {
        if (habilidad == null) {
            System.err.println("La habilidad no puede ser nula");
            return false;
        }
        
        Perfil perfil = perfilDAO.obtenerPerfil();
        
        if (perfil == null) {
            System.err.println("No existe un perfil para agregar habilidades");
            return false;
        }
        
        if (perfil.getHabilidades() == null) {
            perfil.setHabilidades(new ArrayList<>());
        }
        
        perfil.getHabilidades().add(habilidad);
        return perfilDAO.guardarPerfil(perfil);
    }
    
    /**
     * Obtiene todas las habilidades del perfil
     * 
     * @return Lista de habilidades o null si no existe el perfil
     */
    public List<Habilidad> obtenerTodasLasHabilidades() {
        Perfil perfil = perfilDAO.obtenerPerfil();
        
        if (perfil != null && perfil.getHabilidades() != null) {
            return perfil.getHabilidades();
        }
        
        return new ArrayList<>();
    }
    
    /**
     * Obtiene una habilidad específica por su ID
     * 
     * @param id ID de la habilidad a buscar
     * @return La habilidad si existe, null en caso contrario
     */
    public Habilidad obtenerHabilidadPorId(String id) {
        if (id == null || id.isEmpty()) {
            System.err.println("El ID de la habilidad no puede ser nulo o vacío");
            return null;
        }
        
        List<Habilidad> habilidades = obtenerTodasLasHabilidades();
        
        return habilidades.stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Actualiza una habilidad existente
     * 
     * @param habilidad Habilidad con los datos actualizados
     * @return true si se actualiza correctamente, false en caso contrario
     */
    public boolean actualizarHabilidad(Habilidad habilidad) {
        if (habilidad == null || habilidad.getId() == null) {
            System.err.println("La habilidad o su ID no pueden ser nulos");
            return false;
        }
        
        Perfil perfil = perfilDAO.obtenerPerfil();
        
        if (perfil == null || perfil.getHabilidades() == null) {
            System.err.println("No existe un perfil para actualizar habilidades");
            return false;
        }
        
        // Buscar y eliminar la habilidad antigua
        boolean encontrada = perfil.getHabilidades().removeIf(h -> h.getId().equals(habilidad.getId()));
        
        if (encontrada) {
            perfil.getHabilidades().add(habilidad);
            return perfilDAO.guardarPerfil(perfil);
        }
        
        System.err.println("No se encontró la habilidad con ID: " + habilidad.getId());
        return false;
    }
    
    /**
     * Elimina una habilidad por su ID
     * 
     * @param id ID de la habilidad a eliminar
     * @return true si se elimina correctamente, false en caso contrario
     */
    public boolean eliminarHabilidad(String id) {
        if (id == null || id.isEmpty()) {
            System.err.println("El ID de la habilidad no puede ser nulo o vacío");
            return false;
        }
        
        Perfil perfil = perfilDAO.obtenerPerfil();
        
        if (perfil == null || perfil.getHabilidades() == null) {
            System.err.println("No existe un perfil para eliminar habilidades");
            return false;
        }
        
        boolean eliminada = perfil.getHabilidades().removeIf(h -> h.getId().equals(id));
        
        if (eliminada) {
            return perfilDAO.guardarPerfil(perfil);
        }
        
        System.err.println("No se encontró la habilidad con ID: " + id);
        return false;
    }
    
    /**
     * Obtiene la cantidad de habilidades del perfil
     * 
     * @return Cantidad de habilidades
     */
    public int obtenerCantidadHabilidades() {
        List<Habilidad> habilidades = obtenerTodasLasHabilidades();
        return habilidades.size();
    }
    
    /**
     * Verifica si existe una habilidad con el ID especificado
     * 
     * @param id ID de la habilidad
     * @return true si existe, false en caso contrario
     */
    public boolean existeHabilidad(String id) {
        return obtenerHabilidadPorId(id) != null;
    }
}
