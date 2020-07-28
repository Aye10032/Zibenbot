package com.aye10032.Utils.TimeUtil;

import com.aye10032.Functions.CQMsg;
import com.aye10032.Zibenbot;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * {@inheritDoc}
 */
public abstract class SubscribableBase implements ISubscribable {

    private Zibenbot bot;
    private List<CQMsg> recipients;

    public SubscribableBase(Zibenbot zibenbot) {
        this.bot = zibenbot;
    }

    public Zibenbot getBot() {
        return bot;
    }

    /**
     * 回复所有收件人
     *
     * @param s 消息
     */
    public void replyAll(String s) {
        if (recipients != null) {
            for (CQMsg cqMsg : recipients) {
                bot.replyMsg(cqMsg, s);
            }
        }
    }

    @Override
    public List<CQMsg> getRecipients() {
        return recipients == null ? Collections.emptyList() : recipients;
    }

    @Override
    public void setRecipients(List<CQMsg> cqMsgs) {
        recipients = cqMsgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubscribableBase that = (SubscribableBase) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}


