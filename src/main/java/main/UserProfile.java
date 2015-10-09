package main;

import org.jetbrains.annotations.NotNull;

public class UserProfile {

    @NotNull
    private String email;
    @NotNull
    private String login;
    @NotNull
    private String password;


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
