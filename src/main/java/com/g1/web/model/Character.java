package com.g1.web.model;

public class Character {

    //Attributes
	private long id;
    private String name;
	private String description;
	private String thumbnail; //link de la foto
	private long ranking;
	private long likes; //cantidad de likes (= gente que lo marcó como favorito)

    public Character(){
        id=0;
    }

	public Character(long id, String name,String description, String thumbnailPath, String thumbnailExt) {
		this.name = name;
		this.id = id;
		this.description = description;
		this.thumbnail= thumbnailPath;
		this.thumbnail = thumbnail.concat(".");
		this.thumbnail = thumbnail.concat(thumbnailExt);
	}

	public Long getId() {
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

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description;}

	public String getThumbnail() { return thumbnail; }
	public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

	public long getRanking() {
		return ranking;
	}
	public void setRanking(long ranking) {
		this.ranking = ranking;
	}

	public long getLikes() { return likes; }
	public void setLikes(long likes) { this.likes = likes; }

	@Override
	public boolean equals(Object obj) {
		return !(obj == null || obj.getClass() != getClass()) && ((Character) obj).id == id;
	}
}
