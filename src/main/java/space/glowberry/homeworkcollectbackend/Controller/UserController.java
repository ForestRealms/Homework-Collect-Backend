package space.glowberry.homeworkcollectbackend.Controller;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import space.glowberry.homeworkcollectbackend.Entity.RandomTokenGenerator;
import space.glowberry.homeworkcollectbackend.Entity.User;
import space.glowberry.homeworkcollectbackend.Service.TokenService;
import space.glowberry.homeworkcollectbackend.Service.UserService;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    private RandomTokenGenerator tokenGenerator;
    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setTokenGenerator(RandomTokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @Autowired
    private void setUserService(UserService service){
        this.userService = service;
    }
    @GetMapping("/getUsers")
    public List<JSONObject> getUsers() throws IllegalAccessException {
        List<User> users = this.userService.getUsers();
        List<JSONObject> result = new ArrayList<>();
        for (User user : users) {
            JSONObject jsonObject = new JSONObject();
            for (Field field : User.class.getDeclaredFields()) {
                field.setAccessible(true);
                jsonObject.put(field.getName(), field.get(user));
            }
            result.add(jsonObject);
        }
        return result;
    }

    @PostMapping(value = "login", consumes = "application/json")
    public JSONObject login(@RequestBody User user,
                            HttpServletRequest request,
                            HttpServletResponse response){
        JSONObject result = new JSONObject();
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("token") && this.tokenService.isValid(cookie.getValue())){
                    result.put("code", 1);
                    result.put("message", "您已登录，无需重复登录哦");
                    result.put("privileged", this.userService.isPrivileged(user.getUsername()));
                    return result;
                }else {
                    result.put("code", -1);
                    result.put("message", "令牌无效或已过期");
                }
            }
        }

        int code = this.userService.login(user.getUsername(), user.getPassword());
        result.put("code", code);
        result.put("privileged", this.userService.isPrivileged(user.getUsername()));
        switch (code){
            case -1:
                result.put("message", "用户不存在");
                break;
            case 0:
                result.put("message", "用户名或密码不正确");
                break;
            case 1:
                result.put("message", "登录成功");
                String token = this.tokenGenerator.generateToken();
//                Cookie cookie = new Cookie("token", token);
//                cookie.setDomain(""); // 包括子域名
//                cookie.setSecure(false);
//
//                cookie.setPath("/");
//                response.addCookie(cookie);
                ResponseCookie cookie = ResponseCookie.from("token", token)
                        .httpOnly(true)
                        .sameSite("None")
                        .secure(true)
                        .domain("")
                        .maxAge(Duration.ofSeconds(86400L * this.tokenService.getExpiredDays()))
//                        .maxAge(Duration.ZERO)
                        .path("/")
                        .build();
                response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                this.tokenService.registerToken(token, user.getUsername());
                break;
        }
        return result;
    }


    @PostMapping("/logout")
    public JSONObject logout(HttpServletRequest request){
        JSONObject result = new JSONObject();
        for (Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                if (this.tokenService.removeToken(token)) {
                    result.put("code", 1);
                    result.put("message", "退出成功");
                    return result;
                }else {
                    result.put("code", 0);
                    result.put("message", "您已经退出，无需重复退出");
                    return result;
                }

            }
        }
        result.put("code", 0);
        result.put("message", "您已经退出，无需重复退出");
        return result;
    }

    @GetMapping("/user_agreement")
    public JSONObject getUserAgreement(){
        JSONObject agreement = new JSONObject();
        agreement.put("message", "用户协议");
        return agreement;
    };
}
