package edu.uph.m23si1.aplikasipertama.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Mahasiswa extends RealmObject {
    @PrimaryKey
    private int idMahasiswa;
    private String nama,jenisKelamin,prodi,hobi;
    private int nilaiBisnis,nilaiMobile;

    private RealmList<Krs> krss;

    public Mahasiswa(){}

    public Mahasiswa(String hobi, int idMahasiswa, String jenisKelamin,
                     String nama, int nilaiBisnis, int nilaiMobile, String prodi) {
        this.hobi = hobi;
        this.idMahasiswa = idMahasiswa;
        this.jenisKelamin = jenisKelamin;
        this.nama = nama;
        this.nilaiBisnis = nilaiBisnis;
        this.nilaiMobile = nilaiMobile;
        this.prodi = prodi;
    }

    public Mahasiswa(String hobi, int idMahasiswa, String jenisKelamin, RealmList<Krs> krss, String nama, int nilaiBisnis, int nilaiMobile, String prodi) {
        this.hobi = hobi;
        this.idMahasiswa = idMahasiswa;
        this.jenisKelamin = jenisKelamin;
        this.krss = krss;
        this.nama = nama;
        this.nilaiBisnis = nilaiBisnis;
        this.nilaiMobile = nilaiMobile;
        this.prodi = prodi;
    }

    public RealmList<Krs> getKrss() {
        return krss;
    }

    public void setKrss(RealmList<Krs> krss) {
        this.krss = krss;
    }

    public String getHobi() {
        return hobi;
    }

    public void setHobi(String hobi) {
        this.hobi = hobi;
    }

    public int getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(int idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getNilaiBisnis() {
        return nilaiBisnis;
    }

    public void setNilaiBisnis(int nilaiBisnis) {
        this.nilaiBisnis = nilaiBisnis;
    }

    public int getNilaiMobile() {
        return nilaiMobile;
    }

    public void setNilaiMobile(int nilaiMobile) {
        this.nilaiMobile = nilaiMobile;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    @Override
    public String toString() {
        return "Mahasiswa{" +
                "hobi='" + hobi + '\'' +
                ", idMahasiswa=" + idMahasiswa +
                ", nama='" + nama + '\'' +
                ", jenisKelamin='" + jenisKelamin + '\'' +
                ", prodi='" + prodi + '\'' +
                ", nilaiBisnis=" + nilaiBisnis +
                ", nilaiMobile=" + nilaiMobile +
                '}';
    }
}
