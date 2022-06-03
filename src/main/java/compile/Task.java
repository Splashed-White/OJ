package compile; /**
 * Created with IntelliJ IDEA.
 * Description:
 * User: X2148
 * Date: 2022-04-30
 * Time: 19:27
 */

import common.FileUtil;

import java.io.File;
import java.util.UUID;
/**
 * Created with IntelliJ IDEA.
 * Description: 实现一次编译 + 运行 过程
 * User: X2148
 * Date: 2022-05-31
 * Time: 9:58
 */
public class Task {
    // 1. 约定临时文件
    private static final String WORKDIR ="./tmp/";
    private static final String CLASS= "Solution";  // 编译代码的类名
    private static final String CODE = WORKDIR+"Solution.java";  // 编译代码的文件名
    private static final String COMPILE_ERROR = WORKDIR+"compileError.txt"; //编译错误
    private static final String STDOUT = WORKDIR+"stdout.txt"; //运行标准输出
    private static final String STDERR = WORKDIR+"stderr.txt"; //运行标准错误

    public Answer compileAndRun(Question question){
        File workDir = new File(WORKDIR);
        if(!workDir.mkdirs()){
            workDir.mkdirs();
        }

        Answer answer = new Answer();
        // 2. Question.code -> Solution.java
        FileUtil.writeFile(question.getCode(),CODE);
        // 3. javac编译
        String compileCmd = String.format("javac -encoding utf8 %s -d %s", CODE, WORKDIR);
        System.out.println("编译命令: " + compileCmd);
        CommandUtil.run(compileCmd,null,COMPILE_ERROR);
        String compileError = FileUtil.readFile(COMPILE_ERROR);
        if(!compileError.equals("")){
            //编译错误
            answer.setError(1);
            answer.setReason(compileError);
            return answer;
        }
        // 4. java运行
        String runCmd = String.format("java -classpath %s %s", WORKDIR, CLASS);
        System.out.println("运行命令: " + runCmd);
        CommandUtil.run(runCmd,STDOUT,STDERR);
        String runError = FileUtil.readFile(STDERR);
        if(!runError.equals("")){
            //运行错误
            answer.setError(2);
            answer.setReason(runError);
            return answer;
        }
        //编译运行正常
        // 5. 打包结果为Answer返回
        answer.setError(0);
        answer.setStdout(FileUtil.readFile(STDOUT));
        return answer;
    }

    public static void main(String[] args) {
        Question question = new Question();
        question.setCode(
                "public class Solution {\n" +
                        "   public static void main(String[] args) {\n" +
                        "       System.out.println(\"hello\");\n" +
                        "   }\n" +
                        "}\n");
        Task task = new Task();
        Answer answer = task.compileAndRun(question);
        System.out.println(answer);
    }
}
