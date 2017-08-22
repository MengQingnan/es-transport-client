package transport;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * es  测试4
 *
 * @author mengqingnan
 * @create 2017-06-11 21:09
 **/
public class ESClientIndex4Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        // 设置集群相关信息
        // 初始化客户端
        TransportClient client = ESClient.getClient();
        // 批量構建索引文檔  和  刪除索引文檔
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

        bulkRequestBuilder.add(client.prepareIndex("izaodoa","java","0")
                                     .setSource(jsonBuilder()
                                             .startObject()
                                             .field("user", "mengqn")
                                             .field("message", "mengqn")
                                             .endObject()))
                          .add(client.prepareDelete("twitter1","tweet","0"));

        BulkResponse bulkResponse = bulkRequestBuilder.get();
        if(bulkResponse.hasFailures()){
            Iterator it = bulkResponse.iterator();
            while (it.hasNext()){
                BulkItemResponse item = (BulkItemResponse)it.next();
                System.out.println(item.getFailureMessage());
            }
        }
    }
}
