package edu.uph.m23si1.aplikasipertama;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import edu.uph.m23si1.aplikasipertama.api.ApiResponse;
import edu.uph.m23si1.aplikasipertama.api.ApiService;
import edu.uph.m23si1.aplikasipertama.model.Kota;
import edu.uph.m23si1.aplikasipertama.model.Krs;
import edu.uph.m23si1.aplikasipertama.model.KrsDetail;
import edu.uph.m23si1.aplikasipertama.model.Mahasiswa;
import edu.uph.m23si1.aplikasipertama.model.Matakuliah;
import edu.uph.m23si1.aplikasipertama.model.Provinsi;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegister;
    EditText edtName;
    Spinner sprProvinsi, sprKota;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    ArrayAdapter<String> adapterProvinsi, adapterKota;
    List<Provinsi> provinsiList;
    List<Kota> kotaList;
    List<String> namaProvinsi = new ArrayList<>();
    List<String> namaKota = new ArrayList<>();
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Realm setup
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("mahasiswa2.realm")
                .schemaVersion(1)
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        // init spinner
        sprProvinsi = findViewById(R.id.sprProvinsi);
        sprKota = findViewById(R.id.sprKota);

        // Adapter Provinsi
        adapterProvinsi = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, namaProvinsi);
        adapterProvinsi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprProvinsi.setAdapter(adapterProvinsi);

        // Adapter Kota
        adapterKota = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, namaKota);
        adapterKota.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprKota.setAdapter(adapterKota);

        // Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wilayah.id")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Panggil API Provinsi
        loadProvinsi();
    }

    private void loadProvinsi() {
        apiService.getProvinces().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    provinsiList = response.body().getData();
                    namaProvinsi.clear();
                    for (Provinsi p : provinsiList) {
                        namaProvinsi.add(p.getName());
                    }
                    adapterProvinsi.notifyDataSetChanged();

                    // listener kalau provinsi dipilih
                    sprProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Provinsi selected = provinsiList.get(position);
                            Toast.makeText(RegisterActivity.this,
                                    "Kode: " + selected.getCode() + " Nama: " + selected.getName(),
                                    Toast.LENGTH_SHORT).show();

                            // load kota dari provinsi terpilih
                            loadKota(selected.getCode());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Gagal: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadKota(String kodeProvinsi) {
        apiService.getRegencies(kodeProvinsi).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Ambil list dari response
                    List<Provinsi> listKota = response.body().getData();

                    namaKota.clear();
                    for (Provinsi k : listKota) {
                        namaKota.add(k.getName());
                    }
                    adapterKota.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,
                        "Gagal load kota: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });



    edtName = findViewById(R.id.edtName);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast toast = Toast.makeText(getApplicationContext(), "Tombol Register diTekan", Toast.LENGTH_LONG);
                //toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
                //toast.show();
                toDashboard();
            }
        });

        //inisialisasi
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }
    public void initData(){
//        Mahasiswa mhs = new Mahasiswa("Makan;",10,"Perempuan","Susan",
//                50,50,"Hukum");
        Realm realm = Realm.getDefaultInstance();
/// Kosongkan data dulu
        realm.executeTransaction(r -> {
            RealmResults<Mahasiswa> mhs = r.where(Mahasiswa.class)
                    .findAll();
            if (mhs != null) {
                for (Mahasiswa mahasiswa: mhs) {
                    mahasiswa.deleteFromRealm();
                }
            }
        });
/// Masukkan data
//        realm.executeTransaction(r -> {
//            Number maxId = r.where(Mahasiswa.class).max("idMahasiswa");
//            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
//            Mahasiswa mhs2 = r.createObject(Mahasiswa.class, nextId);
//            mhs2.setNama("Budi");
//            mhs2.setProdi("Hukum");
//            mhs2.setHobi("Makan;");
//            mhs2.setJenisKelamin("Perempuan");
//            mhs2.setNilaiBisnis(50);
//            mhs2.setNilaiMobile(70);
//        });
//
//        realm.executeTransaction(r -> {
//            Number maxId = r.where(Mahasiswa.class).max("idMahasiswa");
//            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
//            Mahasiswa mhs2 = r.createObject(Mahasiswa.class, nextId);
//            mhs2.setNama("Budi Susan");
//            mhs2.setProdi("Hukum");
//            mhs2.setHobi("Makan;");
//            mhs2.setJenisKelamin("Perempuan");
//            mhs2.setNilaiBisnis(50);
//            mhs2.setNilaiMobile(70);
//        });

        List<Matakuliah> daftarMatakuliah = new ArrayList<>();
        daftarMatakuliah.add(new Matakuliah(1,"KO 2024",
                "Pemrograman Aplikasi Bergerak",3));
        daftarMatakuliah.add(new Matakuliah(2,"KO 2024",
                "Pemrograman IoT",3));
        daftarMatakuliah.add(new Matakuliah(3,"KO 2024",
                "Teknologi Imersive",3));

        realm.executeTransaction(r -> {
            r.insert(daftarMatakuliah);
        });

        List<KrsDetail> daftarKrsDetail = new ArrayList<>();
        daftarKrsDetail.add(new KrsDetail(1,daftarMatakuliah.get(0)));//Pemrograman Aplikasi Bergerak
        daftarKrsDetail.add(new KrsDetail(2,daftarMatakuliah.get(1)));//Pemrograman IoT
        daftarKrsDetail.add(new KrsDetail(3,daftarMatakuliah.get(2)));//Teknologi Imersive

        realm.executeTransaction(r -> {
            r.insert(daftarKrsDetail);
        });
        RealmList<KrsDetail> krsDetails = new RealmList<>();
        krsDetails.addAll(realm.copyToRealm(daftarKrsDetail));
        Krs krs = new Krs(1,krsDetails,"Sistem Informasi","2024/2025");

        List<Mahasiswa> daftarMahasiswa = new ArrayList<>();

        daftarMahasiswa.add(new Mahasiswa("Makan", 1, "Perempuan", "Budi", 50, 70, "Hukum"));
        daftarMahasiswa.add(new Mahasiswa("Makan", 2, "Laki-laki", "Andi", 65, 75, "Sistem Informasi"));
        daftarMahasiswa.add(new Mahasiswa("Makan", 3, "Perempuan", "Siti", 80, 85, "Sistem Informasi"));
        daftarMahasiswa.add(new Mahasiswa("Makan", 4, "Perempuan", "Dina", 75, 90, "Sistem Informasi"));
        daftarMahasiswa.add(new Mahasiswa("Makan", 5, "Laki-laki", "Rudi", 60, 65, "Informatika"));
        daftarMahasiswa.add(new Mahasiswa("Makan", 6, "Laki-laki", "Tono", 70, 72, "Informatika"));
        daftarMahasiswa.add(new Mahasiswa("Makan", 7, "Perempuan", "Lisa", 88, 93, "Sistem Informasi"));
        daftarMahasiswa.add(new Mahasiswa("Makan", 8, "Laki-laki", "Joko", 55, 60, "Sistem Informasi"));
        daftarMahasiswa.add(new Mahasiswa("Makan", 9, "Perempuan", "Maya", 78, 82, "Sistem Informasi"));
        daftarMahasiswa.add(new Mahasiswa("Makan", 10, "Perempuan", "Dewi", 90, 95, "Informatika"));
        daftarMahasiswa.add(new Mahasiswa("Makan", 11, "Perempuan", "Dewa", 90, 95, "Manajemen"));

        // Masukkan ke Realm
        realm.executeTransaction(r -> {
            r.insert(daftarMahasiswa);
        });
    }
    public void toProfil(){
        String nama = edtName.getText().toString();
        Intent intent = new Intent(this, ProfilActivity.class);
        intent.putExtra("nama",nama);
        startActivity(intent);
    }

    public void toDashboard(){
        String nama = edtName.getText().toString();

        editor.putString(getString(R.string.username_key), nama);
        editor.apply();

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("nama",nama);
        startActivity(intent);
    }
}