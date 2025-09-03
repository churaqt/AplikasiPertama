package edu.uph.m23si1.aplikasipertama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import edu.uph.m23si1.aplikasipertama.adapter.MahasiswaAdapter;
import edu.uph.m23si1.aplikasipertama.model.Mahasiswa;
import io.realm.Realm;
import io.realm.RealmResults;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout llyProfil,llyListMahasiswa;
    TextView txvHasil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        llyProfil= findViewById(R.id.llyProfil);
        llyProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProfil();
            }
        });
        llyListMahasiswa = findViewById(R.id.llyListMahasiswa);
        llyListMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toListMahasiswa();
            }
        });

        txvHasil = findViewById(R.id.txvHasil);
//        Realm realm = Realm.getDefaultInstance();
//
//
//        final ArrayList<Mahasiswa> arrayList = new ArrayList<>();
//        RealmResults<Mahasiswa> results = realm.where(Mahasiswa.class).findAll();
//        if (results != null) {
//            arrayList.addAll(realm.copyFromRealm(results));
//        }
//
//        MahasiswaAdapter numbersArrayAdapter = new MahasiswaAdapter(this, arrayList);
//        ListView numbersListView = findViewById(R.id.lsvMahasiswa);
//        numbersListView.setAdapter(numbersArrayAdapter);
    }
    public void toProfil(){
        String nama = getIntent().getStringExtra("nama");
        Intent intent = new Intent(this, ProfilActivity.class);
        intent.putExtra("nama",nama);
        intent.putExtra("mode","create");
        startActivity(intent);
    }

    public void toListMahasiswa(){
        Intent intent = new Intent(this, ListMahasiswaActivity.class);
        startActivity(intent);
    }
}