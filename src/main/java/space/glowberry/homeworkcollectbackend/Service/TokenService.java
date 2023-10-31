package space.glowberry.homeworkcollectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import space.glowberry.homeworkcollectbackend.DataAccess.TokenDataAccess;
import space.glowberry.homeworkcollectbackend.Entity.Token;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    private TokenDataAccess tokenDataAccess;

    @Value("${settings.cookie_cycle_days}")
    private int days;

    @Autowired
    public void setTokenDataAccess(TokenDataAccess tokenDataAccess) {
        this.tokenDataAccess = tokenDataAccess;
    }


    public boolean isValid(String token){
        Token token1 = this.tokenDataAccess.getByTokenValue(token);
        if (token1 == null) {
            return false;
        }
        return !token1.getExpired().before(new Date());
    }

    public void registerToken(String value, String username){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        this.tokenDataAccess.registerToken(value,
                calendar.getTime(), username);
    }

    public boolean removeToken(String value){
        return this.tokenDataAccess.removeToken(value) > 0;
    }

    /**
     * 通过令牌值反查其所有者
     * @param tokenValue 令牌值
     * @return 所有者的用户名，如果不存在，则返回<code>null</code>
     */
    @Nullable
    public String getOwner(String tokenValue){
        Token token = this.tokenDataAccess.getByTokenValue(tokenValue);
        if (token == null) {
            return null;
        }else {
            return token.getOwner();
        }
    }

    public int getExpiredDays() {
        return days;
    }
}
