package hello.login.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Member {

    public Long id;

    @NotEmpty
    public String loginId;

    @NotEmpty
    public String name;

    @NotEmpty
    public String password;

}
