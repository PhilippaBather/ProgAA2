package com.philippabather.actividadaprendizaje2.domain;

/**
 * Team define el objeto cuadrilla que corresponde a la tabla Cuadrilla en la BBDD.
 * @author bathe
 */
public class Team {
    //declarar variables de instancia
    private int teamId;
    private String teamName;
    
    //constructor
    public Team(int teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }
    
    //getters y setters

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    //otros métodos

    @Override
    public String toString() {
        return "ID de Cuadrilla: " + teamId + "\nNombre de Cuadrilla: " + teamName;
    }
    
    
}
