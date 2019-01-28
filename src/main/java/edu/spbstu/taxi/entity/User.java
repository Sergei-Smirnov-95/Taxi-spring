package edu.spbstu.taxi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "user")
@DiscriminatorColumn(name="TypeUser",
        discriminatorType = DiscriminatorType.INTEGER)
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String login;
    private String pwd;
    private String name;
    private String email;
    private String phone;
    private boolean authenticated;
    @Column(name="TypeUser",insertable=false, updatable=false)
    private int TypeUser;

    public User() {
    }

    public User(int id_, String login_, String pwd_, String name_,
                String email_, String phone_, boolean authenticated_, int TypeUser_) {
        id = id_;
        login = login_;
        name = name_;
        email = email_;
        phone = phone_;
        pwd = pwd_;
        authenticated = authenticated_;
        TypeUser = TypeUser_;
    }
    public boolean loginUser(String pwd) {
        if (this != null) {
            String originpwd = this.getPwd();
            if (originpwd.equals(pwd)) {
                this.setAuthenticated(true);
                return true;
            }
            return false;
        }
        return false;
    }

}
