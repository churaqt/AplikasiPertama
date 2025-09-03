package edu.uph.m23si1.aplikasipertama.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import edu.uph.m23si1.aplikasipertama.ProfilActivity;
import edu.uph.m23si1.aplikasipertama.R;
import edu.uph.m23si1.aplikasipertama.model.Mahasiswa;
import io.realm.Realm;

public class MahasiswaAdapter2 extends ArrayAdapter<Mahasiswa> {
    public MahasiswaAdapter2(@NonNull Context context, ArrayList<Mahasiswa> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.mahasiswa_layout, parent, false);
        }

        Mahasiswa currentNumberPosition = getItem(position);

        assert currentNumberPosition != null;
        ImageView numbersImage = currentItemView.findViewById(R.id.imvMahasiswa);
        if (currentNumberPosition.getJenisKelamin().equals("Perempuan")){
            numbersImage.setImageResource(R.drawable.user3);
        }
        else {
            numbersImage.setImageResource(R.drawable.user2);
        }


        TextView textView1 = currentItemView.findViewById(R.id.txvNama);
        textView1.setText(currentNumberPosition.getNama() + "-" + currentNumberPosition.getIdMahasiswa());

        TextView textView2 = currentItemView.findViewById(R.id.txvProdi);
        textView2.setText(currentNumberPosition.getProdi());

        LinearLayout llyItemMahasiswa =  currentItemView.findViewById(R.id.llyItemMahasiswa);
        llyItemMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfilActivity.class);
                intent.putExtra("idMahasiswa", currentNumberPosition.getIdMahasiswa());
                intent.putExtra("mode","edit");
                getContext().startActivity(intent);
            }
        });

        ImageView imvDelete = currentItemView.findViewById(R.id.imvDelete);
        imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm  realm = Realm.getDefaultInstance();
                realm.executeTransaction(r -> {
                    Mahasiswa mhs = r.where(Mahasiswa.class)
                            .equalTo("idMahasiswa", currentNumberPosition.getIdMahasiswa())
                            .findFirst();
                    if (mhs != null) {
                        mhs.deleteFromRealm();
                    }
                });
            }
        });

        return currentItemView;
    }

}
