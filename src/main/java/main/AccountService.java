package main;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import base.DBService;
import base.dataSets.UserDataSet;
import dbService.DBServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    DBService dbService = new DBServiceImpl();

    @NotNull
    private final Map<String, UserDataSet> users = new HashMap<>();

    @NotNull
    private final Map<String, UserDataSet> sessions = new HashMap<>();


    public void addUser(@Nullable String name, UserDataSet profile) {
        /*
        if (!users.containsKey(name))
            users.put(name, profile);
        */
        dbService.save(profile);
    }

    public void addSession(@Nullable String id, UserDataSet user) {
        sessions.put(id, user);
    }

    @Nullable
    public UserDataSet getUser(String email) {
        return dbService.readByName(email);
    }

    @Nullable
    public UserDataSet getSession(@Nullable String id) {
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
    public Map<String, UserDataSet> getUsers() {
        return users;
    }

    @NotNull
    public Map<String, UserDataSet> getSessions() {
        return sessions;
    }

}
