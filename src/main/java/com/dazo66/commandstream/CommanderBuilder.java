package com.dazo66.commandstream;

import com.aye10032.Utils.ExceptionUtils;
import com.dazo66.commandstream.interfaces.CommandRun;
import com.dazo66.commandstream.interfaces.CommanderFactory;
import com.dazo66.commandstream.interfaces.ExceptionHandler;
import com.dazo66.commandstream.interfaces.PieceCheck;

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
 *      execute("test 1");
 *
 *
 * @author Dazo66
 */
public class CommanderBuilder {

    private ExceptionHandler eHandler = ExceptionUtils::printStack;
    private Stack<CommandPiece> stack = new Stack<>();
    private CommandPiece main = new CommandPiece();
    private CommandPiece current;
    private or currentOr;

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
        current.addOr(currentOr = new or(b));
        return this;
    }

    public CommanderBuilder next(){
        current = new CommandPiece();
        stack.push(current);
        currentOr.setPatch(current);
        currentOr = null;
        return this;
    }

    public CommanderBuilder pop(){
        stack.pop();
        current = stack.peek();
        currentOr = current.getOrs().get(current.getOrs().size() - 1);
        return this;
    }

    public CommanderBuilder ifNot(CommandRun runnable){
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

    protected ExceptionHandler geteHandler() {
        return eHandler;
    }

    protected Stack<CommandPiece> getStack() {
        return stack;
    }

    protected void setStack(Stack<CommandPiece> stack) {
        this.stack = stack;
    }

    protected CommandPiece getMain() {
        return main;
    }

    protected void setMain(CommandPiece main) {
        this.main = main;
    }

    protected CommandPiece getCurrent() {
        return current;
    }

    protected void setCurrent(CommandPiece current) {
        this.current = current;
    }

    protected or getCurrentOr() {
        return currentOr;
    }

    protected void setCurrentOr(or currentOr) {
        this.currentOr = currentOr;
    }
}
