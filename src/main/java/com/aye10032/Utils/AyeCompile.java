package com.aye10032.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AyeCompile {

    private String msg = "";
    private boolean hasCode = false;

    private Pattern pattern = null;
    private Matcher matcher = null;

    public AyeCompile(String msg) {
        this.msg = msg;
        pattern = Pattern.compile("(av|AV)\\d+");
        matcher = pattern.matcher(this.msg);
    }

    public boolean hasAV() {
        this.hasCode = matcher.find();
        return hasCode;
    }

    public String getAVNum() {
        String avn = "";
        avn = matcher.group();
        avn = avn.substring(2);

        return avn;
    }

}
