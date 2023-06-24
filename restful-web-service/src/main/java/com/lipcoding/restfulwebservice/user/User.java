package com.lipcoding.restfulwebservice.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 1. Jackson 라이브러리의 기능을 이용하여 특정 필드를 필터링한다. (패스워드, 주민번호)
//@JsonIgnoreProperties(value={"password", "ssn"})
// 2. 1번과 비슷하되 프로그래밍으로 필터링을 제어한다.
//@JsonFilter("UserInfo")
// Swagger 설정
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
public class User {
    public User(Integer id, String name, Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }

    @Id
    @GeneratedValue
    private Integer id;

    // Hibernate Validation 기능 사용
    @Size(min = 2, message = "Name 은 2 글자 이상 입력해주세요.")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    private String name;
    @Past
    @ApiModelProperty(notes = "사용자의 등록일을 입력해 주세요.")
    private Date joinDate;

    // Filtering 이 필요한 중요 필드
    // (보안상의 이슈로 다르게 처리가 필요하다)
    @ApiModelProperty(notes = "사용자의 패스워드를 입력해 주세요.")
    private String password;
    @ApiModelProperty(notes = "사용자의 주민번호를 입력해 주세요.")
    private String ssn; // 주민번호

    @OneToMany(mappedBy = "user") // 일 대 다
    private List<Post> posts;

}
