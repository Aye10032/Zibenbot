package com.dazo66.commandstream;

import com.dazo66.commandstream.interfaces.CommandRun;

import java.util.ArrayList;
import java.util.List;

public class CommandPiece {

    private List<or> ors = new ArrayList<>();
    private CommandRun ifNot;


    public void setOrs(List<or> ors) {
        this.ors = ors;
    }

    public void addOr(or or){
        ors.add(or);
    }

    public List<or> getOrs() {
        return ors;
    }

    public CommandRun getIfNot() {
        return ifNot;
    }

    public void setIfNot(CommandRun ifNot) {
        this.ifNot = ifNot;
    }

    protected List<or> match(String patch) {
        List<or> r = new ArrayList<>();
        for (or or : ors) {
            if (or.ret(patch)) {
                r.add(or);
            }
        }
        return r;
    }
}
