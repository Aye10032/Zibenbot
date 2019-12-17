package com.aye10032;

import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.message.CQCode;

import java.io.File;
import java.util.Random;

public class SetuUtil {

    private File image;

    public SetuUtil(String appDirectory) {
        File file = new File(appDirectory + "\\setu");
        File[] fs = file.listFiles();

        boolean Flag = true;

        while (Flag) {
            Random random = new Random();
            assert fs != null;
            int num = random.nextInt(fs.length);
            if (fs[num].isFile()) {
                image = fs[num];
                Flag = false;
            }
        }
    }

    public File getImage() {
        return image;
    }

}
