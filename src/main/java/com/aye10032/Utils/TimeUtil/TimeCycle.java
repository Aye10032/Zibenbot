package com.aye10032.Utils.TimeUtil;

import java.util.Date;

/**
 *
 * 时间循环类
 * 存储一个得到下个时间的方法
 * 具体实例参考 {@link TimeConstant}
 *
 * @author Dazo66
 */
public interface TimeCycle {

    Date getNextTime(Date date);

}
