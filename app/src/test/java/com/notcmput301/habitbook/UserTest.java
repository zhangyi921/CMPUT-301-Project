package com.notcmput301.habitbook;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by shang on 10/21/2017.
 */
public class UserTest {

    @Test
    public void getUsername() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        String expected = "test1";
        assertEquals("Failed to retrieve user name", expected, u1.getUsername());
    }

    @Test
    public void setUsername() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        u1.setUsername("new username");
        String expected = "new username";
        String actual = u1.getUsername();   //dependent on the success of getUsername();
        assertEquals("Failed to set Username", expected, actual);
    }

    @Test
    public void getPassword() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        String expected = "carrot42";
        assertEquals("Failed to retrieve correct password", expected, u1.getPassword());
    }

    @Test
    public void setPassword() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        u1.setPassword("new password");
        String expected = "new password";
        assertEquals("Failed to set correct password", expected, u1.getPassword()); //relies on success of getPassword
    }

    @Test
    public void getCreationDate() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        Date expected = new Date();
        assertEquals("Failed to get correct creation date", expected, u1.getCreationDate());
    }

    @Test
    public void setCreationDate() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        Date expected = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(expected);
        cal.add(Calendar.YEAR, -5);
        expected = cal.getTime();
        u1.setCreationDate(expected);
        assertEquals("Failed to set proper date", expected, u1.getCreationDate());                  //depends on the success of getCreationDate()
    }

    @Test
    public void getFollowers() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        ArrayList<User> expected = new ArrayList<User>();
        assertEquals("Failed get proper followers", expected, u1.getFollowers());
    }

    @Test
    public void setFollowers() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        User f1 = new User("follower1", "carrot1"); //Test object
        User f2 = new User("follower2", "carrot99"); //Test object
        User f3 = new User("follower3", "carrot420"); //Test object
        ArrayList<User> expectedF = new ArrayList<User>();
        expectedF.add(f1); expectedF.add(f2); expectedF.add(f3);
        u1.setFollowers(expectedF);
        assertEquals("Failed to set correct followers", expectedF, u1.getFollowers());              //depends on the cussess of getFollowers()
    }

    @Test
    public void getFollowedUsers() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        ArrayList<User> expected = new ArrayList<User>();
        assertEquals("Failed get proper followed users", expected, u1.getFollowers());
    }

    @Test
    public void setFollowedUsers() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        User f1 = new User("follower1", "carrot1"); //Test object
        User f2 = new User("follower2", "carrot99"); //Test object
        User f3 = new User("follower3", "carrot420"); //Test object
        ArrayList<User> expectedFd = new ArrayList<User>();
        expectedFd.add(f1); expectedFd.add(f2); expectedFd.add(f3);
        u1.setFollowedUsers(expectedFd);
        assertEquals("failed to set followed users", expectedFd, u1.getFollowedUsers());            //dependson the cussess of getFollowedUser()
    }

    @Test
    public void getHabitTypes() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        ArrayList<HabitType> expected = new ArrayList<HabitType>();
        assertEquals("Failed to retrieve habit types", expected, u1.getHabitTypes());
    }

    @Test
    public void setHabitTypes() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        HabitType h1 = new HabitType(u1.getUsername(), "eating less", "coming close to morbid obesity", new Date(), new ArrayList<Integer>());                                                             //depends on the success of habit type
        HabitType h2 = new HabitType(u1.getUsername(), "watch less utube", "failing 301", new Date(), new ArrayList<Integer>());
        HabitType h3 = new HabitType(u1.getUsername(), "be less lazy", "failing 301", new Date(), new ArrayList<Integer>());
        ArrayList<HabitType> expectedH = new ArrayList<HabitType>();
        expectedH.add(h1); expectedH.add(h2); expectedH.add(h3);
        u1.setHabitTypes(expectedH);
        assertEquals("failed to retrieve correct habit types", expectedH, u1.getHabitTypes());      //depends on the success of get habit types
    }

    @Test
    public void addHabitType() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        HabitType expectedH = new HabitType(u1.getUsername(), "eating less", "coming close to morbid obesity",
                                            new Date(), new ArrayList<Integer>());
        u1.addHabitType(expectedH);
        assertEquals("Failed to add Habit", true, u1.getHabitTypes().contains(expectedH));          //depends on success of getHabitTypes
    }

    @Test
    public void removeHabitType() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        HabitType h1 = new HabitType(u1.getUsername(), "eating less", "coming close to morbid obesity",
                                    new Date(), new ArrayList<Integer>());
        HabitType h2 = new HabitType(u1.getUsername(), "watch less utube", "failing 301", new Date(),
                                    new ArrayList<Integer>());
        u1.addHabitType(h1); u1.addHabitType(h2);
        u1.removeHabitType(h1);
        assertEquals("Failed to remove habit", false, u1.getHabitTypes().contains(h1));
    }

    @Test
    public void addFollower() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        User f1 = new User("follower1", "carrot1"); //Test object
        u1.addFollower(f1);
        assertEquals("Failed to addFollower", true, u1.getFollowers().contains(f1));                //depends on the success of getFollowers
    }

    @Test
    public void removeFollower() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        User f1 = new User("follower1", "carrot1"); //Test object
        User f2 = new User("follower2", "carrot99"); //Test object
        u1.addFollower(f1); u1.addFollower(f2);                                                     //depends on the success of add follower
        u1.removeFollower(f1);
        assertEquals("failed to remove follower", false, u1.getFollowers().contains(f1));           //depends on the success of getFollowers
    }

    @Test
    public void addFollowedUser() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        User fd1 = new User("followed1", "carrot1"); //Test object
        u1.addFollowedUser(fd1);
        assertEquals("Failed to add FollowedUser", true, u1.getFollowedUsers().contains(fd1));      //depends on the success opf getFollowedUsers
    }

    @Test
    public void removeFollowedUser() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        User fd1 = new User("followed1", "carrot1"); //Test object
        User fd2 = new User("followed2", "carrot99"); //Test object
        u1.addFollowedUser(fd1); u1.addFollowedUser(fd2);
        u1.removeFollowedUser(fd1);
        assertEquals("Failed to removeFollowedUser", false, u1.getFollowedUsers().contains(fd1));
    }

    @Test
    public void isActiveUser() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
    }

    @Test
    public void equals() throws Exception {
        User u1 = new User("test1", "carrot42"); //Test object
        User u2 = new User("test1", "carrot42"); //test object
        assertEquals("Failed equal comparison", true, u1.equals(u2));
    }

//    @Test
//    public void toString() throws Exception {
//
//    }

}