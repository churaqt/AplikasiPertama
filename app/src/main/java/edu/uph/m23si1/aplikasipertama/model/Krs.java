package edu.uph.m23si1.aplikasipertama.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Krs  extends RealmObject {
    @PrimaryKey
    private int idKrs;
    private String term, prodi;
    private RealmList<KrsDetail> krsDetails;

    public Krs(){}

    public Krs(int idKrs, RealmList<KrsDetail> krsDetails, String prodi, String term) {
        this.idKrs = idKrs;
        this.krsDetails = krsDetails;
        this.prodi = prodi;
        this.term = term;
    }

    public int getIdKrs() {
        return idKrs;
    }

    public void setIdKrs(int idKrs) {
        this.idKrs = idKrs;
    }

    public RealmList<KrsDetail> getKrsDetails() {
        return krsDetails;
    }

    public void setKrsDetails(RealmList<KrsDetail> krsDetails) {
        this.krsDetails = krsDetails;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
