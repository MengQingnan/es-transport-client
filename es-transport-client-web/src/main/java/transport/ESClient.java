package transport;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ES 客戶端初始化（單例）
 *
 * @author mengqingnan
 * @create 2017-06-11 21:25
 **/
public class ESClient {
    private static TransportClient client = null;
    private static byte[] singleByte = new byte[0];

    private ESClient(){}

    public static  TransportClient getClient(){
        synchronized (singleByte){
            if(client == null){
                Settings settings = Settings.builder()
                        .put("cluster.name", "mengqn-cluster")
                        //.put("xpack.security.transport.ssl.enabled", false)
                        .put("xpack.security.user", "elastic:changeme")
                        //.put("xpack.security.enabled", false)
                        //.put("client.transport.sniff", true)
                        .build();
                // 初始化客户端
                try {
                    client = new PreBuiltXPackTransportClient(settings)
                            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.112.129"), 9300));
                } catch (UnknownHostException e) {
                    client = null;
                }
            }
            return client;
        }
    }
}
