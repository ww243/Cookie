package com.my.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.my.dao.CookieDao;
import com.my.entity.Cookie;
import com.my.service.CookieService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/06 21:33
 */
@Service
@Transactional
public class CookieServiceImpl implements CookieService {

    @Autowired
    private CookieDao cookieDao;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override//查所有
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Cookie> selectAll() {
        List<Cookie> cookies = cookieDao.selectAll();
        return cookies;
    }

    @Override//根据id查
    @Transactional(propagation = Propagation.SUPPORTS)
    public Cookie selectByid(String id) {
        return cookieDao.selectByid(id);
    }

    @Override//添加
    public void insertCookie(Cookie cookie) {
        cookie.setId(UUID.randomUUID().toString());
        cookie.setPutTime(new Date());
        try {
            //进行es索引添加数据
            IndexRequest indexRequest = new IndexRequest("cookie", "cookies", cookie.getId());
            indexRequest.source(JSONObject.toJSONStringWithDateFormat(cookie,"yyyy-MM-dd"), XContentType.JSON);
            IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(index.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cookieDao.insertCookie(cookie);
    }

    @Override//修改
    public void updateCookie(Cookie cookie) {
       try {
           //进行es索引修改数据
            UpdateRequest updateRequest = new UpdateRequest("cookie", "cookies", cookie.getId());
            updateRequest.doc(JSONObject.toJSONStringWithDateFormat(cookie,"yyyy-MM-dd"),XContentType.JSON);
            UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(update.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cookieDao.updateCookie(cookie);
    }

    @Override//进行es索引删除数据
    public void deleteCookie(String id) {
        try {
            //进行es索引删除数据
            DeleteResponse delete = restHighLevelClient.delete(new DeleteRequest("cookie", "cookies", id), RequestOptions.DEFAULT);
            System.out.println(delete.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cookieDao.deleteCookie(id);
    }

    @Override//清空es中所有数据
    public void closeIndex() {
        try {
            //查es中所有数据
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("cookie").types("cookies")
                    .source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = search.getHits();
            SearchHit[] hits1 = hits.getHits();
            //遍历删除数据
            for (SearchHit hit : hits1) {
                System.out.println("文档的内容: "+hit.getSourceAsString());
                System.out.println("=============================");
                DeleteResponse delete = restHighLevelClient
                        .delete(new DeleteRequest("cookie", "cookies",hit.getId() ), RequestOptions.DEFAULT);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addIndex() {
        try {
            //进行数据库中的所有数据
            List<Cookie> all = cookieDao.selectAll();
            for (Cookie cookie : all) {
                //遍历添加
                IndexRequest indexRequest = new IndexRequest("cookie", "cookies", cookie.getId());
                indexRequest.source(JSONObject.toJSONStringWithDateFormat(cookie,"yyyy-MM-dd"), XContentType.JSON);
                IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
                System.out.println(index.status());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
