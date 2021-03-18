package com.application.game.configuration;

import com.application.game.Response.UserToken;
import com.application.game.utils.EncodeUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthorizeInterceptor implements HandlerInterceptor {

    private static final Long VALIDATION_TIME=60*30l;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Method method = null;
        HandlerMethod hm = (HandlerMethod) handler;
        method = hm.getMethod();

        if (method.isAnnotationPresent(Authorize.class))
        {
            String token = request.getHeader("Authorization");
            UserToken userToken = EncodeUtil.getUserTokenByAuthorizationHeader(token);
            Long currentTimeSec=System.currentTimeMillis()/1000;

            if (StringUtils.isEmpty(token))
            {
                throw new Exception("Token not found");
               // return false;
            }
            if (userToken==null)
            {
                throw new Exception("Invalid token");
            }
            else if (currentTimeSec-userToken.getCreatedAt()>VALIDATION_TIME)
            {
                throw new Exception("token expired");
            }
            return true;
        }
        else
        {
            return true;
        }
    }
}