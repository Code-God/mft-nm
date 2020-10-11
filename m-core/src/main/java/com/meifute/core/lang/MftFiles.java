package com.meifute.core.lang;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.StringTokenizer;

public final class MftFiles {

    /**
     * 递归强制删除指定路径下的文件或目录，包括子目录
     * 这个方法存在一些显然的风险，比如目录拼错的情况，请非常小心的使用
     * @param path
     */
    public final static void deleteRecursive(Path path){
        throw  new UnsupportedOperationException("Not implemented");
    }

    /**
     * 列出指定路径所有的目录和文件（包括子目录）
     * @param path
     * @return
     */
    public final static List<Files> listRecursive(Path path){
        throw  new UnsupportedOperationException("Not implemented");
    }

    /**
     * 获取零时目录路径
     * @return
     */
    public final static  String getTmpDirPath(){
        return System.getProperty("java.io.tmpdir");
    }

    public final static String getUserHomeDirPath(){
        return System.getProperty("user.home");
    }

    /**
     * 获取子路径相对于父路径的相对路径
     * @param parentPath
     * @param childPath
     * @return
     */
    public final static String relativePath(String parentPath,String childPath){
        throw  new UnsupportedOperationException("Not implemented");

    }

    /**
     * 获取部分路径.
     * @param path
     * @param begin 开始位置index
     * @param end  结束位置index
     * @return
     */
    public static Path subPath(Path path, int begin, int end){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * 获取路径下文件名后缀
     * 如果是文件夹则返回null
     * 如果没有扩展名，则返回空字符串
     * @param fileName
     * @return  null ： 如果不是文件， 空字符串： 如果没有扩展名。
     */
    public static String getExtensionName(String fileName){
        StringTokenizer st =new StringTokenizer("abcc,sdfe");

        throw new UnsupportedOperationException("Not implemented");
    }


}
