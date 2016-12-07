package com.nesei.minhash.service.impl;

import com.google.common.collect.Lists;
import com.nesei.minhash.bean.Article;
import com.nesei.minhash.service.FileService;
import com.nesei.minhash.service.HashService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @Author wzy
 * @Date 2016/12/6 16:33
 */
@Service("FileService")
public class FileServiceImpl implements FileService {
    @Autowired
    private HashService hashService;

    public Article readFile(String filePath) throws Exception {
        Article article=new Article();

        String context=new Tika().parseToString(new File(filePath));
        article.setContext(context);
        article.setFilePath(filePath);
//        article.setMinHash(hashService.string2Minvalue(context));
//        article.setShingle(hashService.String2Shingle(context));

        return article;
    }

    public List<Article> readFilesInDir(String dirPath) throws Exception {
        List<Article> results= Lists.newArrayList();

        List<String> files=scanningFile(dirPath);

        if(CollectionUtils.isEmpty(files)){
            return results;
        }

        for(int i=0;i<files.size();i++){
            results.add(readFile(files.get(i)));
        }

        return results;
    }

    private List<String> scanningFile(String path) {
        List<String> fileNameListHistory= Lists.newArrayList();

        File file=new File(path);
        File[] filelist = file.listFiles();
        if(filelist==null){
            file.delete();
            return fileNameListHistory;
        }

        for (File filesub : filelist) {
            String extension=getFileExtension(filesub);
            if(extension.equals("doc")||extension.equals("docx")||extension.equals("pdf")||extension.equals("txt")){
                fileNameListHistory.add(filesub.getPath());
            }
            if (fileNameListHistory.size() >= 10000 ) break;
        }
        return fileNameListHistory;
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
}
