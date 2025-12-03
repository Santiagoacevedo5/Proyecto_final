/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mi_perfil_dev.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Clase utilitaria para manejo de JSON
 * Proporciona métodos para serialización y deserialización de objetos
 *
 * @author default
 */
public class JsonUtil {
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    /**
     * Convierte un objeto a su representación en JSON
     * 
     * @param obj Objeto a convertir
     * @return String con la representación JSON del objeto
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
    
    /**
     * Convierte una cadena JSON a un objeto de la clase especificada
     * 
     * @param json Cadena JSON a convertir
     * @param classOfT Clase del objeto a crear
     * @return Objeto deserializado de tipo T
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
