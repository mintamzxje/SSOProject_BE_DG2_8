package com.sso.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "component")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_component",
            joinColumns = @JoinColumn(name = "component_uuid"),
            inverseJoinColumns = @JoinColumn(name = "user_uuid"))
    private Set<User> users = new HashSet<>();
}
