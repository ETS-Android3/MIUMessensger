package com.example.miumessenger.models;

public class Users {
    String profilePic;
    String userName;
    String email;
    String password;
    String userId;
    String lastMsg;
    String lastMsgTime;
    String about;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }



    public Users(){

    }

    public Users(String profilePic, String userName, String email, String password, String userId, String lastMsg, String lastMsgTime, String about) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.lastMsg = lastMsg;
        this.lastMsgTime = lastMsgTime;
        this.about = about;
    }


    //SignUp constructor
    public Users( String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;

    }
    public Users(  String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getLastMsgTime() {
        return lastMsgTime;
    }

    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

}
