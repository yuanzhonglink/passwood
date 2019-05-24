package com.pass.util.api.consul;

import com.alibaba.fastjson.JSONObject;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.acl.model.Acl;
import com.ecwid.consul.v1.acl.model.AclType;
import com.ecwid.consul.v1.acl.model.NewAcl;
import com.ecwid.consul.v1.acl.model.UpdateAcl;
import com.ecwid.consul.v1.agent.model.Check;
import com.ecwid.consul.v1.agent.model.Member;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.agent.model.Service;
import com.ecwid.consul.v1.coordinate.model.Datacenter;
import com.ecwid.consul.v1.health.model.HealthService;
import com.ecwid.consul.v1.kv.model.GetValue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author yuanzhonglin
 * @date 2019/5/22
 */
public class ConsulApi {

    private static final String ACL_TOKEN = "6171fe30-1138-4127-9b08-60069e945550";

    private static ConsulClient consulClient = initConsul();

    private static ConsulClient initConsul(){
        consulClient = new ConsulClient("192.168.32.15", 9500);
        return consulClient;
    }

    // 增加服务
    public static void addService(String id, String name) {
        NewService newService = new NewService();
        newService.setId(id);
        newService.setName(name);
        newService.setTags(Arrays.asList("version=1.0", "author=dfzq", "secure=false"));
        newService.setPort(1104);
        newService.setAddress("192.168.32.15");

        NewService.Check serviceCheck = new NewService.Check();
        serviceCheck.setHttp("http://127.0.0.1:1101/consul/health");
        serviceCheck.setInterval("10s");

        newService.setCheck(serviceCheck);

        consulClient.agentServiceRegister(newService, ACL_TOKEN);
    }

    // 删除服务
    public static void deleteService(String serviceId) {
        consulClient.agentServiceDeregister(serviceId, ACL_TOKEN);
    }

    // 获取consul成员
    public static void findMembers() {
        Response<List<Member>> agentMembers = consulClient.getAgentMembers();
        System.out.println("----findMembers----:" + JSONObject.toJSON(agentMembers.getValue()));
    }

    // 获取注册服务
    public static void findService() {
        Response<Map<String, Service>> services = consulClient.getAgentServices();
        System.out.println("----findService----:" + JSONObject.toJSON(services.getValue()));
    }

    // 获取注册服务
    public static void findCheckMsg() {
        Response<Map<String, Check>> agentChecks = consulClient.getAgentChecks();
        System.out.println("----findCheckMsg----:" + JSONObject.toJSON(agentChecks.getValue()));
    }

    // 根据服务名以及健康状态获取服务列表
    public static void findHealthyService(String serviceName, boolean onlyPassing) {
        Response<List<HealthService>> healthyServices = consulClient.getHealthServices(serviceName, onlyPassing, QueryParams.DEFAULT);
        System.out.println("----findHealthyService----:" + JSONObject.toJSON(healthyServices.getValue()));
    }

    // 设置KV
    public static void setKV(String key, String value) {
        Response<Boolean> response = consulClient.setKVValue(key, value, ACL_TOKEN, null);
        System.out.println("----setKV----:" + response.getValue());
    }

    // 查询KV
    public static void getKV(String key) {
        Response<GetValue> kvValue = consulClient.getKVValue(key, ACL_TOKEN);
        System.out.println("----getKV----:" + kvValue.getValue().getDecodedValue());
    }

    // 删除KV
    public static void deleteKV(String key) {
        consulClient.deleteKVValue(key, ACL_TOKEN);
    }

    public static void findRaftPeers() {
        Response<List<String>> listResponse = consulClient.getStatusPeers();
        System.out.println("----findRaftPeers----:" + listResponse.getValue());
    }

    public static void findRaftLeader() {
        Response<String> stringResponse = consulClient.getStatusLeader();
        System.out.println("----findRaftLeader----:" + stringResponse.getValue());
    }

    // 获取数据中心
    public static void getDatacenters() {
        Response<List<String>> response = consulClient.getCatalogDatacenters();
        System.out.println("----getDatacenters----:" + response.getValue());

        Response<List<Datacenter>> datacenters = consulClient.getDatacenters();
        System.out.println("----getDatacenters----:" +datacenters.getValue());
    }

    // createACL
    public static void createACL() {
        NewAcl acl = new NewAcl();
        acl.setName("acl_1");
        acl.setType(AclType.CLIENT);
        acl.setRules("node\"\"{policy=\"write\"}");
        Response<String> response = consulClient.aclCreate(acl, "6171fe30-1138-4127-9b08-60069e945550");

        System.out.println("----createACL----:" + response.getValue());
    }

    // updACL
    public static void updACL() {
        UpdateAcl acl = new UpdateAcl();
        acl.setId("fa5b9db4-8271-de50-28bd-321555137c10");  //token值
        acl.setRules("node\"\"{policy=\"read\"}");
        acl.setName("acl_demo");
        consulClient.aclUpdate(acl, "6171fe30-1138-4127-9b08-60069e945550");

        System.out.println("----updACL----");
    }

    public static void getACLList() {
        Response<List<Acl>> aclList = consulClient.getAclList("6171fe30-1138-4127-9b08-60069e945550");
        System.out.println("----getACLList----:" + JSONObject.toJSON(aclList.getValue()));
    }

    public static void getACL() {
        Response<Acl> acl = consulClient.getAcl("7b21ff0e-c71c-1678-caed-7c47f204032c");
        System.out.println("----getACL----:" + acl.getValue());
    }



    public static void main(String[] args) {
        // 服务
        addService("consul-server-1104", "consul-server");
        findService();
        findHealthyService("consul-client", true);
        deleteService("consul-server-1104");

        // 查询
        findMembers();
        findCheckMsg();

        // KV
        setKV("key1", "value1");
        getKV("key_1");
        deleteKV("key_1");

        // 数据中心
        getDatacenters();

        // ACL
        createACL();
        updACL();
        getACLList();
        getACL();


        findRaftPeers();
        findRaftLeader();
    }
}
