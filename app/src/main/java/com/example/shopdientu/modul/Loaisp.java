package com.example.shopdientu.modul;

public class Loaisp {
    public int id;
    public String tenLoaiSP;
    public String hinhanhSP;

    public Loaisp(int id, String tenLoaiSP, String hinhanhSP) {
        this.id = id;
        this.tenLoaiSP = tenLoaiSP;
        this.hinhanhSP = hinhanhSP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getHinhanhSP() {
        return hinhanhSP;
    }

    public void setHinhanhSP(String hinhanhSP) {
        this.hinhanhSP = hinhanhSP;
    }
}
