package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public IndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //開くページ数を取得
        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(NumberFormatException e){}


        //最大件数と開始位置を指定してメッセージを取得
        List<Task> tasks = em.createNamedQuery("getAllTasks", Task.class)
                              .setFirstResult(15 * (page - 1))  //何件目からデータを取得するか
                              .setMaxResults(15)                //データの最大取得件数
                              .getResultList();

        //全件数を取得
        long task_count = (long)em.createNamedQuery("getTasksCount", Long.class)
                              .getSingleResult();

        em.close();

        //リクエストスコープに取得した値をセット
        request.setAttribute("tasks", tasks);
        request.setAttribute("page", page);
        request.setAttribute("task_count", task_count);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/index.jsp");
        rd.forward(request, response);

    }
}
