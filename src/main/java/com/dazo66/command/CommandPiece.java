package com.dazo66.command;

import com.dazo66.command.interfaces.CommandRun;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令片，代表一个命令深度，可以包含多个分支
 * @author Dazo66
 */
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

    protected List<or> match(String[] patch) {
        List<or> r = new ArrayList<>();
        for (or or : ors) {
            if (or.match(patch)) {
                r.add(or);
            }
        }
        return r;
    }
}
