package transport;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * es  测试2
 *
 * @author mengqingnan
 * @create 2017-06-11 21:09
 **/
public class ESClientIndex2Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        // 设置集群相关信息
        // 初始化客户端
        TransportClient client = ESClient.getClient();
        // 合并（初始化索引信息，然后执行修改，如果不存在，将索引信息插入，存在，则执行修改）
        IndexRequest indexRequest = new IndexRequest("zaozao","taoyan","1")
                                    .source(jsonBuilder()
                                    .startObject()
                                    .field("name","mengsy")
                                    .field("sex","sale")
                                    .endObject());
        UpdateRequest updateRequest = new UpdateRequest("zaozao","taoyan","1")
                                    .doc(jsonBuilder().startObject()
                                    .field("sex","sale2")
                                    .endObject()).upsert(indexRequest);
        client.update(updateRequest).get();
        }
}
