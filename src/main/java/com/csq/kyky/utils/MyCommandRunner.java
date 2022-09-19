package com.csq.kyky.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动项目时自动打开数据大屏
 *
 * @author csq
 * @since 2022/9/19
 */

@Order(100)
@Component
public class MyCommandRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String os = System.getProperties().getProperty("os.name");
        switch (os){
            case "Mac OS X":
            case "Mac OS":
                Runtime.getRuntime().exec("open http://localhost:8100/index.html");
                break;
            default:
                Runtime.getRuntime().exec("cmd /c start http://localhost:8100/index.html");
                break;
        }
    }
}
