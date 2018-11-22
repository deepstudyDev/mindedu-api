package com.mindedu.api.filter;

import com.mindedu.api.define.error.MindErrCode;
import com.mindedu.api.define.error.MindException;
import com.mindedu.api.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *		1. Comment : 유저 세션 정보 필터링
 *		2. 작성자 : 안지호
 *		3. 작성일 : 2018. 06. 19
 * </pre>
 */
public class UserTokenFilter extends DelegatingFilterProxy {

    static final Logger logger = LoggerFactory.getLogger(UserTokenFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        try {
            HttpServletRequest req = ((HttpServletRequest) request);
            if (MindEduSecurity.isSecure(req)) {
                Cookie[] cookies = req.getCookies();
                if (cookies == null || cookies.length == 0) {
                    throw new MindException(MindErrCode.NOT_AUTHENTICATE, "Required for auth token. Unauthorized!");
                }
                String token = "";
                for (Cookie ck : cookies) {
                    String ckName = ck.getName();
                    if (ckName.equals("JSESSIONID")) {
                        token = ck.getValue();
                        break;
                    }
                }
                AuthService authService = findWebApplicationContext().getBean(AuthService.class);
                boolean isAuth = authService.isCompanyApiAuth(token);
                if (!isAuth) {
                    String msg = "Unauthorized :: not found user account, token >> " + token;
                    throw new MindException(MindErrCode.NOT_AUTHENTICATE, msg);
                }
            }
            chain.doFilter(request, response);
        } catch(Exception e) {
            logger.error("Request URI >> " + ((HttpServletRequest) request).getRequestURI());
            logger.error(e.getMessage(), e);
            ((HttpServletResponse)response).sendError(500);
        }
    }
}
