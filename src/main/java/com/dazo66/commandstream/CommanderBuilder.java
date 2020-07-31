package com.dazo66.commandstream;

import com.aye10032.Utils.ExceptionUtils;
import com.dazo66.commandstream.interfaces.CommanderFactory;
import com.dazo66.commandstream.interfaces.PieceCheck;
import com.dazo66.commandstream.interfaces.CommandRun;
import com.dazo66.commandstream.interfaces.ExceptionHandler;

import java.util.Stack;

/**
 * Commander commander = new CommanderBuilder()
 *      .setExceptionHandler(eHandler)
 *      .start()
 *      .or(s -> "test".equal)
 *      .next()
 *          .or(s -> "1".equal(s))
 *          .run( () -> printIn("test 1"))
 *          .or(s -> "2".equal(s))
 *          .next()
 *              .or(s -> "2".equal(s))
 *              .run( () -> printIn("test 2 2"))
 *              .ifNot( () -> printLn("1 or 2"))
 *          .pop()
 *      .or(s -> "TEST".equal(s))
 *      .next()
 *          .or(s -> "1".equal(s))
 *          .run( () -> printIn("TEST 1"))
 *          .or(s -> "2".equal(s))
 *          .run( () -> printIn("TEST 2"))
 *          .pop()
 *      .build();
 *
 *
 *      commander.execute("test 1");
 *
 *
 */




public class CommanderBuilder {

    private ExceptionHandler eHandler = ExceptionUtils::printStack;
    private Stack<Commander.CommandPiece> stack = new Stack<>();
    private Commander.CommandPiece main = new Commander.CommandPiece();
    private Commander.CommandPiece current;
    private Commander.or currentOr;

    public CommanderBuilder seteHandler(ExceptionHandler eHandler) {
        this.eHandler = eHandler;
        return this;
    }

    public CommanderBuilder start() {
        current = main;
        stack.push(current);
        return this;
    }

    public CommanderBuilder run(CommandRun run){
        currentOr.setRun(run);
        return this;
    }

    public CommanderBuilder or(PieceCheck b) {
        current.addOr(currentOr = new Commander.or(b));
        return this;
    }

    public CommanderBuilder next(){
        current = new Commander.CommandPiece();
        stack.push(current);
        currentOr.setPatch(current);
        currentOr = null;
        return this;
    }

    public CommanderBuilder pop(){
        stack.pop();
        current = stack.peek();
        currentOr = current.ors.get(current.ors.size() - 1);
        return this;
    }

    public CommanderBuilder ifNot(Runnable runnable){
        current.setIfNot(runnable);
        return this;
    }

    public Commander build(){
        Commander ret = new Commander();
        ret.seteHandler(eHandler);
        ret.setPiece(main);
        return ret;
    }

    public Commander build(CommanderFactory factory){
        Commander ret = factory.build();
        ret.seteHandler(eHandler);
        ret.setPiece(main);
        return ret;
    }
}
