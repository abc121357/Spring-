package spring.user.service;

import spring.user.dao.UserDao;
import spring.user.domain.Level;
import spring.user.domain.User;

import java.util.List;

public class UserService {

    UserDao userDao;

    public void setUserDao(UserDao userDao){
        this.userDao= userDao;
    }

    public void upgradeLevels(){
        List<User> users=userDao.getAll();
        for(User user : users){
            Boolean changed = null;
            if(user.getLevel() == Level.BASIC && user.getLogin()>=50){
                user.setLevel(Level.SILVER);
                changed = true;
            }

            else if(user.getLevel() == Level.SILVER && user.getRecommend()>=30){
                user.setLevel(Level.GOLD);
                changed =true;

            }else if(user.getLevel()==Level.GOLD){
                changed=false;

            }

            else{
                changed=false;

            }

            if(changed){
                userDao.update(user);
            }
        }
    }

}
