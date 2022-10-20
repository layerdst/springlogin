package hello.login.web.filter;

import hello.login.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {"/", "/members/add", "/login", "/css/*","/logout"};


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try{
            log.info("인증체크필터시작 {}", requestURI);
            log.info("로그인체크 {}", isLoginCheckPath(requestURI));
            if(isLoginCheckPath(requestURI)){
                log.info("인증체크로직실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if(session==null||session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
                    log.info("미인증사용자요청 {}", requestURI);

                    //로그인 페이지 이동후 현재 페이지로 이동
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }

            }
            chain.doFilter(request, response);
        }catch (Exception e){
            throw e;
        }finally {
            log.info("인증 체크 필터 종료 {} ", requestURI);
        }

    }


    /**
     * 화이트 체크의 경우 인증체크 X
     */

    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
