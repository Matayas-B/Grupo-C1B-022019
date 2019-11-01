package backend.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoggingService {

    private static final Logger logger = LogManager.getLogger(LoggingService.class);

    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        StringBuilder string = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);

        string.append("REQUEST: ").append(httpServletRequest.getAttribute("request-id")).append("| ");
        string.append("start-execution-time=[").append(httpServletRequest.getAttribute("start-time")).append("] ");
        string.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        string.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        string.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");

        if (!parameters.isEmpty()) {
            string.append("parameters=[").append(parameters).append("] ");
        }

        if (body != null) {
            string.append("body=[").append(body.toString()).append("]");
        }

        logger.info(string.toString());
    }

    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body, String requestId) {
        long executionStartTime = (Long) httpServletRequest.getAttribute("start-time");
        long renderingStartTime = System.currentTimeMillis();
        long executionDuration = renderingStartTime - executionStartTime;

        StringBuilder string = new StringBuilder();
        string.append("RESPONSE: ").append(requestId).append("| ");
        string.append("end-execution-time=[").append(executionDuration).append("] ");
        string.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        string.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        string.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ");
        string.append("responseBody=[").append(body).append("] ");

        logger.info(string);
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }
}
