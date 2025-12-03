/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mi_perfil_dev.model;
import java.util.UUID;

/**
 * Clase que representa una habilidad de programación
 */
public class Habilidad {
    private String id;
    private String nombre;
    private String nivel; // Básico, Intermedio, Avanzado, Experto
    private int experienciaAnios;
    private String descripcion;

    // Constructor vacío
    public Habilidad() {
        this.id = UUID.randomUUID().toString();
    }

    // Constructor con parámetros
    public Habilidad(String nombre, String nivel, int experienciaAnios, String descripcion) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.nivel = nivel;
        this.experienciaAnios = experienciaAnios;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getExperienciaAnios() {
        return experienciaAnios;
    }

    public void setExperienciaAnios(int experienciaAnios) {
        this.experienciaAnios = experienciaAnios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Habilidad{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nivel='" + nivel + '\'' +
                ", experienciaAnios=" + experienciaAnios +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}