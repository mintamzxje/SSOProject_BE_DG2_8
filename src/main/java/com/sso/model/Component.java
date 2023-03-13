package com.sso.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "component")
public class Component {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;
    @Column
    @NotBlank
    private String name;
    @Column
    @NotBlank
    private String code;
    @Column
    @NotBlank
    private String icon;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_component",
            joinColumns = @JoinColumn(name = "component_uuid"),
            inverseJoinColumns = @JoinColumn(name = "user_uuid"))
    private Set<User> users = new HashSet<>();*/

    public Component(){}

    public Component(String name, String code, String icon/*, Set<User> users*/) {
        this.name = name;
        this.code = code;
        this.icon = icon;
        /*this.users = users;*/
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /*public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }*/
}
