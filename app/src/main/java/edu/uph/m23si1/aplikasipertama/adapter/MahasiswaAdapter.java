package edu.uph.m23si1.aplikasipertama.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

import edu.uph.m23si1.aplikasipertama.R;
import edu.uph.m23si1.aplikasipertama.model.Mahasiswa;

public class MahasiswaAdapter extends ArrayAdapter<Mahasiswa> {
    // invoke the suitable constructor of the ArrayAdapter class
    public MahasiswaAdapter(@NonNull Context context, ArrayList<Mahasiswa> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.listviewmahasiswa, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Mahasiswa currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView numbersImage = currentItemView.findViewById(R.id.imageView);
        assert currentNumberPosition != null;

        //numbersImage.setImageResource(currentNumberPosition.getNumbersImageId());

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.txvNama);
        textView1.setText(currentNumberPosition.getNama());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView2 = currentItemView.findViewById(R.id.txvProdi);
        textView2.setText(currentNumberPosition.getProdi());

        // then return the recyclable view
        return currentItemView;
//        adapter.updateData(updatedResults);
    }

//    public void updateData(RealmResults<Mahasiswa> newData) {
//        this.data = newData;
//        notifyDataSetChanged();
//    }


}
