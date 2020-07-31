package com.dazo66.commandstream;

import com.dazo66.commandstream.interfaces.CommandRun;
import com.dazo66.commandstream.interfaces.PieceCheck;

public class or {

    private PieceCheck pieceCheck;
    private CommandRun run;
    private CommandPiece patch;

    public void setPieceCheck(PieceCheck pieceCheck) {
        this.pieceCheck = pieceCheck;
    }

    public CommandPiece getPatch() {
        return patch;
    }

    public CommandRun getRun() {
        return run;
    }

    public PieceCheck getPieceCheck() {
        return pieceCheck;
    }

    public or(PieceCheck pieceCheck) {
        this.pieceCheck = pieceCheck;
    }

    public void setRun(CommandRun run) {
        this.run = run;
    }

    public void setPatch(CommandPiece patch) {
        this.patch = patch;
    }

    public CommandPiece getPiece() {
        return patch;
    }

    public boolean hasPiece() {
        return getPiece() != null;
    }

    public boolean ret(String s) {
        return pieceCheck.BoolReturn(s);
    }

    public boolean hasRunable(){
        return run != null;
    }

    public void run(String[] command){
        run.run(command);
    }

}
