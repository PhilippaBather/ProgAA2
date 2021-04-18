package com.philippabather.actividadaprendizaje2.domain;

/**
 * Manager define la clase que especifica un empleado como un jefe.
 * 
 * @author philippa bather
 */
public class Manager extends Employee {

    //constructor
    public Manager(int employeeId, String name, String surname, String dni, int teamId) {
        super(employeeId, name, surname, dni, teamId);
    }

    //otros métodos
    
    @Override
    public String toString() {
        return super.toString();
    }
    
}
