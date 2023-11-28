package com.example.termproject2023;

import java.util.Objects;

public class Announcement {
    String text;

    public Announcement(){}
    public Announcement(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Announcement)) return false;
        Announcement that = (Announcement) o;
        return getText().equals(that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText());
    }
}
