package com.rwy.spider.service.base;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.bean.task.Task;
import com.rwy.spider.bean.task.TaskExecution;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Luocj on 2014/10/23.
 */
public interface BasePageProcessor extends PageProcessor {

    @Deprecated
    public void execute(TaskExecution te);

    public void execute(PlatForm pf,String taskType,Object ... objs);
}
