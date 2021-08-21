package it.disco.unimib.GameBook.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTopRating implements Parcelable {

    @SerializedName(value = "count")
    @Expose
    private Integer count;
    @SerializedName(value = "results")
    @Expose
    private List<VideoGame> videoGameListTopRating = null;

    public ResponseTopRating() {}

    public ResponseTopRating(Integer count, List<VideoGame> videoGameListTopRating) {
        this.count = count;
        this.videoGameListTopRating = videoGameListTopRating;
    }


    public int getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<VideoGame> getVideoGameListTopRating() {
        return videoGameListTopRating;
    }

    public void setVideoGameListTopRating(List<VideoGame> videoGameList) {
        this.videoGameListTopRating = videoGameList;
    }

    //Parcelable
    protected ResponseTopRating(Parcel in) {
        this.count = in.readInt();
        this.videoGameListTopRating = in.createTypedArrayList(VideoGame.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeTypedList(this.videoGameListTopRating);
    }
    public void readFromParcel(Parcel source) {
        this.count = source.readInt();
        this.videoGameListTopRating = source.createTypedArrayList(VideoGame.CREATOR);
    }

    public static final Creator<ResponseTopRating> CREATOR = new Creator<ResponseTopRating>() {
        @Override
        public ResponseTopRating createFromParcel(Parcel in) {
            return new ResponseTopRating(in);
        }

        @Override
        public ResponseTopRating[] newArray(int size) {
            return new ResponseTopRating[size];
        }
    };


    @Override
    public String toString() {
        return "Response{" +
                ", videoGameListTopRating=" + videoGameListTopRating +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }




}


