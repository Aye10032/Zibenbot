package com.aye10032.Utils.Video;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VideoClass {

    private Integer videoNum;
    private Integer videoSum;
    private List<VideoData> dataList;

    public VideoClass() {
        videoSum = 0;
        dataList = new ArrayList<VideoData>();
    }

    public int getVideoNum() {
        if (dataList.isEmpty()) {
            setVideoNum(0);
        } else {
            setVideoNum(dataList.size());
        }

        return this.videoNum;
    }

    public void setVideoNum(int num) {
        this.videoNum = num;
    }

    public int getVideoSum() {
        return this.videoSum;
    }

    public void addVideoSum() {
        videoSum += 1;
    }

    public boolean addVideo(VideoData data) {
        String link = data.getVideoLink();
        boolean addSuccess = true;

        for (VideoData data1 : dataList) {
            if (data1.getVideoLink().equals(link)) {
                if (data1.getDescription().equals("")) {
                    data1.setDescription(data.getDescription());
                } else {
                    if (!data.getDescription().equals("")) {
                        data1.setDescription(data1.getDescription() + "," + data.getDescription());
                    }
                }
                addSuccess = false;
                break;
            }
        }

        if (addSuccess) {
            dataList.add(data);
        }

        getVideoNum();
        return addSuccess;
    }

    public boolean VideoDone(String flag) {
        boolean hasVideo = false;
        for (VideoData data : dataList) {
            if (data.getVideoLink().equals(flag) || (data.getNO() + "").equals(flag)) {
                data.setHasDone(true);
                hasVideo = true;
                break;
            }
        }

        return hasVideo;
    }

    public List<VideoData> getDataList() {
        return dataList;
    }

    public void updateList() {
        Iterator<VideoData> iterator = dataList.iterator();

        while (iterator.hasNext()) {
            VideoData data = iterator.next();
            if (data.isHasDone()) {
                iterator.remove();
            }
        }
    }

}
