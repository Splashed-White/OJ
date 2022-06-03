package common;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 封装文件读写操作
 * User: X2148
 * Date: 2022-05-31
 * Time: 9:57
 */
public class FileUtil {
    public static String readFile(String filePath){
        StringBuilder ret = new StringBuilder();
        if(filePath != null){
            try {
                FileReader fileReader = new FileReader(filePath);
                while(true){
                    int ch = fileReader.read();
                    if(ch == -1){
                        break;
                    }
                    ret.append((char)ch);
                }
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret.toString();
    }
    public static void writeFile(String content,String filePath){
        if(filePath != null){
            try {
                FileWriter fileWriter = new FileWriter(filePath);
                fileWriter.write(content);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        FileUtil.writeFile("hello world","D:/2021_code/项目/Servlet_OJ/test.txt");
        String content = FileUtil.readFile("D:/2021_code/项目/Servlet_OJ/test.txt");
        System.out.println(content);
    }
}