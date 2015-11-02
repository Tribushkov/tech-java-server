package main;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AccountServiceTest {

    @NotNull
    private final AccountService accountService = new AccountService();
    @NotNull
    private final UserProfile testUser = new UserProfile("test@test.test", "testLogin", "testPassword");

    @Test
    public void testAddUser() throws Exception {
        accountService.addUser(testUser.getEmail(), testUser);
        final UserProfile user = accountService.getUser(testUser.getEmail());
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
        accountService.addUser(testUser.getEmail(), testUser);
        final UserProfile user = accountService.getUser(testUser.getEmail());
        assertNotNull(user);
        assertEquals(testUser.getLogin(), user.getLogin());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getPassword(), user.getPassword());
    }

    @Test
    public void testGetSession() throws Exception {
        String sessionId = "testId";
        accountService.addSession(sessionId, testUser);
        final UserProfile user = accountService.getSession(sessionId);
        assertNotNull(user);
        assertEquals(testUser.getLogin(), user.getLogin());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getPassword(), user.getPassword());
    }

    @Test
    public void testDeleteSession() throws Exception {
        String sessionId = "testId";
        accountService.addSession(sessionId, testUser);
        accountService.deleteSession(sessionId);
        final UserProfile user = accountService.getSession(sessionId);
        assertNull(user);
    }

    @Test
    public void testGetUsers() throws Exception {
        Map<String, UserProfile> testUsers = new HashMap<>();
        testUsers.put(testUser.getEmail(), testUser);

        accountService.addUser(testUser.getEmail(), testUser);

        assertEquals(testUsers, accountService.getUsers());
    }

    @Test
    public void testGetSessions() throws Exception {
        Map<String, UserProfile> testSessions = new HashMap<>();
        String sessionId = "testId";
        testSessions.put(sessionId,testUser);

        accountService.addSession(sessionId, testUser);

        assertEquals(testSessions, accountService.getSessions());
    }
}