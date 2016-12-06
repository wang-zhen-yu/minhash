package com.nesei.minhash.service;

import com.nesei.minhash.bean.Article;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Author wzy
 * @Date 2016/12/6 16:24
 */
public interface FileService {

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return
     */
    Article readFile(String filePath) throws Exception;

    /**
     * 读取某一路径下第一子目录所有文件
     *
     * @param dirPath
     * @return
     */
    List<Article> readFilesInDir(String dirPath) throws Exception;
}


//~ Formatted by Jindent --- http://www.jindent.com
