package com.ms.msuser.Spring_Security_Jwt;

public class authenticationrequest {
    private String userCin;
    private String password;

    public authenticationrequest() {
    }

    public authenticationrequest(String usercin, String password) {
        this.userCin = usercin;
        this.password = password;
    }

    public String getUserCin() {
        return userCin;
    }

    public void setUserCin(String usercin) {
        this.userCin = usercin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
