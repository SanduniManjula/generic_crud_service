package org.example.filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    protected FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Initialize filter: {}", getClass().getSimpleName());
        this.filterConfig = filterConfig;

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;


        logRequest(httpServletRequest); //log request
        chain.doFilter(request, response); // continue with the next filter chain
        logResponse(httpServletResponse);// log response details
    }


    @Override
    public void destroy() {
        logger.info("Destroy filter: {}", getClass().getSimpleName());
        //cleanup code
    }

    private void logRequest(HttpServletRequest request) {
        logger.info("Incoming Request: method={}, URI={}, queryParams={}, remoteAddress={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                request.getRemoteAddr());
    }

    private void logResponse(HttpServletResponse response) {
        logger.info("Outgoing Response: status={}", response.getStatus());
    }
}

