package it.disco.unimib.GameBook.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * It represents an answer from given by NewsApi.org Webservice.
 */
public class Response implements Parcelable {

    private int count;

    @SerializedName(value = "results")
    private List<VideoGame> videoGameList;

    public Response(int count, List<VideoGame> videoGameList) {
        this.count = count;
        this.videoGameList = videoGameList;
    }

    protected Response(Parcel in) {
        this.count = in.readInt();
        this.videoGameList = in.createTypedArrayList(VideoGame.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeTypedList(this.videoGameList);
    }
    public void readFromParcel(Parcel source) {
        this.count = source.readInt();
        this.videoGameList = source.createTypedArrayList(VideoGame.CREATOR);
    }

    public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };


    public List<VideoGame> getVideoGameList() {
        return videoGameList;
    }

    public void setVideoGameList(List<VideoGame> videoGameList) {
        this.videoGameList = videoGameList;
    }

    @Override
    public String toString() {
        return "Response{" +

                ", videoGameList=" + videoGameList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


