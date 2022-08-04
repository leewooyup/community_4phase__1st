package com.ll.exam.article.repository;

import com.ll.exam.annotation.Autowired;
import com.ll.exam.annotation.Repository;
import com.ll.exam.article.dto.ArticleDto;
import com.ll.exam.mymap.MyMap;
import com.ll.exam.mymap.SecSql;

import java.util.List;


@Repository
public class ArticleRepository {
    @Autowired
    private MyMap myMap;

    public List<ArticleDto> getArticles() {
        SecSql sql = myMap.genSecSql();
        sql
                .append("SELECT *")
                .append("FROM article")
                .append("ORDER BY id DESC");
        return sql.selectRows(ArticleDto.class);
    }

    public ArticleDto getArticleById(long id) {
        SecSql sql = myMap.genSecSql();
        sql
                .append("SELECT * FROM article WHERE id = ?", id);
        ArticleDto articleDto = sql.selectRow(ArticleDto.class);
        return  articleDto;
    }

    public long getArticlesCount() {
        SecSql sql = myMap.genSecSql();
        sql
                .append("SELECT COUNT(*)")
                .append("FROM article");

        Long count = sql.selectLong();
        return count;
    }

    public long write(String title, String body, boolean isBool) {
        SecSql sql = myMap.genSecSql();
        sql
                .append("INSERT INTO article")
                .append("SET createdDate = NOW()")
                .append(", modifiedDate = NOW()")
                .append(", title = ?", title)
                .append(", body = ?", body)
                .append(", isBlind = ?", isBool);

        long newId = sql.insert();
        return newId;
    }

    public void modify(long id, String title, String body, boolean isBlind) {
        SecSql sql = myMap.genSecSql();

        // id가 0, 1, 2, 3인 글 수정
        // id가 0인 글은 없으니, 실제로는 3개의 글이 삭제됨
        sql
                .append("UPDATE article")
                .append("SET modifiedDate = NOW()")
                .append(", title = ?", title)
                .append(", body = ?", body)
                .append(", isBlind = ?", isBlind)
                .append("WHERE id = ?", id);


        // 수정된 row 개수
        sql.update();
    }
}
