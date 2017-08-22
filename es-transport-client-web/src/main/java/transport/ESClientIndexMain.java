package transport;

import org.elasticsearch.action.bulk.byscroll.BulkByScrollResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * practice es
 *
 * @author mengqingnan
 * @create 2017-06-04 16:48
 **/
public class ESClientIndexMain {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        // 设置集群相关信息
        TransportClient client = ESClient.getClient();
//        // 创建索引 、类型、文档内容
//        for(int i=0;i<20;i++){
              //// 创建
//            IndexResponse indexResponse = client.prepareIndex("twitter1", "tweet", String.valueOf(i))
//                    .setSource(jsonBuilder()
//                            .startObject()
//                            .field("user", "mengqn"+i)
//                            .field("postDate", new Date())
//                            .field("message", "trying out Elasticsearch")
//                            .endObject()
//                    )
//                    .get();
//        }

//        // Index name
//        String _index = indexResponse.getIndex();
//        System.out.println(_index);
//        // Type name
//        String _type = indexResponse.getType();
//        System.out.println(_type);
//        // Document ID (generated or not)
//        String _id = indexResponse.getId();
//        System.out.println(_id);
//        // Version (if it's the first time you index this document, you will get: 1)
//        long _version = indexResponse.getVersion();
//        System.out.println(_version);
//        // status has stored current instance statement.
//        RestStatus status = indexResponse.status();
//        System.out.println(status);

       //// 删除
        // DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();
        //System.out.println(response.getIndex()+"=="+response.getId());
        // 查询删除
//        BulkByScrollResponse response =
//                DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
//                        .filter(QueryBuilders.matchQuery("user", "mengqn14"))
//                        .source("twitter1")
//                        .get();
//
//        long deleted = response.getDeleted();
//        System.out.println(deleted);
        // on shutdown
        // 修改
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("twitter1");
        updateRequest.type("tweet");
        updateRequest.id("19");
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("user", "刘丽杰")
                .endObject());
        client.prepareUpdate("twitter1", "tweet", "19").setDoc(jsonBuilder()
                .startObject()
                .field("user", "刘丽杰1")
                .endObject()).get();

        GetResponse response = client.prepareGet("twitter1", "tweet", "19")//查询
                .setOperationThreaded(true)
                //.execute().actionGet();
                .get("1000ms");
        Map<String,Object> map = response.getSource();
        Set<Map.Entry<String, Object>> set = map.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()){
            Map.Entry<String,Object> et =  (Map.Entry<String,Object>)it.next();
            System.out.println(et.getKey()+"===="+et.getValue());
        }

        client.close();
    }
}
