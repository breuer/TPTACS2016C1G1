package com.g1.web.model;

import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.Set;

public class User {

    private long id;

    private String username;

    private String password;

    private DateTime ultimoAcceso;

    private Set<Group> grupos= new HashSet<Group>();

    public User() {
        id = 0;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) // este override no soporta herencia
            return false;

        return id == ((User)obj).id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(DateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public void addGroup(Group grupo) { this.grupos.add(grupo); }

    public Set<Group> getGroups() { return this.grupos; }

}