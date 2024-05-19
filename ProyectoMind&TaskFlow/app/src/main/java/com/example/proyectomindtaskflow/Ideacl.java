package com.example.proyectomindtaskflow;

public class Ideacl {
    public int id_idea,id_usuario;
    public String titulo,descripcion,fecha,grupo;

    public Ideacl(int id_idea, int id_usuario, String titulo, String descripcion, String fecha, String grupo) {
        this.id_idea = id_idea;
        this.id_usuario = id_usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.grupo = grupo;
    }

    public String tostring(){
        return "Idea: "+titulo;
    }

    public void setId_idea(int id_idea) {
        this.id_idea = id_idea;
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

    public int getId_idea() {
        return id_idea;
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
}
