package springboot.Model.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

//
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    private Long id;
    private String username;
    private String email;
    private String fullname;
    private Date birthday;
    private Date lastLoginDate;
    private Date lockoutDate;

    @JsonIgnore
    private String password;
    private int loginFailedCount;
    private String roleName;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String email, String fullname, Date birthday, Date lastLoginDate, Date lockoutDate, String password, int loginFailedCount, String roleName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.birthday = birthday;
        this.lastLoginDate = lastLoginDate;
        this.lockoutDate = lockoutDate;
        this.password = password;
        this.loginFailedCount = loginFailedCount;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLockoutDate() {
        return lockoutDate;
    }

    public void setLockoutDate(Date lockoutDate) {
        this.lockoutDate = lockoutDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLoginFailedCount() {
        return loginFailedCount;
    }

    public void setLoginFailedCount(int loginFailedCount) {
        this.loginFailedCount = loginFailedCount;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
