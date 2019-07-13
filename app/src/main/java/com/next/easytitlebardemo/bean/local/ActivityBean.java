package com.next.easytitlebardemo.bean.local;

import android.os.Parcel;
import android.os.Parcelable;

import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/7/16.
 */

public class ActivityBean implements Parcelable {


    private Unbinder unbinder;

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    public Unbinder getUnbinder() {
        return unbinder;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) this.unbinder, flags);
    }

    public ActivityBean() {
    }

    protected ActivityBean(Parcel in) {
        this.unbinder = in.readParcelable(Unbinder.class.getClassLoader());
    }

    public static final Creator<ActivityBean> CREATOR = new Creator<ActivityBean>() {
        @Override
        public ActivityBean createFromParcel(Parcel source) {
            return new ActivityBean(source);
        }

        @Override
        public ActivityBean[] newArray(int size) {
            return new ActivityBean[size];
        }
    };
}
