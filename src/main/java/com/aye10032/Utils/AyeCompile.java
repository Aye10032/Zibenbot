package com.aye10032.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AyeCompile {

    private String msg = "";
    private boolean hasCode = false;

    private static Pattern av_pattern = Pattern.compile("([aA])([vV])\\d+");
    private Matcher av_matcher;

    private static Pattern bv_pattern = Pattern.compile("([bB])([vV])[(0-9)|(A-Z)|(a-z)]{10}");
    private Matcher bv_matcher;

    public AyeCompile(String msg) {
        this.msg = msg;
        av_matcher = av_pattern.matcher(this.msg);
        bv_matcher = bv_pattern.matcher(this.msg);

    }

    public boolean hasAV() {
        this.hasCode = av_matcher.matches();
        return hasCode;
    }

    public boolean hasBV() {
        this.hasCode = bv_matcher.matches();
        return hasCode;
    }

    public String getAVString() {
        String avn = "";
        avn = av_matcher.group();
        return avn;
    }

    public String getBVString() {
        String avn = "";
        avn = bv_matcher.group();
        return avn;
    }

}
