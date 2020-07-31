package com.dazo66.commandstream;

import com.dazo66.commandstream.interfaces.ExceptionHandler;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;


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
        List<String> list = Lists.newArrayList(s.trim().replaceAll(" +", " ").split(" "));
        or or = findPiece(list, piece);

        List<CommandPiece> patchs = getRoad(list, piece);
        if (or != null) {
            if (list.size() == patchs.size()) {
                or.getRun().run(list.toArray(new String[]{}));
            }
        } else {
            if (patchs.size() > 0) {
                CommandPiece p = patchs.get(patchs.size() - 1);
                if (p.getIfNot() != null) {
                    p.getIfNot().run(list.toArray(new String[]{}));
                }
            }
        }
    }

    protected or findPiece(List<String> strings, CommandPiece main) {
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

    protected CommandPiece getPiece() {
        return piece;
    }

    protected ExceptionHandler geteHandler() {
        return eHandler;
    }

    protected List<CommandPiece> getRoad(List<String> strings, CommandPiece main) {
        return getRoad(new ArrayList<>(), strings, main);
    }

    private List<CommandPiece> getRoad(List<CommandPiece> ret, List<String> strings,
                                       CommandPiece main) {
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
                if (patches.size() > 0 && patches.size() == ps.size() && patches.get(patches.size() - 1).getIfNot() != null && ps.get(ps.size() - 1).getIfNot() == null) {
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

}
