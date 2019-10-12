package com.fahmi.simadesav1.model;

public class ModelDataUMKM {
    String id_umkm, nik, namausaha, deskripsi, img;

    public ModelDataUMKM() {
    }

    public ModelDataUMKM(String id_umkmn, String nik, String namausaha, String img) {

    }


    public String getId_umkm() {
        return id_umkm;
    }

    public void setId_umkm(String id_umkm) {
        this.id_umkm = id_umkm;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNamausaha() {
        return namausaha;
    }

    public void setNamausaha(String namausaha) {
        this.namausaha = namausaha;
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
