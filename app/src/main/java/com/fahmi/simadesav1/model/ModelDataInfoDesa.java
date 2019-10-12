package com.fahmi.simadesav1.model;

public class ModelDataInfoDesa {
    String id_info, judul, deskripsi, foto, createdat;

    public ModelDataInfoDesa() {
    }

    public ModelDataInfoDesa(String id_info, String judul, String deskripsi, String foto) {

    }

    public String getId_info() {
        return id_info;
    }

    public void setId_info(String id_info) {
        this.id_info = id_info;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }
}