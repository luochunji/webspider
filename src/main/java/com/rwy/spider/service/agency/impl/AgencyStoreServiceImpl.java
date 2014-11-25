package com.rwy.spider.service.agency.impl;

import com.rwy.spider.bean.agency.AgencyStore;
import com.rwy.spider.service.agency.AgencyStoreService;
import com.rwy.spider.service.base.DaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Luocj on 2014/11/11.
 */
@Service("agencyStoreService")
@Transactional
public class AgencyStoreServiceImpl extends DaoSupport<AgencyStore> implements AgencyStoreService {

}
