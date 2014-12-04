package com.rwy.spider.constant;

import com.rwy.spider.bean.system.SystemParams;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Luocj on 2014/10/28.
 */
public class Constant {

    public static final Map<String, String> status = new HashMap<String, String>();

    public static final Map<String, String> TAOBAO_PARAM = new HashMap<String, String>();

    public static final Map<String, String> TAOBAO_VALID_PARAM = new HashMap<String, String>();

    public static Map PLATFORM_MAP = new HashMap();

    public static final Map<String, String> TAOBAO_VALID_MAP = new LinkedHashMap<String, String>();

    public static Map<String, SystemParams> SYSTEM_PARAMS = new HashMap();

    static {
        //门票类别 成人票、儿童票
        TAOBAO_PARAM.put("123390659", "category");
        TAOBAO_PARAM.put("10682468", "category");
        //门票种类 实体票、电子票
        TAOBAO_PARAM.put("6180395", "type");

        status.put("ACQUIRED", "运行中");
        status.put("PAUSED", "暂停中");
        status.put("WAITING", "等待中");

        TAOBAO_VALID_PARAM.put("[1,2,3,4]", "NORMAL");
        TAOBAO_VALID_PARAM.put("[5,6,7]", "WEEKEND");

        TAOBAO_VALID_MAP.put("", "所有");
        TAOBAO_VALID_MAP.put("NORMAL", "平日票");
        TAOBAO_VALID_MAP.put("WEEKEND", "周末票");
        TAOBAO_VALID_MAP.put("OTHER", "其他");
    }

    //    public static Integer TEMP_DATA_CLEAR = Integer.valueOf(SYSTEM_PARAMS.get("TEMP_DATA_CLEAR").getParamValue());
    public static Integer TEMP_DATA_CLEAR = 0;

    public static Integer HISTORY_DATA_CLEAR = 0;

    public static String EMAIL = "";

}
