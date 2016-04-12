package com.g1.web.model;

import java.util.Date;

public class User {
		 
	    private long id;
	     
	    private String name;
	     
	    private String password;
	    
	    private Date ultimoAcceso;
	     
	    public User(){
	        id=0;
	    }
	    
	    public User(String username, String password) {
	    	this.name = username;
	    	this.password = password;
	    }
	    
	    public User(long id, String name){
	        this.id = id;
	        this.name = name;
	    }
	     
	    public long getId() {
	        return id;
	    }
	 
	    public void setId(long id) {
	        this.id = id;
	    }
	 
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
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
	        if (this == obj)
	            return true;
	        if (obj == null)
	            return false;
	        if (getClass() != obj.getClass())
	            return false;
	        User other = (User) obj;
	        if (id != other.id)
	            return false;
	        return true;
	    }

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Date getUltimoAcceso() {
			return ultimoAcceso;
		}

		public void setUltimoAcceso(Date ultimoAcceso) {
			this.ultimoAcceso = ultimoAcceso;
		}
	 
	}
