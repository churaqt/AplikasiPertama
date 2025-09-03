package edu.uph.m23si1.aplikasipertama.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KrsDetail  extends RealmObject {
    @PrimaryKey
    private int idKrsDetail;
    private Matakuliah matakuliah;
    public KrsDetail(){}

    public KrsDetail(int idKrsDetail, Matakuliah matakuliah) {
        this.idKrsDetail = idKrsDetail;
        this.matakuliah = matakuliah;
    }

    public int getIdKrsDetail() {
        return idKrsDetail;
    }

    public void setIdKrsDetail(int idKrsDetail) {
        this.idKrsDetail = idKrsDetail;
    }

    public Matakuliah getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(Matakuliah matakuliah) {
        this.matakuliah = matakuliah;
    }
}
