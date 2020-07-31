package com.aye10032.Functions;

import com.aye10032.Functions.funcutil.BaseFunc;
import com.aye10032.Functions.funcutil.CQMsg;
import com.aye10032.Zibenbot;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;

public class CubeFunc extends BaseFunc {

    private Stack<String> cubeStack = new Stack<String>();
    private String[] cubarr = new String[]{"F", "B", "U", "D", "R", "L"};
    private String[] statusarr = new String[]{"", "", "'", "'", "2"};
    private int step = 20;
    private String cuberandom = "";

/*    public static void main(String[] args) {
        System.out.println(new AyeCube().getCuberandom());
    }*/

    public CubeFunc(Zibenbot zibenbot) {
        super(zibenbot);
        Random random = new Random();
        int num = random.nextInt(5);
        step += num;
        for (int i = 0; i < step; i++) {
            String thisStep = nextStep(random) + statusarr[random.nextInt(statusarr.length)];
            cubeStack.push(thisStep);
        }

        String temp = "";
        while (!cubeStack.empty()) {
            temp += cubeStack.pop() + " ";
        }

        setCuberandom(temp);
    }

    private String nextStep(Random random) {
        String temp = "";

        if (!cubeStack.empty()) {
            while (temp.equals("") || cubeStack.peek().contains(temp)) {
                temp = cubarr[random.nextInt(cubarr.length)];
            }
        } else {
            temp = cubarr[random.nextInt(cubarr.length)];
        }

        return temp;
    }

    public String getCuberandom() {
        return this.cuberandom;
    }

    public void setCuberandom(String cuberandom) {
        this.cuberandom = cuberandom;
    }

    public void setUp() {

    }

    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.equals(".3")) {
            zibenbot.replyMsg(CQmsg, getCuberandom());
        }else if (CQmsg.msg.startsWith("CFOP")||CQmsg.msg.startsWith("cfop")) {
            try {
                if (CQmsg.isTeamspealMsg()) {
                    zibenbot.replyMsg(CQmsg, "ts频道无法发图片，请从群聊或者私聊获取");
                    return;
                }
                if (CQmsg.msg.contains("F2L")||CQmsg.msg.contains("f2l")) {
                    zibenbot.replyMsg(CQmsg, zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP2.jpg")));
                } else if (CQmsg.msg.contains("OLL")||CQmsg.msg.contains("oll")) {
                    zibenbot.replyMsg(CQmsg, zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP3.jpg")));
                } else if (CQmsg.msg.contains("PLL")||CQmsg.msg.contains("pll")) {
                    zibenbot.replyMsg(CQmsg, zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP4.jpg")));
                } else {
                    zibenbot.replyMsg(CQmsg,
                            zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP1.jpg"))
                                    +zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP2.jpg"))
                                    +zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP3.jpg"))
                                    +zibenbot.getCQCode().image(new File(zibenbot.appDirectory + "\\image\\CFOP4.jpg")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
