package com.gulimall.search.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xiaoning
 * @date 2023/03/02
 */
@Data
@AllArgsConstructor
public class User {

    private Integer id;

    private String name;

    private Integer age;

}
