/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mi_perfil_dev.model;

import java.util.List;

/**
 * Clase Perfil
 * Representa el perfil profesional del usuario con informacion personal y habilidades
 */
public class Perfil {
private String nombre;
private String bio;
private String experiencia;
private String contacto;
private String fotoPerfil;
private List<Habilidad> habilidades;

    /**
     * Obtiene el nombre del usuario
     * @return nombre del perfil
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la biografia del usuario
     * @return biografia
     */
    public String getBio() {
        return bio;
    }

    /**
     * Establece la biografia del usuario
     * @param bio nueva biografia
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Obtiene la experiencia laboral del usuario
     * @return experiencia
     */
    public String getExperiencia() {
        return experiencia;
    }

    /**
     * Establece la experiencia laboral del usuario
     * @param experiencia nueva experiencia
     */
    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    /**
     * Obtiene la informacion de contacto del usuario
     * @return contacto
     */
    public String getContacto() {
        return contacto;
    }

    /**
     * Establece la informacion de contacto del usuario
     * @param contacto nuevo contacto
     */
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    /**
     * Obtiene la ruta de la foto de perfil
     * @return ruta de la foto
     */
    public String getFotoPerfil() {
        return fotoPerfil;
    }

    /**
     * Establece la ruta de la foto de perfil
     * @param fotoPerfil nueva ruta
     */
    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    /**
     * Obtiene la lista de habilidades del usuario
     * @return lista de habilidades
     */
    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    /**
     * Establece la lista de habilidades del usuario
     * @param habilidades nueva lista de habilidades
     */
    public void setHabilidades(List<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }
    

}