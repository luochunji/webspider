package com.rwy.spider.service.spider.impl;

import com.rwy.spider.bean.platform.PlatForm;
import com.rwy.spider.bean.product.Product;
import com.rwy.spider.bean.product.ProductDetail;
import com.rwy.spider.bean.task.Task;
import com.rwy.spider.bean.task.TaskExecution;
import com.rwy.spider.bean.task.TaskScheduler;
import com.rwy.spider.constant.Constant;
import com.rwy.spider.service.base.BasePageProcessor;
import com.rwy.spider.service.product.ProductService;
import com.rwy.spider.utils.MD5Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Luocj on 2014/10/23.
 */
@Service("taobaoSpiderV2Service")
@Transactional
public class TaobaoSpiderV2ServiceImpl implements BasePageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TaobaoSpiderV2ServiceImpl.class);
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    private Map<String,Product> productMap;
    private String regexItemId = "(^|\\?|&)itemId=(\\d*)(&|$)";
    private String regexId = "(^|\\?|&)id=(\\d*)(&|$)";
    /**
     * 淘宝网产品列表搜索AJAX请求URL
     *
     * {0}分页起始值 从0开始
     * {1}搜索关键字
     * {2}搜索日期 format:yyyyMMdd
     * &cat=50944003
     */
    private static String LIST_URL = "http://s.taobao.com/search?data-key=s&data-value={0}&ajax=true&q={1}&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&initiative_id=tbindexz_{2}&style=list&tab=all&bcoffset=1&s=0";
    /**
     * 产品价格明细（种类、类别、有效期、入园限制、价格等）
     * {0}产品编号
     */
    private static String PRICE_URL = "http://detailskip.taobao.com/json/travel/travel_ifq.htm?itemId={0}&travelType=3";
    /**
     * 产品规则（退票规则、改期规则、预定须知）
     * {0}产品编号
     */
    private static String RULE_URL = "http://tds.alicdn.com/json/travel/sku_info_json.htm?itemId={0}&travelType=3";

    private static String MATCH_LIST_URL = "http://s\\.taobao\\.com/search.*";
    private static String MATCH_PRICE_URL = "http://detailskip\\.taobao\\.com/json.*";
    private static String MATCH_RULE_URL = "http://tds\\.alicdn\\.com/json.*";
    private static String MATCH_DETAIL_URL = "http://item\\.taobao\\.com/item.*";


    @Resource
    private ProductService productService;

    @Override
    public void process(Page page) {
        if(page.getUrl().regex(MATCH_LIST_URL).match()){
            JSONObject json = JSONObject.fromObject(page.getRawText());
            if(null != json){
                JSONObject pager = null;
                try{
                    pager = json.getJSONObject("mods").getJSONObject("pager").getJSONObject("data");
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(null != pager && pager.size()>0){
                    int pageSize = pager.getInt("pageSize");
                    int currentPage = pager.getInt("currentPage");
                    int totalPage = pager.getInt("totalPage");
                    if(currentPage != totalPage){
                        //组装下一页的请求URL
                        int nextPage = currentPage*pageSize;
                        String keyword = json.getJSONObject("mainInfo").getJSONObject("srpGlobal").getString("q");
                        String date = DateFormatUtils.format(new Date(), "yyyyMMdd");
                        String nextUrl = MessageFormat.format(LIST_URL, nextPage, URLEncoder.encode(keyword), date);
                        page.addTargetRequest(nextUrl);
                    }
                }
                spiderProductData(page,json);
            }else{

            }
        }else if(page.getUrl().regex(MATCH_PRICE_URL).match()){
            try {
                spiderPriceData(page);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(page.getUrl().regex(MATCH_RULE_URL).match()){
//                spiderRuleData(page);
        }else if(page.getUrl().regex(MATCH_DETAIL_URL).match()){
            spiderItemData(page);
        }
    }

    /**
     * 解析产品价格JSON
     * @param page
     * @throws Exception
     */
    private void spiderPriceData(Page page) throws Exception {
        JSONObject jobj = JSONObject.fromObject(page.getRawText());
        String itemId =getItemId(page,regexItemId);
        String skuId = "";
        Page p = new Page();
        p.setHtml(new Html(productMap.get(itemId).getHtml()));
        int code = jobj.getInt("code");
        if(200 == code){
            JSONObject jsonData = jobj.getJSONObject("data");
            for(Object obj : jsonData.keySet()){
                String key = obj.toString();
                String[] strs = key.split(";");
                JSONObject expireDate = jsonData.getJSONObject(key).getJSONObject("expireDate");
                skuId = jsonData.getJSONObject(key).getString("skuId");
                for(Object ikey : expireDate.keySet()){
                    JSONObject xx = expireDate.getJSONObject(ikey.toString());
                    String category = p.getHtml().xpath("//li[@data-value='"+strs[1]+"']/a/span/text()").toString();
                    String type = p.getHtml().xpath("//li[@data-value='"+strs[2]+"']/a/span/text()").toString();
                    String price = xx.getString("price");
                    String name = xx.getString("name");
                    String desc = xx.getString("desc");
                    Date orderStartDate = null;
                    Date orderEndDate = null;
                    if(StringUtils.isNotEmpty(xx.getString("orderStartDate"))){
                        orderStartDate = DateUtils.parseDate(xx.getString("orderStartDate"), "yyyy-MM-dd");
                    }
                    if(StringUtils.isNotEmpty(xx.getString("orderEndDate"))){
                        orderEndDate = DateUtils.parseDate(xx.getString("orderEndDate"), "yyyy-MM-dd");
                    }
                    String validWeeks = Constant.TAOBAO_VALID_PARAM.get(xx.getString("validWeeks")) == null? "OTHER":Constant.TAOBAO_VALID_PARAM.get(xx.getString("validWeeks"));
                    ProductDetail pd = new ProductDetail(skuId,Double.valueOf(price)/100,category,type,name,desc,orderStartDate,orderEndDate,validWeeks);
                    productMap.get(itemId).addProductDetail(pd);
                }
            }
        }else if(500 ==code){
            List<String> saList = p.getHtml().xpath("//script").all();
            String hubConfig = "";
            for(String str : saList){
                if(str.contains("Hub.config.set")){
                    hubConfig = str;
                    break;
                }
            }
            saList = null;
            int beginIndex = hubConfig.indexOf("skuMap");
            if(beginIndex != -1){
                hubConfig = hubConfig.substring(beginIndex-1);
                int endIndex = hubConfig.indexOf("Hub.config.set");
                hubConfig = hubConfig.substring(0,endIndex);
                int endIndex2 = hubConfig.lastIndexOf("}");
                hubConfig = hubConfig.substring(0,endIndex2);
                hubConfig = "{"+hubConfig;
                JSONObject json = JSONObject.fromObject(hubConfig);
                JSONObject skuMapJson = (JSONObject)json.get("skuMap");
                Map paramMap = new HashMap();
                for(Object key : skuMapJson.keySet()){
                    String[] strs = key.toString().split(";");
                    for(String str : strs){
                        if(StringUtils.isBlank(str)) continue;
                        String param = Constant.TAOBAO_PARAM.get(str.split(":")[0]);
                        if(StringUtils.isNotBlank(param)){
                            paramMap.put(param,p.getHtml().xpath("//li[@data-value='"+str+"']/a/span/text()").toString());
                        }
                    }
                    JSONObject skuJson = (JSONObject) skuMapJson.get(key);
                    skuId = skuJson.getString("skuId");
                    String category = paramMap.get("category")==null?"":paramMap.get("category").toString();
                    String type = paramMap.get("type")==null?"":paramMap.get("type").toString();
                    String price = skuJson.getString("price");
                    ProductDetail pd = new ProductDetail(skuId,Double.valueOf(price),category,type);
                    productMap.get(itemId).addProductDetail(pd);
                    paramMap.clear();
                }
                paramMap = null;
            }

        }else{
            Product product = productMap.get(itemId);
            ProductDetail pd = new ProductDetail(product.getPrice());
            product.addProductDetail(pd);
        }
        p = null;
    }

    /**
     * 解析产品列表JSON
     * @param page
     * @param json
     */
    private void spiderProductData(Page page,JSONObject json) {
        JSONArray jsonArray = json.getJSONObject("mods").getJSONObject("itemlist").getJSONObject("data").getJSONArray("auctions");
        for(Object obj : jsonArray){
            JSONObject jobj =(JSONObject)obj;
            String storeName = jobj.getString("nick");
            double price = jobj.getDouble("reserve_price");
            String productName = jobj.getString("raw_title");
            String picUrl = jobj.getString("pic_url");
            String productUrl = jobj.getString("detail_url");
            String storeUrl = jobj.getString("shopLink");
            String itemId = jobj.getString("nid");
            Product product = new Product(itemId,productName,productUrl,picUrl,storeName,price,storeUrl);
            productMap.put(itemId,product);
//            String ruleUrl = MessageFormat.format(RULE_URL, itemId);
            page.addTargetRequest(productUrl);
        }

    }
    private void spiderItemData(Page page) {
        String itemId =getItemId(page,regexId);
        String htmlStr = page.getRawText();
        productMap.get(itemId).setHtml(htmlStr);
        String priceUrl = MessageFormat.format(PRICE_URL, itemId);
        page.addTargetRequest(priceUrl);
    }

    private String getItemId(Page page,String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(page.getUrl().toString());
        if(m.find()){
            return m.group(2);
        }else{

        }
        return null;
    }


    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public synchronized void execute(TaskExecution te) {
        logger.info("*** 抓取平台：【淘宝网】，抓取关键字：【" + te.getKeyword() + "】 ***");
        TaskScheduler taskScheduler = te.getTaskScheduler();
        String newUrl = MessageFormat.format(LIST_URL, "0", te.getKeyword(), "20141030");
        Spider.create(this).addUrl(newUrl).thread(10).run();
        logger.info("*** 数据抓取完毕，正在保存中 ***");
        String taskId = taskScheduler.getId();
        Long platFormId = te.getPlatform().getId();
        for (String key : productMap.keySet()) {
            try {
                Product product = productMap.get(key);
                Iterator it = product.getDetails().iterator();
                while (it.hasNext()){
                    ProductDetail productDetail = (ProductDetail) it.next();
                    Product newProduct = new Product(product,productDetail);
                    newProduct.setTaskId(taskId);
//                    newProduct.setTaskExecutionId(taskExecutionId);
                    newProduct.setPlatFormId(platFormId);
                    List<Product> productList = productService.isProductExist(newProduct.getMd5Code());
                    if(productList.size()>0){
                        productService.updateProduct(productList.get(0),newProduct);
                    }else{
                        productService.save(newProduct);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        productMap.clear();
        logger.info("*** 数据保存结束 ***");
    }

    @Override
    public void execute(PlatForm pf,String taskType,Object ... objs) {
        productMap = new HashMap<String, Product>();
        for(Object obj : objs){
            Task task = (Task) obj;
            String newUrl = MessageFormat.format(LIST_URL, "0", URLEncoder.encode(task.getKeyword()), DateFormatUtils.format(new Date(),"yyyyMMdd"));
            Spider.create(this).addUrl(newUrl).thread(20).run();
            logger.info("*** 关键字【"+task.getKeyword()+"】抓取完毕，共抓取："+productMap.size()+"条数据，正在保存中 ***");
            String id = task.getId();
            Long platFormId = pf.getId();
            int saveCount = 0;
            for (String key : productMap.keySet()) {
                try {
                    Product product = productMap.get(key);
                    Iterator it = product.getDetails().iterator();
                    while (it.hasNext()){
                        ProductDetail productDetail = (ProductDetail) it.next();
                        Product newProduct = new Product(product,productDetail);
                        newProduct.setTaskId(id);
                        newProduct.setPlatFormId(platFormId);
                        newProduct.setScenicId(task.getScenicId());
                        newProduct.setLow_price(task.getPrice());
                        newProduct.setTaskType(taskType);
                        productService.save(newProduct);
                        saveCount ++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            logger.info("*** 数据保存结束，保存："+saveCount+"条 ***");
            productMap.clear();
        }
        productMap = null;
    }

}
