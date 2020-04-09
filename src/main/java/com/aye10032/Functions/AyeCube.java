package com.aye10032.Functions;

import com.aye10032.Zibenbot;

import java.util.Random;
import java.util.Stack;

public class AyeCube extends BaseFunc {

    private Stack<String> cubeStack = new Stack<String>();
    private String[] cubarr = new String[]{"F", "B", "U", "D", "R", "L"};
    private String[] statusarr = new String[]{"", "", "'", "'", "2"};
    private int step = 20;
    private String cuberandom = "";

/*    public static void main(String[] args) {
        System.out.println(new AyeCube().getCuberandom());
    }*/

    public AyeCube(Zibenbot zibenbot) {
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
        }
    }
}
