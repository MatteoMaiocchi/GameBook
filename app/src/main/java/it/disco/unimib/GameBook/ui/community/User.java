package it.disco.unimib.GameBook.ui.community;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String username;

    private String id;

    public User(){}

    public User(String username) {
        this.username = username;
    }

    protected User(Parcel in) {
        username = in.readString();
        id = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(id);
    }
}
