package com.lc.web

import geb.junit4.GebReportingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.Keys

/**
 * @author DELL
 * @date 2022/7/7 10:05
 * @description
 */
@RunWith(JUnit4)
class ToDoTest extends GebReportingTest {
    @Test
    void theToDoHomepage() {
        to ToDoHomepage

        $("form").name = "和欧阳晗艺约会（单元测试）"
        $("form").name << Keys.ENTER

        waitFor { at ToDoInsert }
    }
}
