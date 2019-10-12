package com.fahmi.simadesav1.model;

public class ModelDataLayanan {

    String id_kategori, nama_layanan, keterangan;

    public ModelDataLayanan() {
    }

    public ModelDataLayanan(String id_kategori, String nama_layanan, String keterangan) {

    }


    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_layanan() {
        return nama_layanan;
    }

    public void setNama_layanan(String nama_layanan) {
        this.nama_layanan = nama_layanan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
