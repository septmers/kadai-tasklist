package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;


@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())){

            EntityManager em = DBUtil.createEntityManager();

            //更新したいデータのIDをフォームから取得し、データベースから取り出してDTOクラスに格納する
            Task t = em.find(Task.class, (Integer)request.getSession().getAttribute("task_id"));

            //セッションスコープの不要になったデータを削除
            request.getSession().removeAttribute("task_id");

            //フォームから受け取った値を更新する
            String content = request.getParameter("content");
            t.setContent(content);

            //更新日時のみデータを上書きする
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setUpdated_at(currentTime);

            //後ほどバリデーションを記述する

            //データベースを更新
            em.getTransaction().begin();
            em.getTransaction().commit();
            request.getSession().setAttribute("flush", "更新が完了しました。");   //セッションスコープにフラッシュメッセージをセット
            em.close();

            //indexサーブレットへリダイレクト
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
}