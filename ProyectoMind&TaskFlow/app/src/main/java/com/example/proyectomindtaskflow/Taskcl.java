package com.example.proyectomindtaskflow;

public class Taskcl {
    public int id_task,id_usuario;
    public String titulo,descripcion,fecha,grupo;
    public boolean prioridad;

    public Taskcl(int id_task, int id_usuario, String titulo, String descripcion, String fecha, String grupo, boolean prioridad) {
        this.id_task = id_task;
        this.id_usuario = id_usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.grupo = grupo;
        this.prioridad = prioridad;
    }

    public int getId_task() {
        return id_task;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getGrupo() {
        return grupo;
    }

    public boolean isPrioridad() {
        return prioridad;
    }

    public void setId_task(int id_task) {
        this.id_task = id_task;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setPrioridad(boolean prioridad) {
        this.prioridad = prioridad;
    }
}
