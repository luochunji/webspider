package com.rwy.spider.utils;

/**
 * Created by Luocj on 2014/10/24.
 */
public class CronExpConversionUtils {
    /**
     * 页面设置转为UNIX cron expressions 转换算法
     *
     * @param everyWhat
     * @param commonNeeds      包括 second minute hour
     * @param monthlyNeeds     包括 第几个星期 星期几
     * @param weeklyNeeds      包括 星期几
     * @param userDefinedNeeds 包括具体时间点
     * @return cron expression
     */
    public static String convertDateToCronExp(String everyWhat,
                                              String[] commonNeeds, String[] monthlyNeeds, String weeklyNeeds,
                                              String userDefinedNeeds) {
        String cronEx = "";
        String commons = commonNeeds[0] + " " + commonNeeds[1] + " "
                + commonNeeds[2] + " ";
        String dayOfWeek = "";
        if ("monthly".equals(everyWhat) && null != monthlyNeeds) {
            //  eg.: 6#3 (day 6 = Friday and "#3" = the 3rd one in the
            //  month)
            dayOfWeek = monthlyNeeds[1]
                    + CronExRelated.specialCharacters
                    .get(CronExRelated._THENTH) + monthlyNeeds[0];
            cronEx = (commons
                    + CronExRelated.specialCharacters.get(CronExRelated._ANY)
                    + " "
                    + CronExRelated.specialCharacters.get(CronExRelated._EVERY)
                    + " " + dayOfWeek + " ").trim();
        } else if ("weekly".equals(everyWhat) && null != weeklyNeeds) {
            dayOfWeek = weeklyNeeds;  //  1
            cronEx = (commons
                    + CronExRelated.specialCharacters.get(CronExRelated._ANY)
                    + " "
                    + CronExRelated.specialCharacters.get(CronExRelated._EVERY)
                    + " " + dayOfWeek + "   ").trim();
        } else if ("dayly".equals(everyWhat) ) {
            cronEx = (commons
                    + CronExRelated.specialCharacters.get(CronExRelated._EVERY)
                    + " "
                    + CronExRelated.specialCharacters.get(CronExRelated._EVERY)
                    + " "
                    + CronExRelated.specialCharacters.get(CronExRelated._ANY)
                    + " ").trim();
        }else if ("userDefined".equals(everyWhat) && null != userDefinedNeeds) {
            String dayOfMonth = userDefinedNeeds.split("-")[2];
            if (dayOfMonth.startsWith("0")) {
                dayOfMonth = dayOfMonth.replaceFirst("0", "");
            }
            String month = userDefinedNeeds.split("-")[1];
            if (month.startsWith("0")) {
                month = month.replaceFirst("0", "");
            }
            String year = userDefinedNeeds.split("-")[0];
            // FIXME 暂时不加年份 Quartz报错
                         /* cronEx = (commons + dayOfMonth + " " + month + " "
                     + CronExRelated.specialCharacters.get(CronExRelated._ANY)
                     + " " + year).trim(); */
            cronEx = (commons + dayOfMonth + " " + month + " "
                    + CronExRelated.specialCharacters.get(CronExRelated._ANY)
                    + "   ").trim();
        }
        return cronEx;
    }

    /**
     * 获取调度时间表达式
     * @param runtime
     * @return
     */
    public static String getCronExpression(String runtime) {
        String cronExpression = "";
        String[] time = runtime.split(":");

        String[] commonNeeds = {time[2], time[1], time[0]};
        String everyWhat = "dayly";
        // 得到时间规则
        cronExpression = CronExpConversionUtils.convertDateToCronExp(everyWhat, commonNeeds,
                null, null, null);
        return cronExpression;
    }
}
