package app.springboot.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "role_id", unique = true)
    private Long roleId;

    @Column(name = "role_name", unique = true)
    private String roleName;

//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;

    public Role() {
    }

    public Role(Long roleId,String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }

    @Override
    public String toString() {
        return roleName;
    }
}