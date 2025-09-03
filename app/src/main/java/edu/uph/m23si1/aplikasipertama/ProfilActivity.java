package edu.uph.m23si1.aplikasipertama;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import edu.uph.m23si1.aplikasipertama.model.Mahasiswa;
import io.realm.Realm;
import io.realm.RealmResults;

public class ProfilActivity extends AppCompatActivity {
    Button btnSimpan,btnBersihkan;
    EditText edtNama,edtProdi,edtMobile,edtBisnis;
    TextView txvHasil;
    RadioButton rdbLaki, rdbPerempuan;
    RadioGroup rdgJenisKelamin;
    CheckBox chbMancing, chbMakan;
    Spinner sprProdi;
    Realm realm;
    Mahasiswa mhs;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ///
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initVariable();


        if(getIntent().getStringExtra("mode").toString().equals("edit")) {
            btnBersihkan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idmahasiswa = getIntent().getIntExtra("idMahasiswa",0);
                    realm.executeTransaction(r -> {
                        Mahasiswa mhs = r.where(Mahasiswa.class)
                                .equalTo("idMahasiswa", idmahasiswa)
                                .findFirst();
                        if (mhs != null) {
                            mhs.deleteFromRealm();
                            toListMahasiswa();
                        }
                    });
                }
            });
        }
        else if(getIntent().getStringExtra("mode").toString().equals("create")) {
            btnBersihkan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtNama.setText("");
                    edtProdi.setText("");
                    edtBisnis.setText("");
                    edtMobile.setText("");
                    txvHasil.setText("");
                }
            });
        }
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
    }
    public boolean isValidasiData(){
        if(edtBisnis.getText().toString().equals("")){
            edtBisnis.setError("Nilai bisnis harus di isi");
            return false;
        }else if(edtMobile.getText().toString().equals("")){
            edtMobile.setError("Nilai mobile harus di isi");
            return false;
        }else if(edtNama.getText().toString().equals("")){
            edtNama.setError("Nama harus di isi");
            return false;
        }
        return true;
    }
    public void simpan(){
        if(isValidasiData()) {
            String nama = edtNama.getText().toString();
            String prodi = sprProdi.getSelectedItem().toString();
            String jenisKelamin = "";
            String hobi = "";
            int nilaiBisnis = Integer.parseInt(edtBisnis.getText().toString());
            int nilaiMobile = Integer.parseInt(edtMobile.getText().toString());

            if (rdbLaki.isChecked()) jenisKelamin = rdbLaki.getText().toString();
            else if (rdbPerempuan.isChecked()) jenisKelamin = rdbPerempuan.getText().toString();

            if (chbMancing.isChecked()) hobi += chbMancing.getText().toString() + ";";
            if (chbMakan.isChecked()) hobi += chbMakan.getText().toString() + ";";
            String hobi2 = hobi;
            String jk = jenisKelamin;

            txvHasil.setText(nama
                    + "\nJenis Kelamin " + jenisKelamin
                    + "\nIPK " + getIPK(nilaiBisnis, nilaiMobile).toString()
                    + "\n" + prodi
                    + "\n" + getNamaFakultas(prodi) +
                    "\n" + "Universitas Pelita Harapan"
                    + "\nHobi " + hobi);

            Realm realm = Realm.getDefaultInstance();
            if(getIntent().getStringExtra("mode").toString().equals("create")) {
                realm.executeTransaction(r -> {
                    Number maxId = r.where(Mahasiswa.class).max("idMahasiswa");
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                    Mahasiswa mhs2 = r.createObject(Mahasiswa.class, nextId);
                    mhs2.setNama(nama);
                    mhs2.setProdi(prodi);
                    mhs2.setHobi(hobi2);
                    mhs2.setJenisKelamin(jk);
                    mhs2.setNilaiBisnis(nilaiBisnis);
                    mhs2.setNilaiMobile(nilaiMobile);
                });
                Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show();
                toListMahasiswa();
            }else if(getIntent().getStringExtra("mode").toString().equals("edit")){
                int idmahasiswa = getIntent().getIntExtra("idMahasiswa",0);
                realm.executeTransaction(r -> {
                    Mahasiswa mhs = r.where(Mahasiswa.class)
                            .equalTo("idMahasiswa", idmahasiswa)
                            .findFirst();
                    if (mhs != null) {
                        mhs.setNama(nama);
                        mhs.setProdi(prodi);
                        mhs.setHobi(hobi2);
                        mhs.setJenisKelamin(jk);
                        mhs.setNilaiBisnis(nilaiBisnis);
                        mhs.setNilaiMobile(nilaiMobile);
                    }
                });
                Toast.makeText(this, "Data berhasil diedit", Toast.LENGTH_SHORT).show();

               toListMahasiswa();
            }
        }
    }
    public void toListMahasiswa(){
        Intent intent = new Intent(this, ListMahasiswaActivity.class);
        startActivity(intent);
    }

    public void initVariable(){
        //inisialisasi variabel
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBersihkan = findViewById(R.id.btnBersihkan);
        edtNama = findViewById(R.id.edtNama);
        edtProdi = findViewById(R.id.edtProdi);
        txvHasil = findViewById(R.id.txvHasil);
        edtMobile = findViewById(R.id.edtMobile);
        edtBisnis = findViewById(R.id.edtBisnis);
        rdgJenisKelamin = findViewById(R.id.rdgJenisKelamin);
        rdbLaki = findViewById(R.id.rdbLaki);
        rdbPerempuan = findViewById(R.id.rdbPerempuan);
        chbMakan = findViewById(R.id.chbMakan);
        chbMancing = findViewById(R.id.chbMancing);
        sprProdi = (Spinner) findViewById(R.id.sprProdi);

        adapter = ArrayAdapter.createFromResource(
                this,
                R.array.prodi_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprProdi.setAdapter(adapter);

        realm = Realm.getDefaultInstance();

        if(getIntent().getStringExtra("mode").toString().equals("edit")){
            btnBersihkan.setText("Hapus Data");
            btnSimpan.setText("Ubah Data");
            int idmahasiswa = getIntent().getIntExtra("idMahasiswa",0);
            mhs = realm.where(Mahasiswa.class).equalTo("idMahasiswa",idmahasiswa).findFirst();
            if(mhs != null){
                edtNama.setText(mhs.getNama());
                edtProdi.setText(mhs.getProdi());
                edtBisnis.setText(Integer.toString(mhs.getNilaiBisnis()));
                edtMobile.setText(Integer.toString(mhs.getNilaiMobile()));
                if(mhs.getJenisKelamin().equals("Perempuan")) rdbPerempuan.setChecked(true);
                else rdbLaki.setChecked(true);

                String[] hobi = mhs.getHobi().split(";");
                for (String h : hobi) {
                    if(h.equals("Mancing")) chbMancing.setChecked(true);
                    else if (h.equals("Makan")) chbMakan.setChecked(true);
                }

                int posisi = adapter.getPosition(mhs.getProdi());
                sprProdi.setSelection(posisi);
            }
        }
        else if(getIntent().getStringExtra("mode").toString().equals("create")){
            btnSimpan.setText("Simpan Data");
        }
    }
    String getNamaFakultas(String prodi){
        String namaProdi = prodi.toLowerCase();
        if(namaProdi.equals("sistem informasi") || namaProdi.equals("informatika")){
            return "Fakultas Teknologi Informasi";
        }
        else if(namaProdi.equals("hukum") || namaProdi.equals("law")){
            return "Fakultas Hukum";
        }
        else if(namaProdi.equals("akuntansi") || namaProdi.equals("manajemen")||
                namaProdi.equals("perhotelan")){
            return "Fakultas Ekonomi dan Bisnis";
        }
        else
            return "Fakultas Tidak di Temukan";
    }
    Double getIPK(int nilaiBisnis, int NilaiMobile){
        return ((getBobotNilai(nilaiBisnis)*3)+
                (getBobotNilai(NilaiMobile)*3))/6;
    }
    Double getBobotNilai(int nilai){
       if(nilai>=90) return 4.0;
       else if(nilai>=85) return 3.75;
       else if(nilai>=80) return 3.5;
       else if(nilai>=75) return 3.25;
       else if(nilai>=70) return 3.0;
       else if(nilai>60)  return 2.75;
       else if(nilai>50)  return 2.5;
       else return 0.0;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profil, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if(item.getItemId()== R.id.mSimpan){
           simpan();
           return true;
        }else{
            return super.onOptionsItemSelected(item);
        }

    }

}