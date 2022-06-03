package compile;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: X2148
 * Date: 2022-05-31
 * Time: 9:58
 */
public class Answer {
    // error=0 表示编译运行均正常
    // error=1 表示编译出错
    // error=2 表示运行出错
    private int error;
    private String reason; //编译/运行的错误信息
    private String stdout; //标准输出
    private String stderr; //标准错误

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "error=" + error +
                ", reason='" + reason + '\'' +
                ", stdout='" + stdout + '\'' +
                ", stderr='" + stderr + '\'' +
                '}';
    }
}
