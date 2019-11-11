package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import models.validators.TaskValidator;
import utils.DBUtil;


@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
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

            //バリデーションを実行
            List<String> errors = TaskValidator.validate(t);
            if(errors.size() > 0){
                em.close();

                request.setAttribute("errors", errors);
                request.setAttribute("task", t);
                request.setAttribute("_token", request.getSession().getId());

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
                rd.forward(request, response);

            }else{

                em.getTransaction().begin();
                em.persist(t);
                em.getTransaction().commit();
                request.getSession().setAttribute("flush", "新規登録が完了しました。");  //セッションスコープにフラッシュメッセージをセット
                em.close();

                response.sendRedirect(request.getContextPath() + "/index");
            }
        }
    }

}
