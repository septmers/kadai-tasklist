package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;


@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //フォームから受け取ったIDのデータを取得し、DTOクラスにセット
        Task t = em.find(Task.class, Integer.parseInt(request.getParameter("id")));

        //リクエストスコープに上記で得たデータをセット
        //CSRF対策にセッションIDを_tokenにセット
        request.setAttribute("task", t);
        request.setAttribute("_token", request.getSession().getId());

        //タスクデータが存在している時のみ
        //セッションスコープにIDをセット（後の削除、更新の際に使用するため）
        if(t != null){
            request.getSession().setAttribute("task_id", t.getId());
        }

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/tasks/edit.jsp");
        rd.forward(request, response);

    }
}