package com.aye10032.Utils;

import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.entity.Group;
import org.meowy.cqp.jcq.entity.Member;

import java.util.List;

/**
 * @author Dazo66
 */
public class IDNameUtil {

    private static String Null = "null";


    public static String getGroupNameByID(long id, List<Group> groups){
        for (Group group : groups) {
            if (group.getId() == id) {
                return group.getName();
            }
        }
        return Null;
    }

    public static String getGroupMemberNameByID(long fromGroup, long id, CoolQ CQ){
        Member member = CQ.getGroupMemberInfo(fromGroup, id);
        if (member != null) {
            return member.getTitle();
        } else {
            return Null;
        }
    }





}
