package domain;

public class User {

    private String username;
    private String password;
    private String encrypted_password;

    public User() {
    }

    public User(String _username, String _password) {
        setUsername(_username);
        setPassword(_password);
    }

    public void setUsername(String _username) {
        username = _username;
    }

    public void setPassword(String _password) {
        password = _password;
    }

}
