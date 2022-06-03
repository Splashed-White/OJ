package compile; /**
 * Created with IntelliJ IDEA.
 * Description:
 * User: X2148
 * Date: 2022-04-30
 * Time: 17:29
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * Created with IntelliJ IDEA.
 * Description:  编译模块-通过命令行调用程序，重定向输出到文件中.
 *  * User: X2148
 * User: X2148
 * Date: 2022-05-31
 * Time: 9:59
 */
public class CommandUtil {
    public static int run(String cmd,String stdoutFile,String stderrFile){
        try {
            // 1. 创建子进程
            Process process = Runtime.getRuntime().exec(cmd);
            // 2. 标准输出、标准错误 重定向 到指定文件
            if(stdoutFile != null){
                InputStream stdoutFrom = process.getInputStream();
                FileOutputStream stdoutTo = new FileOutputStream(stdoutFile);
                while(true){
                    int ch = stdoutFrom.read();
                    if(ch == -1){
                        break;
                    }
                    stdoutTo.write(ch);
                }
                stdoutFrom.close();
                stdoutTo.close();
            }
            if(stderrFile != null){
                InputStream stderrFrom = process.getErrorStream();
                FileOutputStream stderrTo = new FileOutputStream(stderrFile);
                while(true){
                    int ch = stderrFrom.read();
                    if (ch == -1) {
                        break;
                    }
                    stderrTo.write(ch);
                }
                stderrFrom.close();
                stderrTo.close();
            }
            // 3. 等待子进程结束
            int exitCode = process.waitFor();
            return exitCode;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        CommandUtil.run("javac", "./stdout.txt", "./stderr.txt");
    }
}
