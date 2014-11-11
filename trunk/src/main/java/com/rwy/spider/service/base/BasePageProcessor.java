package com.rwy.spider.service.base;

import com.rwy.spider.bean.task.TaskExecution;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Luocj on 2014/10/23.
 */
public interface BasePageProcessor extends PageProcessor {

    public void execute(TaskExecution te);
}
