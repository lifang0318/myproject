package cn.com.izj.config;

import cn.com.izj.TcpStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by GouBo on 2018/6/30 11:28.
 * tcp模块入口
 */
@Component
public class ApplicationEntrance implements CommandLineRunner {

    @Autowired
    private TcpStarter tcpStarter;

    @Override
    public void run(String... args) throws Exception {
        tcpStarter.start();
    }
}
