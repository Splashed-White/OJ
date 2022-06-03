package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.Problem;
import dao.ProblemDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: X2148
 * Date: 2022-05-31
 * Time: 9:55
 */
@WebServlet("/problem")
public class ProblemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("application/json;charset=utf8");

        ObjectMapper mapper = new ObjectMapper();

        String idString = req.getParameter("id");
        ProblemDao problemDao = new ProblemDao();
        if("".equals(idString) || idString == null){
            // 获取题目列表
            List<Problem> problems = problemDao.selectAll();
            String resqString = mapper.writeValueAsString(problems);
            resp.getWriter().write(resqString);
        }else{
            //获取题目详情
            Problem problem = problemDao.selectOne(Integer.parseInt(idString));
            String resqString = mapper.writeValueAsString(problem);
            resp.getWriter().write(resqString);
        }
    }
}
