import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: X2148
 * Date: 2022-04-28
 * Time: 20:46
 */
public class TestExec {
    public static void main(String[] args) throws IOException, InterruptedException {
        //Runtime在JVM中是一个单例类，只有一个实例
        Runtime runtime = Runtime.getRuntime();
        Process process =  runtime.exec("javac"); //创建一个子进程
        //获取到子进程的标准输入和标准错误，把这里的内容写入到两个文件中
        //获取标准输出
        InputStream stdoutFrom = process.getInputStream(); //获取子进程的输入流
        FileOutputStream stdoutTo = new FileOutputStream("stdout.txt");

        while(true){
            int ch = stdoutFrom.read();
            if(ch == -1){
                break;
            }
            stdoutTo.write(ch);
        }//while
        stdoutFrom.close();
        stdoutTo.close();

        //获取标准错误，从这个文件对象中读，就能把子进程的标准错误给读出来
        InputStream stderrFrom = process.getErrorStream(); //获取子进程的错误流
        FileOutputStream stderrTo = new FileOutputStream("stderr.txt");
        while(true){
            int ch = stderrFrom.read();
            if(ch == -1){
                break;
            }
            stderrTo.write(ch);
        }//while
        stderrFrom.close();
        stderrTo.close();

        //通过Process 类的 waitFor 方法，来实现进程的等待
        //父进程执行到 waitFor 的时候，就会阻塞，一直阻塞到子进程执行完毕为止
        //exitCode 退出码 ，就表示子进程的退出结果是否OK，若子进程是代码执行完了正常退出，此时返回的退出码是0，
        // 若子进程代码执行了一半异常退出，此时返回的退出码为非0
        int  exitCode = process.waitFor();
        System.out.println(exitCode);

    }
}
