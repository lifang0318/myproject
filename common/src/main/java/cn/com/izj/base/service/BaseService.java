package cn.com.izj.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author: lifang
 * @description:基础service
 * @date: Created in 2018/6/11 23:33
 * @version:
 */
public class BaseService {
    protected static Logger log = LoggerFactory.getLogger(BaseService.class);

    protected DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
