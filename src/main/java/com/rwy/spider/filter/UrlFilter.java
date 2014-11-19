package com.rwy.spider.filter;

import com.rwy.spider.utils.WebUtil;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Luocj on 2014/11/18.
 */
public class UrlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
            String url = WebUtil.getRequestURIWithParam(request);//得到当前请求路径
            String directUrl = new String(Base64.encodeBase64(url.getBytes()));
            HttpServletResponse response = (HttpServletResponse)res;
            response.sendRedirect(request.getRequestURI()+"?directUrl="+ directUrl);
            chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
