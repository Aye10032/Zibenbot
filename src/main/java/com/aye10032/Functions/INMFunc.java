package com.aye10032.Functions;

import com.aye10032.Functions.funcutil.BaseFunc;
import com.aye10032.Functions.funcutil.CQMsg;
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
        }else if (CQmsg.msg.contains("sodayo")||CQmsg.msg.equals("是啊")||CQmsg.msg.equals("救世啊")){
            zibenbot.replyMsg(CQmsg,zibenbot.getCQCode().record("sodayo.amr"));
        }else if (CQmsg.msg.contains("压力马斯内")||CQmsg.msg.equals("确实")||CQmsg.msg.equals("压力吗死内")){
            zibenbot.replyMsg(CQmsg,zibenbot.getCQCode().record("压力马斯内.amr"));
        }
    }
}
