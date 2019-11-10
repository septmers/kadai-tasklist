package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries({
    //データベースのすべてのデータを取得する
    @NamedQuery( name = "getAllTasks",
                 query="SELECT t FROM Task AS t ORDER BY t.id DESC"
            ),
    //データベースのすべての件数を取得する
    @NamedQuery( name = "getTasksCount",
                query = "SELECT COUNT(t) FROM Task AS t")
})

@Table(name = "Task")
public class Task{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "content", nullable = false)
    private String content;

    //ゲッター・セッター
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Timestamp getCreated_at(){
        return created_at;
    }

    public void setCreated_at(Timestamp created_at){
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at(){
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at){
        this.updated_at = updated_at;
    }
}
