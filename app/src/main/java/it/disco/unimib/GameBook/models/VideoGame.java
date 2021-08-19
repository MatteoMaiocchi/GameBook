package it.disco.unimib.GameBook.models;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class VideoGame implements Parcelable {
    /*
    private int id;
    private String nome_videogame;
    private String copertina_videogame;
    private Date data_uscita_videogame;
    private double prezzo_videogame;
    private String released;
    private int rating;

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

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

    public String getCopertina_videogame() {
        return copertina_videogame;
    }

    public void setCopertina_videogame(String copertina_videogame) {
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
                ", immagineCopertina='" + copertina_videogame + '\'' +
                ", rating='" + rating + '\'' +
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
        dest.writeString(this.copertina_videogame);
        dest.writeInt(this.rating);

    }

    public void readFromParcel(Parcel source) {

        this.id_videogame = source.readInt();
        this.nome_videogame = source.readString();
        this.released = source.readString();
        this.copertina_videogame = source.readString();
        this.rating= source.readInt();

    }

    protected VideoGame(Parcel in) {

        this.id_videogame = in.readInt();
        this.nome_videogame = in.readString();
        this.released = in.readString();
        this.copertina_videogame = in.readString();
        this.rating = in.readInt();

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

     */
    @SerializedName(value = "id")
    @Expose
    private Integer id;
    @SerializedName(value = "name")
    @Expose
    private String name;
    @SerializedName(value = "background_image")
    @Expose
    private String background_image;
    @SerializedName(value = "rating")
    @Expose
    private float rating;

    @SerializedName(value = "released")
    @Expose
    private String released;
    @SerializedName(value = "tba")
    @Expose
    private boolean tba;

    public VideoGame(Integer id, String name, String background_image, float rating, String released, boolean tba) {
        this.id = id;
        this.name = name;
        this.background_image = background_image;
        this.rating = rating;
        this.released = released;
        this.tba = tba;
    }

    public VideoGame() {}

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean getTba() {
        return tba;
    }

    public void setTba(boolean tba) {
        this.tba = tba;
    }

    @Override
    public String toString() {
        return "VideoGame{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", background_image='" + background_image + '\'' +
                ", rating=" + rating +
                ", released=" + released +
                ", tba=" + tba +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.background_image);
        dest.writeDouble(this.rating);
        dest.writeString(this.released);

    }
    public void readFromParcel(Parcel source){
        this.id = source.readInt();
        this.name = source.readString();
        this.background_image = source.readString();
        this.rating = source.readFloat();
        this.released = source.readString();
    }

    protected VideoGame(Parcel in){

        this.id = in.readInt();
        this.name = in.readString();
        this.background_image = in.readString();
        this.rating = in.readFloat();
        this.released = in.readString();
    }

    public static final Parcelable.Creator<VideoGame> CREATOR = new Parcelable.Creator<VideoGame>(){

        @Override
        public VideoGame createFromParcel(Parcel source) {
            return new VideoGame(source);
        }

        @Override
        public VideoGame[] newArray(int size) {
            return new VideoGame[size];
        }
    };


    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }
}


