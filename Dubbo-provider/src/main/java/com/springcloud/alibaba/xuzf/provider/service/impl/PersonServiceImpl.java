package com.springcloud.alibaba.xuzf.provider.service.impl;

import com.springcloud.alibaba.service.Person;
import com.springcloud.alibaba.service.PersonService;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2019/11/27
 * Time: 8:17 AM
 * Description: No Description
 */
public class PersonServiceImpl implements PersonService {
    @Override
    public Person get( int id ) {
        return new Person(id, "xuzf", 4);
    }
}
