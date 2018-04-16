package com.example.hoangtienmanh.imagelistview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hoang Tien Manh on 3/28/2018.
 */

public class Tintuc implements Parcelable{
    public String tieude,noidungtieude,noidung1,noidung2,danhmuc, linkBaiviet,linkAnh;
    public int Id;

    public Tintuc(){};

    public Tintuc(String tieude, String noidungtieude,String noidung1,String noidung2, String danhmuc, String linkBaiviet, String linkAnh){
        this.tieude = tieude;
        this.noidungtieude = noidungtieude;
        this.noidung1 = noidung1;
        this.noidung2 = noidung2;
        this.danhmuc = danhmuc;
        this.linkBaiviet = linkBaiviet;
        this.linkAnh = linkAnh;
    }

    public Tintuc(int Id, String tieude, String noidungtieude,String noidung1,String noidung2, String danhmuc, String linkBaiviet, String linkAnh) {
        this.Id = Id;
        this.tieude = tieude;
        this.noidungtieude = noidungtieude;
        this.noidung1 = noidung1;
        this.noidung2 = noidung2;
        this.danhmuc = danhmuc;
        this.linkBaiviet = linkBaiviet;
        this.linkAnh = linkAnh;
    }

    protected Tintuc(Parcel in) {
        tieude = in.readString();
        noidungtieude = in.readString();
        noidung1 = in.readString();
        noidung2 = in.readString();
        danhmuc = in.readString();
        linkBaiviet = in.readString();
        linkAnh = in.readString();
        Id = in.readInt();
    }

    public static final Creator<Tintuc> CREATOR = new Creator<Tintuc>() {
        @Override
        public Tintuc createFromParcel(Parcel in) {
            return new Tintuc(in);
        }

        @Override
        public Tintuc[] newArray(int size) {
            return new Tintuc[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tieude);
        dest.writeString(noidungtieude);
        dest.writeString(noidung1);
        dest.writeString(noidung2);
        dest.writeString(danhmuc);
        dest.writeString(linkBaiviet);
        dest.writeString(linkAnh);
        dest.writeInt(Id);
    }
}
