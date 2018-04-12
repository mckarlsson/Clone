package com.example.micke.clone.Utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable{
    private Images  images;
    private User user;

    protected Data(Parcel in) {
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public Images getImages() {
        return images;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public class User {

        private String profile_picture;

        private String full_name;

        public String getProfile_picture() {
            return profile_picture;
        }

        public String getFull_name() {
            return full_name;
        }
    }

    public class Images {

        private Standard_resolution standard_resolution;

        public Standard_resolution getStandard_resolution() {
            return standard_resolution;
        }

        public class Standard_resolution {

            private String url;

            public String getUrl() {
                return url;
            }
        }
    }
}
