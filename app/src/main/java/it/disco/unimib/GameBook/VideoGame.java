package it.disco.unimib.GameBook;

import android.media.Image;

import java.util.Date;

public class VideoGame {
    protected int id_videogame;
    protected String nome_videogame;
    protected Image copertina_videogame;
    protected Date data_uscita_videogame;
    protected double prezzo_videogame;



    public int getId_videogame() {
        return id_videogame;
    }

    public void setId_videogame(int id_videogame) {
        this.id_videogame = id_videogame;
    }

    public String getNome_videogame() {
        return nome_videogame;
    }

    public void setNome_videogame(String nome_videogame) {
        this.nome_videogame = nome_videogame;
    }

    public Image getCopertina_videogame() {
        return copertina_videogame;
    }

    public void setCopertina_videogame(Image copertina_videogame) {
        this.copertina_videogame = copertina_videogame;
    }

    public Date getData_uscita_videogame() {
        return data_uscita_videogame;
    }

    public void setData_uscita_videogame(Date data_uscita_videogame) {
        this.data_uscita_videogame = data_uscita_videogame;
    }

    public double getPrezzo_videogame() {
        return prezzo_videogame;
    }

    public void setPrezzo_videogame(double prezzo_videogame) {
        this.prezzo_videogame = prezzo_videogame;
    }
}
