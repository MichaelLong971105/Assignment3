package com.example.assignment3.record;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: LONG, QINGSHENG
 * @ID: 16387388
 */
public class Record{//} implements Parcelable {

    private Integer id;
    private String titleName;
    private String textBody;
    private String createTime;
    private String noticeTime;

//    protected Record(Parcel in) {
//        if (in.readByte() == 0) {
//            id = null;
//        } else {
//            id = in.readInt();
//        }
//        titleName = in.readString();
//        textBody = in.readString();
//        createTime = in.readString();
//        noticeTime = in.readString();
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        if (id == null) {
//            dest.writeByte((byte) 0);
//        } else {
//            dest.writeByte((byte) 1);
//            dest.writeInt(id);
//        }
//        dest.writeString(titleName);
//        dest.writeString(textBody);
//        dest.writeString(createTime);
//        dest.writeString(noticeTime);
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Creator<Record> CREATOR = new Creator<Record>() {
//        @Override
//        public Record createFromParcel(Parcel in) {
//            return new Record(in);
//        }
//
//        @Override
//        public Record[] newArray(int size) {
//            return new Record[size];
//        }
//    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", titleName='" + titleName + '\'' +
                ", textBody='" + textBody + '\'' +
                ", createTime='" + createTime + '\'' +
                ", noticeTime='" + noticeTime + '\'' +
                '}';
    }
}
