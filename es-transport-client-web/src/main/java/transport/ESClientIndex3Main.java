package transport;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * es  测试3
 *
 * @author mengqingnan
 * @create 2017-06-11 21:09
 **/
public class ESClientIndex3Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        // 设置集群相关信息
        // 初始化客户端
        TransportClient client = ESClient.getClient();
        // 批量查詢
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                                          .add("twitter1","tweet","0")
                                          .add("twitter1","tweet","19","5","8")
                                          .add("zaozao","taoyan","1")
                                          .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String json = response.getSourceAsString();
                System.out.println(json);
            }
        }
    }
}
