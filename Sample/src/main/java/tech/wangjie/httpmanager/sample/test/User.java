package tech.wangjie.httpmanager.sample.test;


/**
 * Created by djonce on 2016/10/8.
 */

//@HttpUri(ApiConfig.USER)
public class User implements Model {

    private String real_name;
    private String nick;
    private int age;
    private String email;

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "real_name='" + real_name + '\'' +
                ", nick='" + nick + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
