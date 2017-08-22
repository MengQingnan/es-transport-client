package transport;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * es  测试6 聚合查询
 *
 * @author mengqingnan
 * @create 2017-06-11 21:09
 **/
public class ESClientIndex6Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        // 设置集群相关信息
        // 初始化客户端
        TransportClient client = ESClient.getClient();
        SearchResponse sr = client.prepareSearch()
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.terms("agg1").field("user")
                )
//                .addAggregation(
//                        AggregationBuilders.dateHistogram("agg2")
//                                .field("postDate")
//                                .dateHistogramInterval(DateHistogramInterval.YEAR)
//                )
                .get();

        // Get your facet results
        Map<String, Aggregation> aggMap = sr.getAggregations().getAsMap();

        Terms gradeTerms = (Terms) aggMap.get("agg1");

        Iterator<Terms.Bucket> gradeBucketIt = gradeTerms.getBuckets().iterator();

        while (gradeBucketIt.hasNext()){
            Terms.Bucket gradeBucket = gradeBucketIt.next();
            System.out.println(gradeBucket.getKey() + "年级有" + gradeBucket.getDocCount() +"个学生。");
        }

    }
}
