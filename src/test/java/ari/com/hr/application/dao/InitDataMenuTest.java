/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ari-prasetiyo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(
           // executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = "/DataMenu.sql"
    )
public class InitDataMenuTest {

    @Test
    public void test() {
        System.out.println("=======================================================");
    }
   
}
