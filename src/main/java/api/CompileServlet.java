package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.CodeInValidException;
import common.ProblemNotFoundException;
import compile.Answer;
import compile.Question;
import compile.Task;
import dao.Problem;
import dao.ProblemDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: X2148
 * Date: 2022-05-31
 * Time: 9:54
 */
@WebServlet("/compile")
public class CompileServlet extends HttpServlet {
    static class CompileRequest {
        public int id;
        public String code;
    }

    static class CompileResponse {
        public int error;
        public String reason;
        public String stdout;
    }

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CompileRequest compileRequest = null;
        CompileResponse compileResponse = new CompileResponse();
        resp.setStatus(200);
        resp.setContentType("application/json;charset=utf8");
        // 1. 读取请求的 body，将 Json格式解析为 CompileReequset 类对象
        String body = readBody(req);
        compileRequest = objectMapper.readValue(body, CompileRequest.class);
        // 2. 根据 id 属性 从数据库中查到该题目详情，并得到测试用例代码
        ProblemDao problemDAO = new ProblemDao();
        Problem problem = problemDAO.selectOne(compileRequest.id);
        String testCode = problem.getTestCode();  //测试用例代码
        //System.out.println("testCode :" + testCode);
        String requestCode = compileRequest.code;  //用户提交代码
        //System.out.println("requsetCode :" + requestCode);
        /// 3. 拼接测试用例代码和用户提交代码
        String finalCode = mergeCode(requestCode, testCode);
        System.out.println("finalCode :" + finalCode);
        // System.out.println(finalCode);
        // 4. 创建 Task 进行编译 + 运行
        Task task = new Task();
        Question question = new Question();
        question.setCode(finalCode);
        Answer answer = task.compileAndRun(question);
        // 5. 将 Task 运行的结果，包装成一个HTTP响应返回
        compileResponse.error = answer.getError();
        compileResponse.reason = answer.getReason();
        compileResponse.stdout = answer.getStdout();
        String respString = objectMapper.writeValueAsString(compileResponse); //写回Json格式
        resp.getWriter().write(respString);
    }

    private static String mergeCode(String requestCode, String testCode) {
        // 3.1 查找用户提交代码的最后一个}
        int pos = requestCode.lastIndexOf("}");
        if (pos == -1) {
            // 说明提交的代码完全没有 } , 显然是非法的代码.
            return null;
        }
        // 3.2 在此位置进行字符串截取
        String subStr = requestCode.substring(0, pos);
        // 3.3 字符串拼接
        return subStr + testCode + "\n}";
    }

    private static String readBody(HttpServletRequest req) throws UnsupportedEncodingException {
        // 1.1  body.ContentLength
        int contentLength = req.getContentLength();
        // 1.2 byte[]
        byte[] buffer = new byte[contentLength];
        // 1.3 req.InputStream
        try (InputStream inputStream = req.getInputStream()) {
            // 1.4 基于此流对象，读取内容，并存入byte[]数组
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 1.5 byte[] => String
        return new String(buffer, "UTF8"); //new String(buffer)服务器内部会报错
    }
}