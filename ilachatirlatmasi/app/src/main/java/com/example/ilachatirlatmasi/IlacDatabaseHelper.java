package com.example.ilachatirlatmasi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class IlacDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ilaclar.db";
    private static final int DATABASE_VERSION = 1;

    // Tablo ve sütun adları
    private static final String TABLE_ILACLAR = "ilaclar";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ILAC_ISMI = "isim";
    private static final String COLUMN_TARIH = "tarih";
    private static final String COLUMN_SAATLER = "saatler";

    public IlacDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ILACLAR_TABLE = "CREATE TABLE " + TABLE_ILACLAR + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ILAC_ISMI + " TEXT, "
                + COLUMN_TARIH + " TEXT, "
                + COLUMN_SAATLER + " TEXT)";
        db.execSQL(CREATE_ILACLAR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ILACLAR);
        onCreate(db);
    }

    // İlaç ekleme
    public void addIlac(Ilac ilac) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ILAC_ISMI, ilac.getIsim());
        values.put(COLUMN_TARIH, ilac.getTarih());
        values.put(COLUMN_SAATLER, String.join(",", ilac.getSaatler()));

        db.insert(TABLE_ILACLAR, null, values);
        db.close();
    }

    // Tüm ilaçları alma
    public List<Ilac> getAllIlaclar() {
        List<Ilac> ilaclar = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ILACLAR, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String isim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ILAC_ISMI));
                String tarih = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TARIH));
                String saatlerStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SAATLER));
                List<String> saatler = List.of(saatlerStr.split(","));

                ilaclar.add(new Ilac(id, isim, tarih, saatler));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ilaclar;
    }

    // İlaç silme
    public void deleteIlac(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ILACLAR, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Belirli bir ilaç güncelleme
    public void updateIlac(Ilac ilac) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ILAC_ISMI, ilac.getIsim());
        values.put(COLUMN_TARIH, ilac.getTarih());
        values.put(COLUMN_SAATLER, String.join(",", ilac.getSaatler()));

        db.update(TABLE_ILACLAR, values, COLUMN_ID + " = ?", new String[]{String.valueOf(ilac.getId())});
        db.close();
    }

    // Belirli bir ilaç bilgilerini alma
    public Ilac getIlacById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ILACLAR, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String isim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ILAC_ISMI));
            String tarih = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TARIH));
            String saatlerStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SAATLER));
            List<String> saatler = List.of(saatlerStr.split(","));
            cursor.close();
            db.close();
            return new Ilac(id, isim, tarih, saatler);
        }

        if (cursor != null) cursor.close();
        db.close();
        return null;
    }
}
