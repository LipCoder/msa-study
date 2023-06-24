package com.lipcoding.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lipcoding.restfulwebservice.user.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    // User : Post  ->  1      : (0~N)
    // Main : Sub   ->  Parent : Child
    @ManyToOne(fetch = FetchType.LAZY) // 다 대 일
    @JsonIgnore // 결과내용으로 표시하지 않음
    private User user;
}
