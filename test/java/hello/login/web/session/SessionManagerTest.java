package hello.login.web.session;

import hello.login.domain.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest(){

        MockHttpServletResponse  res= new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, res);

        MockHttpServletRequest req= new MockHttpServletRequest();
        req.setCookies(res.getCookies());

        Object result = sessionManager.getSession(req);
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(member);

        sessionManager.expire(req);
        Object session = sessionManager.getSession(req);
        org.assertj.core.api.Assertions.assertThat(session).isNull();


    }
}