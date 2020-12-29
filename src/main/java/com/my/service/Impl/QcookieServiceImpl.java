package com.my.service.Impl;

import com.my.entity.Cookie;
import com.my.service.QcookieService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/08 11:24
 */
@Service
public class QcookieServiceImpl implements QcookieService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<Cookie> QselectKey(String content,Integer pageNow,Integer size) {
        List<Cookie> cookieList = new ArrayList<>();
        try {
            SearchRequest searchRequest = new SearchRequest();
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            //设置高亮
            highlightBuilder.field("name").field("about").requireFieldMatch(false).preTags("<font color='red'>").postTags("</font>");
            //模糊查类型
            queryBuilder.should(QueryBuilders.fuzzyQuery("name",content))
                    .should(QueryBuilders.fuzzyQuery("about",content))
                    .should(QueryBuilders.wildcardQuery("name",content+"*"))
                    .should(QueryBuilders.wildcardQuery("about",content+"*"))
                    .should(QueryBuilders.queryStringQuery(content).field("name").field("about"));
            //添加配置分类、高亮、模糊查类型
            sourceBuilder.query(queryBuilder).from((pageNow-1)*size).size(size).highlighter(highlightBuilder);
            //配置索引、类型
            searchRequest.indices("cookie").types("cookies").source(sourceBuilder);
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //获取分页总数
            int count = (int)search.getHits().getTotalHits();
            Integer total=null;
            if(count%size==0){
                total = count/size;
            }else{
                total = count/size+1;
            }
            request.setAttribute("total",total);
            //redis



            //遍历数据
            SearchHit[] hits = search.getHits().getHits();
            for (SearchHit hit : hits) {
                Map<String, Object> asMap = hit.getSourceAsMap();
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                Cookie cookie = new Cookie();
                cookie.setId(hit.getId());
                cookie.setName(String.valueOf(asMap.get("name")));
                if(highlightFields.containsKey("name")){
                    cookie.setName(String.valueOf(highlightFields.get("name").fragments()[0]));
                }
                cookie.setSrc(String.valueOf(asMap.get("src")));
                cookie.setEnterName(String.valueOf(asMap.get("enterName")));
                String putTime = String.valueOf(asMap.get("putTime"));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(putTime);
                cookie.setPutTime(date);
                cookie.setAbout(String.valueOf(asMap.get("about")));
                if(highlightFields.containsKey("about")){
                    cookie.setAbout(String.valueOf(highlightFields.get("about").fragments()[0]));
                }
                cookie.setSteps(String.valueOf(asMap.get("steps")));
                cookieList.add(cookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookieList;
    }

    @Override
    public List<Cookie> QselectAll(Integer pageNow, Integer size) {
        List<Cookie> cookieList = new ArrayList<>();
        try {
            SearchRequest searchRequest = new SearchRequest();
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            //配置索引、类型
            searchRequest.indices("cookie").types("cookies").source(sourceBuilder);
            //配置分页查询
            sourceBuilder.query(QueryBuilders.matchAllQuery()).from((pageNow-1)*size).size(size);
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //分页总数据
            int count = (int)search.getHits().getTotalHits();
            Integer total=null;
            if(count%size==0){
                total = count/size;
            }else{
                total = count/size+1;
            }
            request.setAttribute("total",total);
            //配置数组遍历
            SearchHit[] hits = search.getHits().getHits();
            for (SearchHit hit : hits) {
                Map<String, Object> asMap = hit.getSourceAsMap();
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                Cookie cookie = new Cookie();
                cookie.setId(hit.getId());
                cookie.setName(String.valueOf(asMap.get("name")));
                cookie.setSrc(String.valueOf(asMap.get("src")));
                cookie.setEnterName(String.valueOf(asMap.get("enterName")));
                String putTime = String.valueOf(asMap.get("putTime"));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(putTime);
                cookie.setPutTime(date);
                cookie.setSteps(String.valueOf(asMap.get("steps")));
                cookieList.add(cookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookieList;
    }

    @Override
    public void redis(String content) {
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        Set<String> range = zSet.range("content", 0, -1);
        if (range.contains(content)) {
            zSet.incrementScore("content", content, 1);
        } else {
            zSet.add("content", content, 1);
        }
    }
}
