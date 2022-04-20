package com.tsh.navigation.state;

public class AuthState extends State {

    public static final String NAME = "_state_auth";
    public String username;
    public String password;

    @Override
    String stringify() {
        return null;
    }
}
