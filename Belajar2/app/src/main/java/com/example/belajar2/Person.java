package com.example.belajar2;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private String name;
    private int age;
    private String email;
    private String city;

    private Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
        email = in.readString();
        city = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    Person() {

    }


    public String getName() {
        return name;
    }

    void setName() {
        this.name = "Syukur Iman Attaqwa";
    }

    int getAge() {
        return age;
    }

    void setAge() {
        this.age = 18;
    }

    String getEmail() {
        return email;
    }

    void setEmail() {
        this.email = "ataiman30@gmail.com";
    }

    String getCity() {
        return city;
    }

    void setCity() {
        this.city = "Sidoarjo";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(email);
        dest.writeString(city);
    }
}
