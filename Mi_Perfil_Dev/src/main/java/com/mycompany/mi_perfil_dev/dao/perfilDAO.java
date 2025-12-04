/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mi_perfil_dev.dao;

import com.mycompany.mi_perfil_dev.model.Perfil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase DAO para gestionar la persistencia de objetos Perfil en archivos JSON
 *
 * @author default
 */
public class perfilDAO {
    
    private static final String JSON_DIRECTORY = "data";
    private static final String PERFIL_FILE = "data/perfil.json";
    private final Gson gson;
    
    /**
     * Constructor que inicializa el directorio de datos y el parser GSON
     */
    public perfilDAO() {
        createDataDirectory();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    /**
     * Crea el directorio de datos si no existe
     */
    private void createDataDirectory() {
        File dir = new File(JSON_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Guarda un perfil en formato JSON
     * 
     * @param perfil El perfil a guardar
     * @return true si se guarda correctamente, false en caso contrario
     */
    public boolean guardarPerfil(Perfil perfil) {
        if (perfil == null) {
            System.err.println("El perfil no puede ser nulo");
            return false;
        }
        
        try (FileWriter fileWriter = new FileWriter(PERFIL_FILE)) {
            String jsonString = gson.toJson(perfil);
            fileWriter.write(jsonString);
            fileWriter.flush();
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el perfil: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene el perfil del archivo JSON
     * 
     * @return El perfil guardado o null si no existe
     */
    public Perfil obtenerPerfil() {
        try {
            File file = new File(PERFIL_FILE);
            if (!file.exists()) {
                System.out.println("El archivo de perfil no existe");
                return null;
            }
            
            try (FileReader fileReader = new FileReader(PERFIL_FILE)) {
                return gson.fromJson(fileReader, Perfil.class);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el perfil: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error al parsear el JSON del perfil: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Actualiza un perfil existente
     * 
     * @param perfil El perfil con los datos actualizados
     * @return true si se actualiza correctamente, false en caso contrario
     */
    public boolean actualizarPerfil(Perfil perfil) {
        return guardarPerfil(perfil);
    }
    
    /**
     * Elimina el archivo de perfil
     * 
     * @return true si se elimina correctamente, false en caso contrario
     */
    public boolean eliminarPerfil() {
        try {
            File file = new File(PERFIL_FILE);
            if (file.exists()) {
                return file.delete();
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar el perfil: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verifica si existe un perfil guardado
     * 
     * @return true si el archivo de perfil existe, false en caso contrario
     */
    public boolean existePerfil() {
        return new File(PERFIL_FILE).exists();
    }
}

