public class User {
    private String username;
    private String pwd;

    public User(String username, String pwd) {
        this.pwd = pwd;
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public String getUsername() {
        return username;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
