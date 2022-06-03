/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: X2148
 * Date: 2022-04-27
 * Time: 15:17
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 使用字节流，实现将一个文件中的内容读取出来，写入到另一个文件中(文件拷贝)
 */
public class TestFile {
    public static void main(String[] args) throws IOException {
        String srcPath = "D:/2021_code/项目/java_oj/test1.txt";
        String destPath = "D:/2021_code/项目/java_oj/test2.txt";

        //文件打开
        FileInputStream fileInputStream = new FileInputStream(srcPath); //打开第一个用来读数据的文件
        FileOutputStream fileOutputStream = new FileOutputStream(destPath); //打开第二个用来写数据的文件

        //循环把第一个文件的内容按照字节读取，依次写入到第二个文件中
        while(true){
            // read如果读取完毕，就会返回EOF，用-1表示
            int ch = fileInputStream.read();
            if(ch == -1){
                //文件读取完毕
                break;
            }
            fileOutputStream.write(ch);
        }//while
        //文件关闭,如果忘记关闭文件，可能会引起"文件资源泄露"
        fileInputStream.close();
        fileOutputStream.close();
    }
}

