import org.openqa.selenium.firefox.FirefoxDriver

/**
 * @author DELL
 * @date 2022/7/7 10:01
 * @description
 */

waiting {
    timeout = 2
}

environments {
    firefox {
        System.setProperty("webdriver.firefox.bin", "E:\\firefox\\firefox.exe")
        //获取项目根目录
        String rootPath = System.getProperty("user.dir")
        //因为项目使用了selenium3+Firefox，所以在selenium3中，使用Firefox，需要添加驱动
        //System.setProperty("webdriver.firefox.marionette", rootPath + "\\web\\src\\functTest\\resources\\geckodriver.exe")
        System.setProperty("webdriver.gecko.driver", rootPath + "\\src\\functTest\\resources\\geckodriver.exe")
        driver = { new FirefoxDriver() }
    }
}