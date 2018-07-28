package com.jxshen.redis.test.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author jxshen on 2018/07/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity  {

    private Long id;
    private String name;
    private Date birth;
}
