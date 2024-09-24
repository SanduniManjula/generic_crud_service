package org.example.filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.response.ApiResponse;
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
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Component
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    protected FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Initialize filter: {}", getClass().getSimpleName());
        this.filterConfig = filterConfig;

        // The web container calls this method to indicate to a filter that it is being placed into service.
        // The container calls this method only once. during the lifecycle of the filter instance.
        // The init() method must complete successfully before the filter is asked to do any filtering work.

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
       HttpServletRequest httpServletRequest = (HttpServletRequest) request;
       HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    /*
       CustomHttpServletResponseWrapper responseWrapper = new CustomHttpServletResponseWrapper(httpServletResponse);

        logRequest(httpServletRequest);
        chain.doFilter(request, responseWrapper);
        logResponse(responseWrapper);

     */

        logRequest(httpServletRequest); //log request
        chain.doFilter(request, response); // continue with the next filter chain
        logResponse(httpServletResponse);// log response details



        //The Web container invokes this method every time whenever the client sends a request or the application sends back a response.
        // It is in this method where you perform operation on the request and response objects.

    }


    @Override
    public void destroy() {
        logger.info("Destroy filters: {}", getClass().getSimpleName());
        //cleanup code
        //The Web container calls this method to indicate to a filter that it is being taken out of service.
        // The container calls this method only once during the lifecycle of the filter instance.
        // This method gives the filter an opportunity to clean up any resources that are being held.
        // For example, memory, file handles, and threads.
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

        if(response instanceof ApiResponse<?>){
            ApiResponse<?> apiResponse = (ApiResponse<?>) response;
            logger.info("Response Details:success{},message{},timestamp={}",
                    apiResponse.isSuccess(),
                    apiResponse.getMessage(),
                    apiResponse.getTimestamp());
        }
    }

 /*
private void logResponse(CustomHttpServletResponseWrapper responseWrapper) throws IOException {
    String responseBody = new String(responseWrapper.getCaptureAsBytes(), StandardCharsets.UTF_8);
    logger.info("Outgoing Response: status={}", responseWrapper.getStatus());


    ObjectMapper objectMapper = new ObjectMapper();
    ApiResponse<?> apiResponse;

    try {
        if (!responseBody.isEmpty()) {
            apiResponse = objectMapper.readValue(responseBody, ApiResponse.class);

            logger.info("Response Details: success={}, message={}, timestamp={}",
                    apiResponse.isSuccess(),
                    apiResponse.getMessage(),
                    apiResponse.getTimestamp());
        } else {
            logger.warn("Response body is empty.");
        }
    } catch (Exception e) {
        logger.error("Error parsing response body: {}", e.getMessage());
    }


    PrintWriter out = responseWrapper.getResponse().getWriter();
    out.write(responseBody);
    out.flush();
}

    private class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {

        private ByteArrayOutputStream capture;
        private PrintWriter writer;

        public CustomHttpServletResponseWrapper(HttpServletResponse response) {
            super(response);
            capture = new ByteArrayOutputStream();
            writer = new PrintWriter(capture);
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

        public byte[] getCaptureAsBytes() {
            writer.flush();
            return capture.toByteArray();
        }

    }
  */
}

