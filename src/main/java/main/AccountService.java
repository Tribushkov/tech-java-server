package main;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by v.chibrikov on 13.09.2014.
 */
public class AccountService {

    @NotNull
    private Map<String, UserProfile> users = new HashMap<>();
    @NotNull
    private Map<String, UserProfile> sessions = new HashMap<>();

    public boolean addUser(@Nullable String userName, UserProfile userProfile) {
        if (users.containsKey(userName))
            return false;
        users.put(userName, userProfile);
        return true;
    }

    public void addSession(String sessionId, @Nullable UserProfile userProfile) {
        sessions.put(sessionId, userProfile);
    }

    @Nullable
    public UserProfile getUser(@Nullable String userName) {
        return users.get(userName);
    }

    @Nullable
    public UserProfile getSession(@Nullable String sessionId) {
        return sessions.get(sessionId);
    }

    public boolean deleteSession(@Nullable String sessionId) {

        if (sessions.get(sessionId) != null) {
            sessions.remove(sessionId);
            return true;
        }

        return false;
    }
    
    @NotNull
    public Map<String, UserProfile> getUsers() {
        return users;
    }

    @NotNull
    public Map<String, UserProfile> getSessions() {
        return sessions;
    }
}
