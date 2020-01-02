package com.aye10032.BanUtil;

public class AyeMember {

    private long QQ;
    private int BanedTime = 0;
    private int BanOtherTime = 0;

    public AyeMember(long qq){
        this.QQ = qq;
    }

    public int getBanedTime() {
        return BanedTime;
    }

    public void addBanedTime(){
        this.BanedTime ++;
    }

    public void setBanedTime(int banedTime) {
        BanedTime = banedTime;
    }

    public int getBanOtherTime() {
        return BanOtherTime;
    }

    public void addBanOtherTime(){
        this.BanOtherTime ++;
    }

    public void setBanOtherTime(int banOtherTime) {
        BanOtherTime = banOtherTime;
    }
}
