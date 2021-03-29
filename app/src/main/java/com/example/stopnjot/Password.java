package com.example.stopnjot;

public class Password {
    String mPId, mAccount, mPass;

    public Password() {

    }

    public Password(String pId, String account, String pass) {
        this.mPId = pId;
        this.mAccount = account;
        this.mPass = pass;
    }

    public String getPId() {
        return mPId;
    }

    public String getAccount() {
        return mAccount;
    }

    public String getPass() {
        return mPass;
    }

    public void setPId(String pId) { this.mPId = pId; }

    public void setAccount(String account) {
        this.mAccount = account;
    }

    public void setPass(String pass) {
        this.mPass = pass;
    }
}