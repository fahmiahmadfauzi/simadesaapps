package com.fahmi.simadesav1.model;

public class ModelDataUsulan {
    String id_usulan, nik, usulan, status;

    public ModelDataUsulan() {
    }

    public ModelDataUsulan(String id_usulan, String nik, String usulan, String status) {

    }

    public String getId_usulan() {
        return id_usulan;
    }

    public void setId_usulan(String id_usulan) {
        this.id_usulan = id_usulan;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getUsulan() {
        return usulan;
    }

    public void setUsulan(String usulan) {
        this.usulan = usulan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
