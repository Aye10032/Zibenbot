package com.dazo66.commandstream;

import com.dazo66.commandstream.interfaces.PieceCheck;
import com.dazo66.commandstream.interfaces.CommandRun;
import com.dazo66.commandstream.interfaces.ExceptionHandler;
import com.google.common.collect.Lists;

import java.util.*;


/**
 * Commander commander = new CommanderBuilder()
 *      .setExceptionHandler(eHandler)
 *      .start()
 *      .PieceCheck(s -> "test".equal)
 *      .next()
 *          .or(s -> "1".equal(s))
 *          .run( () -> printIn("test 1"))
 *          .or(s -> "2".equal(s))
 *          .next()
 *              .or(s -> "2".equal(s))
 *              .run( () -> printIn("test 2 2"))
 *          .ifNot( () -> printLn("1 or 2"))
 *          .pop()
 *      .PieceCheck(s -> "TEST".equal(s))
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
public class Commander {

    private ExceptionHandler eHandler;
    private CommandPiece piece;

    public void execute(String s) {
        Stack<String> stack = new Stack<>();
        List<String> list = Lists.newArrayList(s.trim().replaceAll(" +", " ").split(" "));
        or or = findPiece(list, piece);

        List<CommandPiece> patchs = getRoad(list, piece);
        if (or != null) {
            if (list.size() == patchs.size()) {
                or.run.run(list.toArray(new String[]{}));
            }
        } else {
            if (patchs.size() > 0) {
                CommandPiece p = patchs.get(patchs.size() - 1);
                if (p.ifNot != null) {
                    p.ifNot.run();
                }
            }
        }
    }

    private or findPiece(List<String> strings, CommandPiece main) {
        if (strings.size() > 0) {
            List<or> list = main.match(strings.get(0));
            for (or or : list) {
                if (or.hasRunable() && strings.size() == 1) {
                    return or;
                }
                if (or.hasPiece()) {
                    or or1 = findPiece(strings.subList(1, strings.size()), or.getPiece());
                    if (or1 != null) {
                        return or1;
                    }
                }
            }
 /*           if (list.isEmpty()) {
                if (main.ifNot != null) {
                    main.ifNot.run();
                }
            }*/
        }
        return null;
    }
    private List<CommandPiece> getRoad(List<String> strings, CommandPiece main){
        return getRoad(new ArrayList<>(), strings, main);
    }

    private List<CommandPiece> getRoad(List<CommandPiece> ret, List<String> strings, CommandPiece main) {
        List<List<CommandPiece>> ls = new ArrayList<>();
        List<CommandPiece> l = new ArrayList<>(ret);
        if (strings.size() > 0) {
            List<or> list = main.match(strings.get(0));
            if (list.isEmpty()) {
                l.add(main);
                return l;
            } else {
                l.add(main);
            }
            ls.add(l);
            for (or or : list) {
                if (or.hasRunable() && strings.size() == 1) {
                    return l;
                }
                if (or.hasPiece()) {
                    if (strings.size() == 1) {
                        l.add(or.getPiece());
                        return l;
                    } else {
                        ls.add(getRoad(l, strings.subList(1, strings.size()), or.getPiece()));
                    }
                }
            }
            List<CommandPiece> ps = new ArrayList<>();
            for (List<CommandPiece> patches : ls) {
                if (patches.size() > ps.size()) {
                    ps = patches;
                }
                if (patches.size() > 0
                        && patches.size() == ps.size()
                        && patches.get(patches.size() - 1).ifNot != null
                        && ps.get(ps.size() - 1).ifNot == null ) {
                    ps = patches;
                }
            }
            return ps;
        } else {

            //l.add(main);
        }
        return l;
    }


    protected void setPiece(CommandPiece piece) {
        this.piece = piece;
    }

    protected void seteHandler(ExceptionHandler eHandler) {
        this.eHandler = eHandler;
    }

    public static <T>Stack reverseStack(Stack<T> s){
        Queue<T> r = new LinkedList<>();
        Stack<T> ret = new Stack<>();

        //r.offer() 是将指定队列插到r中

        //s.pop是 移除堆栈顶部的对象，并作为此函数的值返回该对象。

        while(s.size()>0) {
            r.offer(s.pop());
        }

        //s.push() 把项压入堆栈顶部。

        //r.poll() 获取并移除此队列的头，如果此队列为空，则返回 null。
        while(r.size()>0) {
            ret.push(r.poll());
        }
        return ret;

    }

    static class CommandPiece {

        List<or> ors = new ArrayList<>();
        private Runnable ifNot;


        protected void setOrs(List<or> ors) {
            this.ors = ors;
        }

        protected void addOr(or or){
            ors.add(or);
        }

        protected void setIfNot(Runnable ifNot) {
            this.ifNot = ifNot;
        }

        protected List<or> match(String patch){
            List<or> r = new ArrayList<>();
            for (or or : ors) {
                if (or.ret(patch)) {
                    r.add(or);
                }
            }
            return r;
        }
    }

    static class or {

        private PieceCheck boolRet;
        private CommandRun run;
        private CommandPiece patch;


        protected or(PieceCheck boolRet) {
            this.boolRet = boolRet;
        }

        protected void setRun(CommandRun run) {
            this.run = run;
        }

        protected void setPatch(CommandPiece patch) {
            this.patch = patch;
        }

        protected CommandPiece getPiece() {
            return patch;
        }

        protected boolean hasPiece() {
            return getPiece() != null;
        }

        protected boolean ret(String s) {
            return boolRet.BoolReturn(s);
        }

        protected boolean hasRunable(){
            return run != null;
        }

        protected void run(String[] command){
            run.run(command);
        }
    }

}
