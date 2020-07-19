package com.aye10032.Functions;

import com.aye10032.Zibenbot;

public class INMFunc extends BaseFunc {
    public INMFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void run(CQMsg CQmsg) {
        if (CQmsg.msg.contains("目力")){
            zibenbot.replyMsg(CQmsg,zibenbot.getCQCode().record("目力.amr"));
        }else if (CQmsg.msg.contains("sodayo")){
            zibenbot.replyMsg(CQmsg,zibenbot.getCQCode().record("sodayo.amr"));
        }else if (CQmsg.msg.contains("压力马斯内")){
            zibenbot.replyMsg(CQmsg,zibenbot.getCQCode().record("压力马斯内.amr"));
        }
    }
}
