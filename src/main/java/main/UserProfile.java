package main;

import com.sun.istack.internal.NotNull;

public class UserProfile {

    @NotNull
    private final String email;
    @NotNull
    private final String login;
    @NotNull
    private final String password;


    public UserProfile(@NotNull String email, @NotNull String login, @NotNull String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    @NotNull
    public String getLogin() {
        return login;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

}
