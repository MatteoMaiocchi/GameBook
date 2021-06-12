package it.disco.unimib.GameBook.models;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class VideoGame implements Parcelable {
    protected int id_videogame;
    protected String nome_videogame;
    protected Image copertina_videogame;
    protected Date data_uscita_videogame;
    protected double prezzo_videogame;
    protected String released;



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

    @Override
    public String toString() {
        return "VideoGame{" +
                "id=" + id_videogame +
                ", nome='" + nome_videogame + '\'' +
                ", released='" + released + '\'' +

                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_videogame);
        dest.writeString(this.nome_videogame);
        dest.writeString(this.released);

    }

    public void readFromParcel(Parcel source) {

        this.id_videogame = source.readInt();
        this.nome_videogame = source.readString();
        this.released = source.readString();

    }

    protected VideoGame(Parcel in) {

        this.id_videogame = in.readInt();
        this.nome_videogame = in.readString();
        this.released = in.readString();

    }

    public static final Parcelable.Creator<VideoGame> CREATOR = new Parcelable.Creator<VideoGame>() {
        @Override
        public VideoGame createFromParcel(Parcel source) {
            return new VideoGame(source);
        }

        @Override
        public VideoGame[] newArray(int size) {
            return new VideoGame[size];
        }
    };
}


