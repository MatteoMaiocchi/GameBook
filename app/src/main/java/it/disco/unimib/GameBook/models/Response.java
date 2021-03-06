package it.disco.unimib.GameBook.models;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response implements Parcelable {

    @SerializedName(value = "count")
    @Expose
    private Integer count;
    @SerializedName(value = "results")
    @Expose
    private List<VideoGame> videoGameList = null;
    private List<VideoGame> videoGameListTopRating = null;

    public Response() {}

    public Response(Integer count, List<VideoGame> videoGameList, List<VideoGame> videoGameListTopRating) {
        this.count = count;
        this.videoGameList = videoGameList;
        this.videoGameListTopRating = videoGameListTopRating;
    }


    public int getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<VideoGame> getVideoGameList() {
        return videoGameList;
    }

    public void setVideoGameList(List<VideoGame> videoGameList) {
        this.videoGameList = videoGameList;
    }

    public List<VideoGame> getVideoGameListTopRating() {
        return videoGameListTopRating;
    }

    public void setVideoGameListTopRating(List<VideoGame> videoGameList) {
        this.videoGameListTopRating = videoGameList;
    }

    //Parcelable
    protected Response(Parcel in) {
        this.count = in.readInt();
        this.videoGameList = in.createTypedArrayList(VideoGame.CREATOR);
        this.videoGameListTopRating = in.createTypedArrayList(VideoGame.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeTypedList(this.videoGameList);
        dest.writeTypedList(this.videoGameListTopRating);
    }
    public void readFromParcel(Parcel source) {
        this.count = source.readInt();
        this.videoGameList = source.createTypedArrayList(VideoGame.CREATOR);
        this.videoGameListTopRating = source.createTypedArrayList(VideoGame.CREATOR);
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


    @Override
    public String toString() {
        return "Response{" +

                ", videoGameList=" + videoGameList +
                ", videoGameListTopRating=" + videoGameListTopRating +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }




}


