package com.g1.config;


public class MarvelConfig {

    private String apiversion;
    private String urlprefix;
    private String urlsufix;
    private String publicKey;
    private String privateKey;

    public MarvelConfig() {

    }

    public String getApiversion() { return apiversion; }
    public void setApiversion(String apiversion) { this.apiversion = apiversion; }

    public String getUrlprefix() { return urlprefix;  }
    public void setUrlprefix(String urlprefix) { this.urlprefix = urlprefix; }

    public String getUrlsufix() { return urlsufix; }
    public void setUrlsufix(String urlsufix) { this.urlsufix = urlsufix; }

    public String getPublicKey() { return publicKey; }
    public void setPublicKey(String publicKey) { this.publicKey = publicKey; }

    public String getPrivateKey() { return privateKey; }
    public void setPrivateKey(String privateKey) { this.privateKey = privateKey; }

}
