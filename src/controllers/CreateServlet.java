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


@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())){

        EntityManager em = DBUtil.createEntityManager();

        //値をセットするDTOクラスをインスタンス化
        Task t = new Task();

        //フォームから送られた値をDTOクラスにセット
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());   //現在時刻を取得
        t.setCreated_at(currentTime);   //追加日時に現在時刻をセット
        t.setUpdated_at(currentTime);   //更新日時に現在時刻をセット

        String content=request.getParameter("content");
        t.setContent(content);

        //後ほどバリデーションを記述

        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/index");
        }
    }

}
