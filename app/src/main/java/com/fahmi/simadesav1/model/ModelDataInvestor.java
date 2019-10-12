package com.fahmi.simadesav1.model;

public class ModelDataInvestor {

    String id_investor, nama_investor, keterangan, kontak;

    public ModelDataInvestor() {
    }

    public ModelDataInvestor(String id_investor, String nama_investor, String keterangan, String kontak) {

    }


    public String getId_investor() {
        return id_investor;
    }

    public void setId_investor(String id_investor) {
        this.id_investor = id_investor;
    }

    public String getNama_investor() {
        return nama_investor;
    }

    public void setNama_investor(String nama_investor) {
        this.nama_investor = nama_investor;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }
}
