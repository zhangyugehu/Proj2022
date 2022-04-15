package com.thssh.proj2022;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hutianhang
 */
public class ParcelableBean implements Parcelable {
    public static class InnerBean implements Parcelable {
        public InnerBean(String name) {
            this.name = name;
        }

        String name;

        protected InnerBean(Parcel in) {
            name = in.readString();
        }

        public static final Creator<InnerBean> CREATOR = new Creator<InnerBean>() {
            @Override
            public InnerBean createFromParcel(Parcel in) {
                return new InnerBean(in);
            }

            @Override
            public InnerBean[] newArray(int size) {
                return new InnerBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
        }
    }

    List<InnerBean> innerBeans;
    int count;

    public ParcelableBean(int count, List<InnerBean> list) {
        this.count = count;
        this.innerBeans = list;
    }

    protected ParcelableBean(Parcel in) {
        count = in.readInt();
        in.readList((innerBeans = new ArrayList<>()), getClass().getClassLoader());
    }

    public static final Creator<ParcelableBean> CREATOR = new Creator<ParcelableBean>() {
        @Override
        public ParcelableBean createFromParcel(Parcel in) {
            return new ParcelableBean(in);
        }

        @Override
        public ParcelableBean[] newArray(int size) {
            return new ParcelableBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeList(innerBeans);
    }
}
