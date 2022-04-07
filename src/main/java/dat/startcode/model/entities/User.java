package dat.startcode.model.entities;

import java.util.Objects;

public class User
{
    private int userId;
    private int roleId;
    private String username;
    private String password;
    private String email;
    private int balance;


    public User(int userId, int roleId, String username, String password, String email, int balance)
    {
        this.userId = userId;
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = balance;
    }

    public User(String username, String password, int roleId)
    {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }


    @Override
    public String toString()
    {
        return "User{" +
                "userId=" + userId +
                ", role='" + roleId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String role)
    {
        this.roleId = roleId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getBalance()
    {
        return balance;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && roleId == user.roleId && balance == user.balance && username.equals(user.username) && password.equals(user.password) && email.equals(user.email);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getUsername(), getPassword(), getRoleId());
    }
}
