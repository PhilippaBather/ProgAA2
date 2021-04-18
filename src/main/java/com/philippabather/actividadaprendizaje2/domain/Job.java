package com.philippabather.actividadaprendizaje2.domain;

/**
 * Job define el objeto actuación que corresponde a la tabla Actuación en la BBDD.
 * 
 * @author philippa bather
 */
public class Job {
    //declarar variables de instancia
    private int jobId;
    private String job;
    private int duration;
    private Enum season;
    
    //constructor
    public Job(int jobId, String job, int duration, Enum season) {
        this.jobId = jobId;
        this.job = job;
        this.duration = duration;
        this.season = season;
    }
    
    //getters y setters

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Enum getSeason() {
        return season;
    }

    public void setSeason(Enum season) {
        this.season = season;
    }
    
    //otros métodos

    @Override
    public String toString() {
        return "Actuación ID: " + jobId + "\nActuación: " + job + "\nDuración: "
                + duration + "\nTemporada: " + season;
    }
}