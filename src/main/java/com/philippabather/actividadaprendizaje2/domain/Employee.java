package com.philippabather.actividadaprendizaje2.domain;

/**
 * Employee define una clase abstracta para definir las propiedades que pertenece
 * al jardinero y jefe representado en la BBDD en la tabla Jardinero
 * 
 * @author philippa bather
 */
public abstract class Employee {    
    //declarar variables de instancia
    private int employeeId;
    private String name;
    private String surname;
    private String dni;
    private int teamId;
    
    //constructor
    public Employee(int employeeId, String name, String surname, String dni, int teamId) {
        this.employeeId = employeeId;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.teamId = teamId;
    }
    
    //getters y setters

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
   
    //otros métodos

    @Override
    public String toString() {
        return "ID del empleado: " + employeeId + "\nNombre: " + name +
                "\nApellido: " + surname + "\nDNI: " + dni + "\nCuadrilla: " + teamId;
    }
}
