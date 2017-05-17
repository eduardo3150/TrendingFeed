package com.chavez.eduardo.trendingfeed;

import java.io.Serializable;

/**
 * Created by Eduardo_Chavez on 4/4/2017.
 */

public class Routine implements Serializable {
    private int id;
    private String nombre;
    private int tiempo;
    private String descripcion;
    private String tipoEjercicio;


    public Routine(int id,String nombre, String descripcion, String tipoEjercicio,int tiempo) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.descripcion = descripcion;
        this.tipoEjercicio = tipoEjercicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoEjercicio() {
        return tipoEjercicio;
    }

    public void setTipoEjercicio(String tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }
}
