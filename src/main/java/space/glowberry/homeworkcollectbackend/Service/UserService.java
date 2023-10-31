package space.glowberry.homeworkcollectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.glowberry.homeworkcollectbackend.DataAccess.BasicOperationExecutor;
import space.glowberry.homeworkcollectbackend.DataAccess.UserDataAccess;
import space.glowberry.homeworkcollectbackend.Entity.User;

import java.util.List;

@Service
public class UserService {
    private BasicOperationExecutor basicOperationExecutor;
    private UserDataAccess userDataAccess;

    private User user;

    @Autowired
    public void setBasicOperationExecutor(BasicOperationExecutor basicOperationExecutor) {
        this.basicOperationExecutor = basicOperationExecutor;
    }

    @Autowired
    public void setUserDataAccess(UserDataAccess access){
        this.userDataAccess = access;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers(){
        return this.userDataAccess.getUsers();
    }

    public int login(String username, String password){
        if (this.userDataAccess.getUser(username) == null) {
            return -1;
        } else if (!this.userDataAccess.getUser(username).getPassword().equals(password)) {
            return 0;
        }else {
            return 1;
        }
    }

    public boolean isPrivileged(String username){
        if (this.userDataAccess.getUser(username) == null) {
            return false;
        }else {
            return this.userDataAccess.getUser(username).isPrivileged();
        }
    }
}
