/*
 *  Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.seata.samples.dubbo.starter;

import io.seata.samples.dubbo.ApplicationKeeper;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * The type Dubbo account service starter.
 */
public class DubboAllServiceStarter {
    /**
     * 2. Account service is ready . A buyer register an account: U100001 on my e-commerce platform
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-all-service.xml"});
//        context.getBean("service");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
       jdbcTemplate.update("delete from account_tbl where user_id = 'U100001'");
       jdbcTemplate.update("insert into account_tbl(user_id, money) values ('U100001', 999)");
       jdbcTemplate.update("delete from storage_tbl where commodity_code = 'C00321'");
       jdbcTemplate.update("insert into storage_tbl(commodity_code, count) values ('C00321', 100)");

        String sql = "select money from account_tbl where user_id=? ";
        String remind = jdbcTemplate.queryForObject(sql, String.class, "U100001");

        new ApplicationKeeper(context).keep();
    }
}
