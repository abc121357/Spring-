package spring.user.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.user.domain.Level;
import spring.user.domain.User;

import java.util.Arrays;
import java.util.List;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;
    List<User> users;

    @Before
    public void setUp(){
        users = Arrays.asList(
            new User("humi","humika","p1", Level.BASIC,49,0),
            new User("peko","aria","p2",Level.BASIC,50,0),
            new User("usagi","nodoka","p3",Level.SILVER,60,29)
        );
    }
    @Test
    public void upgradeLevels(){
        userDao.deleteAll();

    }



}
