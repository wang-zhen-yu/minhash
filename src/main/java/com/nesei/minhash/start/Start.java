package com.nesei.minhash.start;

import com.google.common.collect.Lists;
import com.nesei.minhash.bean.Article;
import com.nesei.minhash.service.CalculateService;
import com.nesei.minhash.service.FileService;
import com.nesei.minhash.service.FilterService;
import com.nesei.minhash.service.HashService;
import com.nesei.minhash.service.impl.CalculateServiceImpl;
import com.nesei.minhash.service.impl.HashServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;
import java.util.*;

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

        FileService fileService = (FileService) context.getAutowireCapableBeanFactory().getBean("FileService");
        CalculateService calculateService = (CalculateService) context.getAutowireCapableBeanFactory().getBean("CalculateService");
        HashService hashService = (HashService) context.getAutowireCapableBeanFactory().getBean("HashService");
        FilterService filterService = (FilterService) context.getAutowireCapableBeanFactory().getBean("FilterService");


        //待查文件
        long start = System.currentTimeMillis();

//        Article DC=fileService.readFile("E:\\test\\1160010012.pdf");
        Article DC=fileService.readFile("F:\\z\\1160010012.pdf");
        //比对文件集合
//        List<Article> BDes=fileService.readFilesInDir("E:\\test\\compare");
//        List<Article> BDes=fileService.readFilesInDir("E:\\JJW_2016\\JJW_2016\\1");
        List<Article> BDes=fileService.readFilesInDir("F:\\zz");

        long end = System.currentTimeMillis();
        long diff= end - start;

        System.out.println("---读取文件耗时:" + diff + "ms");
        System.out.println("---文件数量:" + BDes.size());

        //==========================2Shingle==============================

        start = System.currentTimeMillis();

        for(int i = 0; i < BDes.size(); i++) {
            Article BDArticle=BDes.get(i);
            BDArticle.setShingle(hashService.String2Shingle(BDArticle.getContext()));
        }

        DC.setShingle(hashService.String2Shingle(DC.getContext()));

        end = System.currentTimeMillis();
        diff= end - start;

        System.out.println("---计算shingle耗时:" + diff + "ms");

        //==========================MH==============================

        start = System.currentTimeMillis();

        for(int i = 0; i < BDes.size(); i++) {
            Article BDArticle=BDes.get(i);
            BDArticle.setMinHash(hashService.shingle2Minvalue(BDArticle.getShingle()));
        }

        DC.setMinHash(hashService.shingle2Minvalue(DC.getShingle()));

        end = System.currentTimeMillis();
        diff= end - start;

        System.out.println("---获取MH耗时:" + diff + "ms");

        //==========================One Permutation Hash==============================

        start = System.currentTimeMillis();

        for(int i = 0; i < BDes.size(); i++) {
            Article BDArticle=BDes.get(i);
            BDArticle.setOnePermHash(hashService.shingle2OnePerHash(BDArticle.getShingle(), 1, 1000));
        }

        DC.setOnePermHash(hashService.shingle2OnePerHash(DC.getShingle(), 1, 1000));

        end = System.currentTimeMillis();
        diff= end - start;

        System.out.println("---获取OPM耗时:" + diff + "ms");

        //===========================计算MH===========================

        float T = 0.3f;

        List<String> MHFile = new ArrayList<String>();

        start = System.currentTimeMillis();

        for(int i = 0; i < BDes.size(); i++) {
            Article BDArticle=BDes.get(i);
            float xsl = calculateService.calcXlsOfMinhash(DC.getMinHash(), BDArticle.getMinHash());
            System.out.println(xsl);
            if(xsl > T) {
                MHFile.add(BDArticle.getFilePath());
//                System.out.println(BDArticle.getFilePath());
            }
        }

        end = System.currentTimeMillis();
        diff= end - start;

        System.out.println("---计算MH耗时:" + diff + "ms");

        //===========================计算OPH===========================

        start = System.currentTimeMillis();

        List<String> OPHFile = new ArrayList<String>();

        for(int i = 0; i < BDes.size(); i++) {
            Article BDArticle=BDes.get(i);
            float xsl = calculateService.calcXlsOfOnePerHash(DC.getOnePermHash(), BDArticle.getOnePermHash(), false);
//            System.out.println(xsl);
            if(xsl > T) {
                OPHFile.add(BDArticle.getFilePath());
            }
        }

        end = System.currentTimeMillis();
        diff= end - start;
        System.out.println("---计算OPH耗时:" + diff + "ms");

        //过滤
        List<Integer> observationPoints= new ArrayList<Integer>();
        observationPoints.add(100);
        observationPoints.add(200);
        observationPoints.add(300);
        observationPoints.add(400);
        observationPoints.add(500);
        observationPoints.add(600);
        observationPoints.add(700);
        observationPoints.add(800);
        observationPoints.add(900);

        double e = 0.0000000001;

        //===========================MH过滤===========================

        start = System.currentTimeMillis();

        List<String> MHFFile = new ArrayList<String>();

        for(int i=0; i<observationPoints.size(); i++) {
            int k = observationPoints.get(0);

//            long a1=System.currentTimeMillis();

            double tmpTU = filterService.getUpper(k, 1, e);
            double tmpTL = filterService.getLower(k, T, e);

//            long a2=System.currentTimeMillis();
//            System.out.println("---MH过滤计算耗时:单次计算上下阈值" + (a2-a1) + "ms");

            for(int j=0; j<BDes.size(); j++) {
                Article BDArticle=BDes.get(j);
                if(!BDArticle.isFiltered()) {
                    double xsl = calculateService.calcXlsOfMinhash(DC.getMinHash(), BDArticle.getMinHash());
                    if( xsl > tmpTU) {
                        BDArticle.setFiltered(true);
                    }
                    else if( xsl < tmpTL) {
                        BDArticle.setFiltered(true);
                        MHFFile.add(BDArticle.getFilePath());
                    }
                    else {
                        continue;
                    }
                }
            }

//            long a3=System.currentTimeMillis();
//            System.out.println("---MH过滤计算耗时:单次过滤" + (a3-a2) + "ms");
        }

        end = System.currentTimeMillis();
        diff= end - start;
        System.out.println("---MH过滤计算耗时:" + diff + "ms");

        //===========================OPH过滤===========================

        List<String> OPHFFile = new ArrayList<String>();

        for(int i=0;i<BDes.size();i++){
            Article BDArticle=BDes.get(i);
            BDArticle.setFiltered(false);
        }

        //===========================OPH过滤计算耗时===========================
        start = System.currentTimeMillis();

        for(int i=0; i<observationPoints.size(); i++) {
            int k = observationPoints.get(i);

            double tmpTU = filterService.getUpper(k, 1, e);
            double tmpTL = filterService.getLower(k, T, e);

            for(int j=0; j<BDes.size(); j++) {
                Article BDArticle=BDes.get(j);
                if(!BDArticle.isFiltered()) {
                    double xsl = calculateService.calcXlsOfOnePerHash(DC.getOnePermHash(), BDArticle.getOnePermHash(), false);
                    if( xsl > tmpTU || xsl < tmpTL) {
                        BDArticle.setFiltered(true);
                        continue;
                    }
                    else if( xsl > tmpTU ) {
                        OPHFFile.add(BDArticle.getFilePath());
                        continue;
                    }
                }
            }
        }

        end = System.currentTimeMillis();
        diff= end - start;
        System.out.println("---OPH过滤计算耗时:" + diff + "ms");


        //===========================MH新过滤计算耗时===========================
        HashMap<Integer,Double> TLMaps=new HashMap<Integer, Double>();
        HashMap<Integer,Double> TUMps=new HashMap<Integer, Double>();
        List<Double> TLList=Lists.newArrayList();
        List<Double> TUList=Lists.newArrayList();

        for(int i=0; i<observationPoints.size(); i++) {
            int k = observationPoints.get(i);

            double tmpTU = filterService.getUpper(k, 1, e);
            TUList.add(tmpTU);
            TUMps.put(k,tmpTU);
            double tmpTL = filterService.getLower(k, T, e);
            TLList.add(tmpTL);
            TLMaps.put(k,tmpTL);
        }

        start = System.currentTimeMillis();
        for(int j=0; j<BDes.size(); j++) {
            Article BDArticle=BDes.get(j);
            double xsl=filterService.filterMH(DC.getMinHash(), BDArticle.getMinHash(),observationPoints,TLMaps,TUMps);
            System.out.println(xsl);
        }
        end = System.currentTimeMillis();
        diff= end - start;
        System.out.println("---MH新过滤计算耗时:" + diff + "ms");

        //===========================MH新2过滤计算耗时(遍历过滤点模式)===========================
        start = System.currentTimeMillis();

        for(int j=0; j<BDes.size(); j++) {
            Article BDArticle=BDes.get(j);
            double xsl=filterService.filterMH(DC.getMinHash(), BDArticle.getMinHash(),observationPoints,TLList,TUList);
            System.out.println(xsl);
        }

        end = System.currentTimeMillis();
        diff= end - start;
        System.out.println("---MH新过滤计算2耗时:" + diff + "ms");

//        System.out.println("---MH结果:" + MHFile.size());
//        System.out.println("---OPH结果:" + OPHFile.size());
//        System.out.println("---MH过滤结果:" + MHFFile.size());
//        System.out.println("---OPH过滤结果:" + OPHFFile.size());
    }
}
