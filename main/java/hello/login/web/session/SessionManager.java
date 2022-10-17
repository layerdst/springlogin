package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse res){
        //세션 id 생성
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        //쿠키생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        res.addCookie(mySessionCookie);
    }

    public Object getSession(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
        if(sessionCookie ==null){
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());

//        if(cookies==null){
//            return null;
//        }
//
//        for (Cookie cookie : cookies) {
//            if(cookie.getName().equals(SESSION_COOKIE_NAME)){
//                return sessionStore.get(cookie.getValue());
//            }
//        }
//        return null;
    }

    public Cookie findCookie(HttpServletRequest req, String cookieName){
        Cookie[] cookies = req.getCookies();
        if(cookies == null){
            return  null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

    public void expire(HttpServletRequest req){
        Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
            if(sessionCookie != null){
                sessionStore.remove(sessionCookie.getValue());
            }

    }
}
