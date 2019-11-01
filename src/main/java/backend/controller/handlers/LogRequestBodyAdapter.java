package backend.controller.handlers;

import backend.service.LoggingService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.UUID;

@ControllerAdvice
public class LogRequestBodyAdapter extends RequestBodyAdviceAdapter {

    @Autowired
    LoggingService loggingService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        long executionStartTime = System.currentTimeMillis();
        httpServletRequest.setAttribute("start-time", executionStartTime);
        String requestId = UUID.randomUUID().toString();
        httpServletRequest.setAttribute("request-id", requestId);

        loggingService.logRequest(httpServletRequest, body);

        ThreadContext.put("id", requestId);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
