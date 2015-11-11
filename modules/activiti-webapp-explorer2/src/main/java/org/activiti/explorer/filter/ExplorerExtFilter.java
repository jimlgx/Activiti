/*
 * @project activiti-root
 * @package org.activiti.explorer.filter
 * @file ExplorerExtFilter.java
 * @version  1.0
 * @author  junming.wang
 * @time 2015-05-30
 */
package org.activiti.explorer.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>ExplorerExtFilter</code>
 *
 * @author junming.wang
 * @version 1.0
 * @since 1.0 2015-05-30
 */
public class ExplorerExtFilter extends ExplorerFilter {
    private static final Logger LOG = LoggerFactory
            .getLogger(ExplorerExtFilter.class);
    public static final String EXPLORER2 = "/explorer2";

    private List<String> ignoreExtList = new ArrayList<String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        ignoreExtList.add("/ui");
        ignoreExtList.add("/VAADIN");
        ignoreExtList.add("/modeler.html");
        ignoreExtList.add("/editor-app");
        ignoreExtList.add("/service");
        ignoreExtList.add("/diagram-viewer");
        ignoreExtList.add("/druid");
        ignoreExtList.add("/H2Console");

    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        String path = req.getRequestURI().substring(req.getContextPath().length());
//        int indexSlash = path.indexOf("/", 1);
//        String firstPart = null;
//        if (indexSlash > 0) {
//            firstPart = path.substring(0, indexSlash);
//        } else {
//            firstPart = path;
//        }
//        if (isMatch(firstPart)) {
//            chain.doFilter(request, response); // Goes to default servlet.
//        } else {
//            superDoFilter(request, response, chain);
//        }
        superDoFilter(request,response,chain);
    }

//    private boolean isMatch(String firstPart) {
//
//        return ignoreExtList.contains(firstPart);
//    }
    public void superDoFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        String firstPart = StringUtils.substringBefore(path,"/");
        if (ignoreExtList.contains(firstPart)) {
            chain.doFilter(request, response); // Goes to default servlet.
        } else if(!EXPLORER2.equals(path)&&StringUtils.startsWith(path, EXPLORER2)) {
            String path2 = StringUtils.removeStart(path, EXPLORER2);
            String path３ = StringUtils.substringBefore(path2,"/");
            ignoreExtList.contains(path３);
            request.getRequestDispatcher(path３).forward(request, response);
        } else {
            request.getRequestDispatcher("/ui" + path).forward(request, response);
        }
    }


}
