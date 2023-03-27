package com.sso.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "component")
@Getter
@Setter
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
    private String icon;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_component",
            joinColumns = @JoinColumn(name = "component_uuid"),
            inverseJoinColumns = @JoinColumn(name = "user_uuid"))
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}
