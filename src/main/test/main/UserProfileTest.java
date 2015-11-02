package main;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 02.11.15.
 */
public class UserProfileTest {

    private UserProfile user = new UserProfile("email", "lalka", "password");

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("email", user.getEmail());
    }

    @Test
    public void testGetLogin() throws Exception {
        assertEquals("lalka", user.getLogin());
    }

    @Test
    public void testGetPassword() throws Exception {
        assertEquals("password", user.getPassword());
    }
}