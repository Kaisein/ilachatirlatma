package com.example.ilachatirlatmasi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IlacAdapter extends RecyclerView.Adapter<IlacAdapter.IlacViewHolder> {

    private List<Ilac> ilacList;
    private IlacDatabaseHelper databaseHelper; // Veritabanı referansı

    // Constructor'a veritabanı referansı ekledik
    public IlacAdapter(List<Ilac> ilacList, IlacDatabaseHelper databaseHelper) {
        this.ilacList = ilacList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public IlacViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ilac, parent, false);
        return new IlacViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IlacViewHolder holder, int position) {
        Ilac ilac = ilacList.get(position);
        holder.ilacIsmi.setText("" + ilac.getIsim());
        holder.ilacTarihi.setText("Tarih: " + ilac.getTarih());
        holder.ilacSaatleri.setText("Saatler: " + String.join(", ", ilac.getSaatler()));

        // Silme butonu için tıklama işlemi
        holder.buttonDeleteIlac.setOnClickListener(v -> {
            // Veritabanından sil
            databaseHelper.deleteIlac(ilac.getId());

            // Listeden kaldır
            ilacList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, ilacList.size());
        });
    }

    @Override
    public int getItemCount() {
        return ilacList.size();
    }

    public static class IlacViewHolder extends RecyclerView.ViewHolder {
        TextView ilacIsmi, ilacTarihi, ilacSaatleri;
        ImageButton buttonDeleteIlac; // Silme butonu

        public IlacViewHolder(@NonNull View itemView) {
            super(itemView);
            ilacIsmi = itemView.findViewById(R.id.textIlacIsmi);
            ilacTarihi = itemView.findViewById(R.id.textIlacTarihi);
            ilacSaatleri = itemView.findViewById(R.id.textIlacSaatleri);
            buttonDeleteIlac = itemView.findViewById(R.id.buttonDeleteIlac); // Silme butonunu bağla
        }
    }
}
