package io.eka.ekanotes.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by ajay on 25/01/18.
 */

@Entity
public class BasicNote implements Parcelable {

    public static final Parcelable.Creator<BasicNote> CREATOR = new Parcelable.Creator<BasicNote>() {
        @Override
        public BasicNote createFromParcel(Parcel source) {
            return new BasicNote(source);
        }

        @Override
        public BasicNote[] newArray(int size) {
            return new BasicNote[size];
        }
    };
    @Id
    private long Id;
    private String title;
    private String content;

    public BasicNote() {
        this.Id = 0;
    }

    protected BasicNote(Parcel in) {
        this.Id = in.readLong();
        this.title = in.readString();
        this.content = in.readString();
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.Id);
        dest.writeString(this.title);
        dest.writeString(this.content);
    }
}
