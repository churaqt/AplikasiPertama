package edu.uph.m23si1.aplikasipertama.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Matakuliah  extends RealmObject {
    @PrimaryKey
    private int idMatakuliah;
    private String namaMatakuliah, KO;
    private int SKS;

    public Matakuliah(){}

    public Matakuliah(int idMatakuliah, String KO, String namaMatakuliah, int SKS) {
        this.idMatakuliah = idMatakuliah;
        this.KO = KO;
        this.namaMatakuliah = namaMatakuliah;
        this.SKS = SKS;
    }

    public int getIdMatakuliah() {
        return idMatakuliah;
    }

    public void setIdMatakuliah(int idMatakuliah) {
        this.idMatakuliah = idMatakuliah;
    }

    public String getKO() {
        return KO;
    }

    public void setKO(String KO) {
        this.KO = KO;
    }

    public String getNamaMatakuliah() {
        return namaMatakuliah;
    }

    public void setNamaMatakuliah(String namaMatakuliah) {
        this.namaMatakuliah = namaMatakuliah;
    }

    public int getSKS() {
        return SKS;
    }

    public void setSKS(int SKS) {
        this.SKS = SKS;
    }
}
