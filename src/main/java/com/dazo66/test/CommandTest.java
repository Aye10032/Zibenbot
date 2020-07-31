package com.dazo66.test;

import com.aye10032.Functions.funcutil.FuncCommanderFactory;
import com.dazo66.commandstream.Commander;
import com.dazo66.commandstream.CommanderBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.lucene.util.RamUsageEstimator;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class CommandTest {


    public static void main(String[] args) {

        final AtomicReference<String> s = new AtomicReference<>("111111");

        /*
         * math plus d1 d2
         * math reduce d1 d2
         * math mult d1 d2
         * math div d1 d2
         */
        Commander commander = new CommanderBuilder()
                .start()
                .or("math"::equals)
                .next()
                    .or("plus"::equals)
                    .next()
                        .or(NumberUtils::isDigits)
                        .run((strings) -> System.out.println(Arrays.toString(strings)))
                        //.ifNot((strings) -> System.out.println("plus d1 wrong" + s.get()))
                        .next()
                            .or(NumberUtils::isDigits)
                            .ifNot((strings) -> System.out.println("plus d2 wrong"))
                            .run((strings) -> {
                                Double d1 = NumberUtils.toDouble(strings[2]);
                                Double d2 = NumberUtils.toDouble(strings[3]);
                                System.out.println(d1 + d2);
                            })
                            .pop()
                        .pop()
                    .or("reduce"::equals)
                    .next()
                        .or(NumberUtils::isDigits)
                        //.ifNot((strings) -> System.out.println("reduce d1 wrong"))
                        .next()
                            .or(NumberUtils::isDigits)
                            //.ifNot((strings) -> System.out.println("reduce d2 wrong"))
                            .run((strings) -> {
                                Double d1 = NumberUtils.toDouble(strings[2]);
                                Double d2 = NumberUtils.toDouble(strings[3]);
                                System.out.println(d1 - d2);
                            })
                            .pop()
                        .pop()
                    .or("mult"::equals)
                    .next()
                        .or(NumberUtils::isDigits)
                        //.ifNot((strings) -> System.out.println("mult d1 wrong"))
                        .next()
                            .or(NumberUtils::isDigits)
                            //.ifNot((strings) -> System.out.println("mult d2 wrong"))
                            .run((strings) -> {
                                Double d1 = NumberUtils.toDouble(strings[2]);
                                Double d2 = NumberUtils.toDouble(strings[3]);
                                System.out.println(d1 * d2);
                            })
                            .pop()
                        .pop()
                    .or("div"::equals)
                    .next()
                        .or(NumberUtils::isDigits)
                        //.ifNot((strings) -> System.out.println("div d1 wrong"))
                        .next()
                            .or(NumberUtils::isDigits)
                            //.ifNot((strings) -> System.out.println("div d2 wrong"))
                            .run((strings) -> {
                                Double d1 = NumberUtils.toDouble(strings[2]);
                                Double d2 = NumberUtils.toDouble(strings[3]);
                                System.out.println(d1 / d2);
                            })
                            .pop()
                        .pop()
                .build(new FuncCommanderFactory());

        commander.execute("math plus 1 2");
        commander.execute("math reduce 1 2");
        commander.execute("math mult 1 2");
        commander.execute("math div 1 2");


        commander.execute("math plus x 2");
        s.set("222222");
        commander.execute("math plus x 2");

        System.out.println(RamUsageEstimator.humanSizeOf(commander));
        System.out.println(RamUsageEstimator.humanSizeOf(new CommanderBuilder().start().build()));
/*        commander.execute("math reduce 1 x");
        commander.execute("math mult x 2");
        commander.execute("math div 1 x");
        commander.execute("math plus 1 2 3");

        commander.execute("math plus 1");

        System.out.println(((FuncCommander) commander).getCqMsg());*/

    }




}
