package com.lipcoding.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자를 위한 컨트롤러 (for critical)
 */
@RestController
// URL 매핑을 공통으로 묶어서 처리할 수도 있다.
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    // 생성자를 이용한 의존성 주입
    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = service.findAll();

        return filteringV1(users);
    }

    /**
     *  GET /admin/users/1 or /admin/v1/users/10 -> String
     */

    // 1. URI 를 사용하는 방법 (일반 브라우저 실행 가능)
//    @GetMapping("/v1/users/{id}")
    // 2. 쿼리파라미터를 사용하는 방법 (일반 브라우저 실행 가능)
//    @GetMapping(value = "/users/{id}", params = "version=1")
    // 3. 헤더를 사용하는 방법 (일반 브라우저 실행 불가능)
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    // 4. MIME(파일형식 지정방법) 을 사용하는 방법  (일반 브라우저 실행 불가능)
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        User user = service.findOne(id);

        if (Objects.isNull(user)) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return filteringV1(user);
    }

//    @GetMapping("/v2/users/{id}")
//    @GetMapping(value = "/users/{id}", params = "version=2")
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = service.findOne(id);

        if (Objects.isNull(user)) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> User2
        UserV2 userV2 = new UserV2();
        // Spring 에서 제공하는 빈 객체 복사 기능
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        return filteringV2(userV2);
    }

    private MappingJacksonValue filteringV1(Object value) {
        return convert2MappingJacksonValue("UserInfo", value,
            "id", "name", "password", "ssn");
    }

    private MappingJacksonValue filteringV2(Object value) {
        return convert2MappingJacksonValue("UserInfoV2", value,
            "id", "name", "password", "ssn", "grade");
    }

    private MappingJacksonValue convert2MappingJacksonValue(String id, Object value, String... properties) {

        // # Bean Response 정보를 프로그래밍으로 필터링처리를 할 수 있다.
        // 필터 정의
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
            .filterOutAllExcept(properties);
        FilterProvider filters = new SimpleFilterProvider().addFilter(id, filter);

        // 필터 적용
        MappingJacksonValue mapping = new MappingJacksonValue(value);
        mapping.setFilters(filters);

        return mapping;
    }
}
