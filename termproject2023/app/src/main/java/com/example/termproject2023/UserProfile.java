package com.example.termproject2023;

public class UserProfile {
    String bio, interests, username, userID;
    int imageId;

    public UserProfile() {
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "bio='" + bio + '\'' +
                ", interests='" + interests + '\'' +
                ", username='" + username + '\'' +
                ", userID='" + userID + '\'' +
                ", imageId=" + imageId +
                '}';
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
