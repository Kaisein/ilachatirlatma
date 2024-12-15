package com.example.ilachatirlatmasi;

import java.util.List;

public class Ilac {
    private int id; // Veritabanı için benzersiz kimlik
    private String isim;
    private String tarih;
    private List<String> saatler;

    // Constructor (id ile birlikte)
    public Ilac(int id, String isim, String tarih, List<String> saatler) {
        this.id = id;
        this.isim = isim;
        this.tarih = tarih;
        this.saatler = saatler;
    }

    // Constructor (id olmadan)
    public Ilac(String isim, String tarih, List<String> saatler) {
        this.isim = isim;
        this.tarih = tarih;
        this.saatler = saatler;
    }

    // Getter ve Setter metotları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public List<String> getSaatler() {
        return saatler;
    }

    public void setSaatler(List<String> saatler) {
        this.saatler = saatler;
    }
}
