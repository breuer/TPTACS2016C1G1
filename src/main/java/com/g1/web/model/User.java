package com.g1.web.model;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class User {

    private long id;

    private String username;

    private String password;

    private DateTime ultimoAcceso;

    private List<Character> favoritos = new ArrayList<>();

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

    public Long getId() {
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
        // este override no soporta herencia
        return !(obj == null || obj.getClass() != getClass()) && ((User) obj).id == id;

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

    public List<Character> getFavoritos() {
        return favoritos;
    }

    public void addFavorito(Character character) {
        this.favoritos.add(character);
    }

    public void removeFavorito(Character character) {
        this.favoritos.remove(character);
    }

    public void setFavoritos(List<Character> favoritos) {
        this.favoritos = favoritos;
    }
}