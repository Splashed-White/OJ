package dao;

import common.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Description: 封装增删改查操作
 * User: X2148
 * Date: 2022-05-31
 * Time: 9:56
 */
public class ProblemDao {
    // 1. 新增题目
    public void insert(Problem problem) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 1. 和数据库建立连接
            connection = DBUtil.getConnection();
            // 2. 构造 SQL 语句
            String sql = "insert into table_oj values(null, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, problem.getTitle());
            statement.setString(2, problem.getLevel());
            statement.setString(3, problem.getDescription());
            statement.setString(4, problem.getTemplateCode());
            statement.setString(5, problem.getTestCode());
            // 3. 执行 SQL
            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("题目新增失败!");
            } else {
                System.out.println("题目新增成功!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
    }
    // 2. 查询题目列表
    public List<Problem> selectAll(){
        List<Problem> problems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select id,title,level from table_oj";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                Problem problem = new Problem();
                problem.setId(resultSet.getInt("id"));
                problem.setTitle(resultSet.getString("title"));
                problem.setLevel(resultSet.getString("level"));
                problems.add(problem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return problems;
    }
    // 3. 按 id 查询某个题目详情
    public Problem selectOne(int id){
        Problem problem = new Problem();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from table_oj where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                problem.setId(resultSet.getInt("id"));
                problem.setTitle(resultSet.getString("title"));
                problem.setLevel(resultSet.getString("level"));
                problem.setDescription(resultSet.getString("description"));
                problem.setTemplateCode(resultSet.getString("templateCode"));
                problem.setTestCode(resultSet.getString("testCode"));
                return problem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return null;
    }
    // 4. 按 id 删除某条题目
    public void delete(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "delete from table_oj where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            int ret = statement.executeUpdate();
            if(ret != 1){
                System.out.println("题目删除失败");
            }else{
                System.out.println("题目删除成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,statement,null);
        }
    }

    /*******************************  单元测试  **************************************************/
    private static void testInsert(){
        ProblemDao problemDAO = new ProblemDao();
        Problem problem = new Problem();
        // problem.setId();
        problem.setTitle("买卖股票的最佳时机");
        problem.setLevel("简单");
        problem.setDescription("给定一个数组 prices ，它的第i 个元素prices[i] 表示一支给定股票第 i 天的价格。\n" +
                "\n" +
                "你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。\n" +
                "\n" +
                "返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。\n" +
                "\n" +
                "\n" +
                "\n" +
                "示例 1：\n" +
                "\n" +
                "输入：[7,1,5,3,6,4]\n" +
                "输出：5\n" +
                "解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。\n" +
                "     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。\n" +
                "示例 2：\n" +
                "\n" +
                "输入：prices = [7,6,4,3,1]\n" +
                "输出：0\n" +
                "解释：在这种情况下, 没有交易完成, 所以最大利润为 0。\n" +
                "\n" +
                "\n" +
                "提示：\n" +
                "\n" +
                "1 <= prices.length <= 105\n" +
                "0 <= prices[i] <= 104\n" +
                "\n" +
                "来源：力扣（LeetCode）\n" +
                "链接：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock\n" +
                "著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。");
        problem.setTemplateCode("class Solution {\n" +
                "    public int maxProfit(int[] prices) {\n" +
                "\n" +
                "    }\n" +
                "}");
        problem.setTestCode("    public static void main(String[] args) {\n" +
                "        Solution solution = new Solution();\n" +
                "        // testcase1\n" +
                "        int[] prices = {7,1,5,3,6,4};\n" +
                "        int result = solution.candy(prices);\n" +
                "        if (result == 5) {\n" +
                "            System.out.println(\"testcase1 OK\");\n" +
                "        } else {\n" +
                "            System.out.println(\"testcase1 failed!\");\n" +
                "        }\n" +
                "\n" +
                "        // testcase2\n" +
                "        int[] prices2 = {7,6,4,3,1};\n" +
                "        int result2 = solution.candy(prices2);\n" +
                "        if (result2 == 0) {\n" +
                "            System.out.println(\"testcase2 OK\");\n" +
                "        } else {\n" +
                "            System.out.println(\"testcase2 failed!\");\n" +
                "        }\n" +
                "    }\n");
        problemDAO.insert(problem);
        System.out.println("插入成功!");
    }
    private static void testSelectAll() {
        ProblemDao problemDAO = new ProblemDao();
        List<Problem> problems = problemDAO.selectAll();
        System.out.println(problems);
    }
    private static void testSelectOne() {
        ProblemDao problemDAO = new ProblemDao();
        Problem problem = problemDAO.selectOne(1);
        System.out.println(problem);
    }
    private static void testDelete() {
        ProblemDao problemDAO = new ProblemDao();
        problemDAO.delete(2);
    }

    public static void main(String[] args) {
        testInsert();
        //testSelectAll();
        //testSelectOne();
        //testDelete();
    }
}