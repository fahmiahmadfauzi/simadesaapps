package com.fahmi.simadesav1.model;

public class ModelDataPotensiDesa {
    String id_potensi, jenis_potensi, nama_potensi, deskripsi, img;

    public ModelDataPotensiDesa() {
    }

    public ModelDataPotensiDesa(String id_potensi, String jenis_potensi, String nama_potensi, String deskripsi, String img) {

    }

    public String getId_potensi() {
        return id_potensi;
    }

    public void setId_potensi(String id_potensi) {
        this.id_potensi = id_potensi;
    }

    public String getJenis_potensi() {
        return jenis_potensi;
    }

    public void setJenis_potensi(String jenis_potensi) {
        this.jenis_potensi = jenis_potensi;
    }

    public String getNama_potensi() {
        return nama_potensi;
    }

    public void setNama_potensi(String nama_potensi) {
        this.nama_potensi = nama_potensi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
