package main;

import org.jetbrains.annotations.Nullable;

/**
 * Created by v.chibrikov on 13.09.2014.
 */
public class UserProfile {
    private String login;
    private String password;
    private String email;

    public UserProfile(@Nullable String login, @Nullable String password, @Nullable String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
