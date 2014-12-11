package com.rwy.spider.service.spider.impl;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.bean.product.Product;
import com.rwy.spider.bean.product.ProductDetail;
import com.rwy.spider.bean.task.Task;
import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.service.base.BasePageProcessor;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.utils.StringFilterUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Selectable;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 淘宝手机专享数据抓取逻辑
 *
 * Created by Luocj on 2014/10/23.
 */
@Service("taobaoMobileSpiderService")
@Transactional
public class TaobaoMobileSpiderServiceImpl implements BasePageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TaobaoMobileSpiderServiceImpl.class);
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    private List<Product> productList;
    /**
     * 淘宝手机端产品列表搜索AJAX请求URL
     *
     * {1}搜索关键字
     * {2}分页
     * &cat=50944003
     */
    private static String LIST_URL = "http://s.m.taobao.com/search?&q={0}&sst=1&n=20&buying=buyitnow&m=api4h5&abtest=3&wlsort=3&page={1}";

    private static String DETAIL_URL ="http://h5.m.taobao.com/trip/ticket/detail.htm?id={0}&sid=d9a84a3ca12dde6f&abtest=3&rn=7b64dcb2020790ead4fc04e021072a68&forceH5=YES";

    private static String MATCH_LIST_URL = "http://s\\.m\\.taobao\\.com/search.*";
    @Resource
    private ProductService productService;

    @Override
    public void process(Page page) {
        if (page.getUrl().regex(MATCH_LIST_URL).match()) {
            JSONObject json = JSONObject.fromObject(page.getRawText());
            if (null != json && "true".equals(json.getString("result"))) {
                int totalPage = json.getInt("totalPage");
                int currentPage = getCurrentPage(page.getUrl().toString());
                if(currentPage<totalPage){
                    String nextUrl = getNextUrl(page.getUrl().toString(),currentPage+1);
                    page.addTargetRequest(nextUrl);
                }
                JSONArray listItem = json.getJSONArray("listItem");
                if(null!=listItem && listItem.size()>0){
                    spiderProductData(page, listItem);
                }
            }
        }
    }

    /**
     * 获取下一页的请求URL
     * @param url
     * @param nextPage
     * @return
     */
    private String getNextUrl(String url, int nextPage) {
        int index = url.lastIndexOf("=");
        url = url.substring(0,index+1) + nextPage;
        return url;
    }


    /**
     * 获取当前页码
     * @param url
     * @return
     */
    private int getCurrentPage(String url) {
        int index = url.lastIndexOf("=");
        int currentPage = Integer.parseInt(url.substring(index+1));
        return currentPage;
    }

    /**
     * 解析产品列表JSON
     * @param page
     * @param listItem
     */
    private void spiderProductData(Page page,JSONArray listItem) {
        for(Object obj : listItem){
            JSONObject item =(JSONObject)obj;
            String mobileDiscount = item.getString("mobileDiscount");
            if(StringUtils.isNotEmpty(mobileDiscount)){
                String storeName = item.getString("nick");
                double price = item.getDouble("price");
                String productName = item.getString("name");
//                String productUrl = item.getString("url");
                String itemId = item.getString("itemNumId");
                String productUrl = MessageFormat.format(DETAIL_URL,itemId);
                Product product = new Product(itemId,productName,productUrl,storeName,price);
                productList.add(product);
            }
        }

    }


    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public synchronized void execute(TaskExecution te) {

    }

    @Override
    public void execute(PlatForm pf,String taskType,Object ... objs) {
        productList = new ArrayList<Product>();
        for(Object obj : objs){
            Task task = (Task) obj;
            String newUrl = MessageFormat.format(LIST_URL, StringFilterUtils.getKeyword(task.getKeyword()),1);
            Spider.create(this).addUrl(newUrl).thread(20).run();
            logger.info("*** 关键字【"+task.getKeyword()+"】抓取完毕，共抓取："+productList.size()+"条数据，正在保存中 ***");
            String id = task.getId();
            Long platFormId = pf.getId();
            int saveCount = 0;
            for(Product product : productList){
                product.setTaskId(id);
                product.setPlatFormId(platFormId);
                product.setScenicId(task.getScenicId());
                product.setLow_price(task.getPrice());
                product.setTaskType(taskType);
                productService.save(product);
                saveCount ++;
            }
            logger.info("*** 数据保存结束，保存："+saveCount+"条 ***");
            productList.clear();
        }
        productList = null;
    }

}
