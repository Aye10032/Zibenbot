package com.aye10032.NLP;

import com.aye10032.Functions.BaseFunc;
import com.aye10032.Functions.CQMsg;
import com.aye10032.Zibenbot;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataCollect extends BaseFunc {

    public static void main(String[] args) {

    }

    public DataCollect(Zibenbot zibenbot) {
        super(zibenbot);
    }

    @Override
    public void setUp() {
        File database = new File(zibenbot.appDirectory + "\\nlpdata\\botnlpdata.db");
        if (!database.exists()){
            database.getParentFile().mkdirs();
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + zibenbot.appDirectory + "\\nlpdata\\botnlpdata.db");
                System.out.println("Opened database successfully");

                stmt = c.createStatement();
                String sql = "CREATE TABLE \"question\" (\n" +
                        "\t\"ID\"\tINTEGER NOT NULL,\n" +
                        "\t\"msg\"\tTEXT NOT NULL,\n" +
                        "\t\"fromQQ\"\tTEXT NOT NULL,\n" +
                        "\t\"fromGroup\"\tTEXT NOT NULL,\n" +
                        "\t\"time\"\tTEXT NOT NULL,\n" +
                        "\tPRIMARY KEY(\"ID\" AUTOINCREMENT)\n" +
                        ")";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
                    System.out.println("Creat table successfully");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
    }

    @Override
    public void run(CQMsg CQmsg) {
        boolean flag = false;
        if (CQmsg.fromClient == 1969631968L){
            return;
        }else if (CQmsg.isGroupMsg()) {
            List<Long> at_list = zibenbot.getCQCode().getAts(CQmsg.msg);
            if (at_list.size() != 0){
                for (long qq:at_list){
                    if (qq == 2375985957L || qq == 2155231604L){
                        flag = true;
                        break;
                    }
                }
            }else if (CQmsg.msg.contains("aye") || CQmsg.msg.contains("Aye") || CQmsg.msg.contains("阿叶")
                    || CQmsg.msg.contains("小叶") || CQmsg.msg.contains("叶受") || CQmsg.msg.contains("叶哥哥")) {
                flag = true;
            }
        }

        if (flag){
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + zibenbot.appDirectory + "\\nlpdata\\botnlpdata.db");
                System.out.println("Opened database successfully");

                stmt = c.createStatement();

                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                String sql = "INSERT INTO question (msg,fromQQ,fromGroup,time) " +
                        "VALUES ('"+CQmsg.msg+"', '"+CQmsg.fromClient+"', '"+CQmsg.fromGroup+"', '"+ft.format(dNow)+"' );";
                stmt.executeUpdate(sql);

                stmt.close();
                c.close();
                System.out.println("Records created successfully");
                zibenbot.getCoolQ().sendPrivateMsg(2375985957L,"已添加数据集：" + CQmsg.msg);
                replyMsg(CQmsg, "["+ft.format(dNow)+"][INFO] 本条对话已添加NLP待处理数据集");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
    }
}
