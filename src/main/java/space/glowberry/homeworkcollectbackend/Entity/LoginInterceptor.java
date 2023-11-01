package space.glowberry.homeworkcollectbackend.Entity;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import space.glowberry.homeworkcollectbackend.Service.TokenService;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                return cookie.getName().equals("token") && tokenService.isValid(cookie.getValue());
            }
        }

        return false;
    }
}
