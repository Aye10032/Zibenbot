package com.dazo66.test;

import com.aye10032.Utils.ConfigLoader;
import com.aye10032.Utils.Video.VideoClass;
import com.aye10032.Zibenbot;

public class VideoClassTest {

    public static void main(String[] args) {
        VideoClass videoClass = ConfigLoader.load( "videoData.json", VideoClass.class);
        System.out.println(videoClass);
    }

}
