package com.nesei.minhash.start;

import com.nesei.minhash.service.CalculateService;
import com.nesei.minhash.service.HashService;
import com.nesei.minhash.service.impl.CalculateServiceImpl;
import com.nesei.minhash.service.impl.HashServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @Author wzy
 * @Date 2016/11/18 21:07
 */
public class Start {
    private static Log log = LogFactory.getLog(Start.class);

    public static void main(String[] args) throws Exception {
        @SuppressWarnings("resource")
        AbstractApplicationContext context = new FileSystemXmlApplicationContext("classpath:applicationContext_main.xml");

        log.info("Server Start Success /服务成功启动");
        context.registerShutdownHook();


        CalculateService calculateService = (CalculateService) context.getAutowireCapableBeanFactory().getBean("CalculateService");
        HashService hashService = (HashService) context.getAutowireCapableBeanFactory().getBean("HashService");

        String paperA = "昨下午，中国石化销售有限公司重庆江南分公司南滨路加油站，站长仵文杰在与重庆晚报记者沟通中坦言，涉事加油枪喷出来的确实不是油，是空气。";
        String paperB = "下午，石化销售有限公司重庆江南分公司南滨路加油站，站长仵文杰在与重庆晚报记者沟通中坦言，涉事加油枪喷出来的确实不是油，是空气。";


        String shgA = hashService.String2Shingle(paperA);
        String shgB = hashService.String2Shingle(paperB);

        String minHashA = hashService.shingle2Minvalue(shgA);
        String minHashB = hashService.shingle2Minvalue(shgB);

        String onePerHashA = hashService.shingle2OnePerHash(shgA, 0, 100);
        String onePerHashB = hashService.shingle2OnePerHash(shgB, 0, 100);

        System.out.println(onePerHashA);
        System.out.println(onePerHashB);

        float minHashXsl = calculateService.calcXlsOfMinhash(minHashA, minHashB);
        float onePerHashXsl_true = calculateService.calcXlsOfOnePerHash(onePerHashA, onePerHashB, true);
        float onePerHashXsl_false = calculateService.calcXlsOfOnePerHash(onePerHashA, onePerHashB, false);
        float shgXsl = calculateService.calcXslOfShingle(shgA, shgB);
        System.out.println("minHashXsl=" + minHashXsl);
        System.out.println("onePerHashXsl_true=" + onePerHashXsl_true);
        System.out.println("onePerHashXsl_false=" + onePerHashXsl_false);
        System.out.println("shgXsl=" + shgXsl);

    }
}
