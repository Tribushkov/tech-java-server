package main;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountServiceTest {
    @NotNull
    private final AccountService accountService = new AccountService();
    @NotNull
    private final UserProfile testUser = new UserProfile("test@test.test", "testLogin", "testPassword");

    @Test
    public void testAddUser() throws Exception {
        accountService.addUser(testUser.getLogin(), testUser);
        final UserProfile user = accountService.getUser(testUser.getLogin());
        assertNotNull(user);
        assertEquals(testUser.getLogin(), user.getLogin());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getPassword(), user.getPassword());
    }

    @Test
    public void testAddSession() throws Exception {
        String sessionId = "testId";
        accountService.addSession(sessionId, testUser);
        final UserProfile user = accountService.getSession(sessionId);
        assertNotNull(user);
        assertEquals(testUser.getLogin(), user.getLogin());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getPassword(), user.getPassword());
    }

    @Test
    public void testGetUser() throws Exception {

    }

    @Test
    public void testGetSession() throws Exception {

    }

    @Test
    public void testDeleteSession() throws Exception {

    }

    @Test
    public void testGetUsers() throws Exception {

    }

    @Test
    public void testGetSessions() throws Exception {

    }
}