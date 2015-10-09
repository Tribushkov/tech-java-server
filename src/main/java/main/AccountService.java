package main;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    @NotNull
    private final Map<String, UserProfile> users = new HashMap<>();
    @NotNull
    private final Map<String, UserProfile> sessions = new HashMap<>();


    public void addUser(@Nullable String name, UserProfile profile) {
        if (!users.containsKey(name))
            users.put(name, profile);
    }

    public void addSession(@Nullable String id, @Nullable UserProfile user) {
        sessions.put(id, user);
    }

    @Nullable
    public UserProfile getUser(@Nullable String name) {
        return users.get(name);
    }

    @Nullable
    public UserProfile getSession(@Nullable String id) {
        return sessions.get(id);
    }

    public boolean deleteSession(@Nullable String id) {
        if (sessions.containsKey(id)) {
            sessions.remove(id);
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
