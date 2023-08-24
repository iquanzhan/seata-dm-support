# seata-dm-support

#### 介绍
Seata 添加对达梦数据库的支持


#### 使用说明

利用SPI机制 以spring-boot-starter的方式外挂实现达梦的支持。

#### 使用步骤

1. 运行Nacos，下载地址：https://github.com/alibaba/nacos/releases/download/2.0.3/nacos-server-2.0.3.zip

2. 运行seata-server，下载地址：https://github.com/iquanzhan/seata/releases/download/1.7.1-SNAPSHOT/seata-server-1.7.1-SNAPSHOT.tar.gz

3. Seata服务中配置修改
    1. 修改`conf/registry.conf`文件为以下内容
   ```yaml
   registry {
     # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
     type = "nacos"
   
     nacos {
       application = "seata-server"
       serverAddr = "127.0.0.1:8848"
       group = "SEATA_GROUP"
       namespace = ""
       cluster = "default"
       username = ""
       password = ""
     }
   }
   
   config {
     # file、nacos 、apollo、zk、consul、etcd3
     type = "nacos"
   
     nacos {
       serverAddr = "127.0.0.1:8848"
       namespace = ""
       group = "SEATA_GROUP"
       username = ""
       password = ""
       dataId = "seataServer.properties"
     }
   }
   ```

    2. 修改`conf/file.conf`为以下内容：

       ```yaml
       ## transaction log store, only used in seata-server
       store {
         ## store mode: file、db、redis
         mode = "file"
         ## rsa decryption public key
         publicKey = ""
         ## file store property
         file {
           ## store location dir
           dir = "file_store/data"
           # branch session size , if exceeded first try compress lockkey, still exceeded throws exceptions
           maxBranchSessionSize = 16384
           # globe session size , if exceeded throws exceptions
           maxGlobalSessionSize = 512
           # file buffer size , if exceeded allocate new buffer
           fileWriteBufferCacheSize = 16384
           # when recover batch read size
           sessionReloadReadSize = 100
           # async, sync
           flushDiskMode = async
         }
       }
       ```

4. 在Nacos中添加`seataServer.properties`配置文件。`Group:SEATA_GROUP`,内容如下：

   ```properties
   transport.type=TCP
   transport.server=NIO
   transport.heartbeat=true
   transport.enableClientBatchSendRequest=false
   transport.threadFactory.bossThreadPrefix=NettyBoss
   transport.threadFactory.workerThreadPrefix=NettyServerNIOWorker
   transport.threadFactory.serverExecutorThreadPrefix=NettyServerBizHandler
   transport.threadFactory.shareBossWorker=false
   transport.threadFactory.clientSelectorThreadPrefix=NettyClientSelector
   transport.threadFactory.clientSelectorThreadSize=1
   transport.threadFactory.clientWorkerThreadPrefix=NettyClientWorkerThread
   transport.threadFactory.bossThreadSize=1
   transport.threadFactory.workerThreadSize=default
   transport.shutdown.wait=3
   service.vgroupMapping.my_test_tx_group=default
   service.default.grouplist=127.0.0.1:8091
   service.enableDegrade=false
   service.disableGlobalTransaction=false
   client.rm.asyncCommitBufferLimit=10000
   client.rm.lock.retryInterval=10
   client.rm.lock.retryTimes=30
   client.rm.lock.retryPolicyBranchRollbackOnConflict=true
   client.rm.reportRetryCount=5
   client.rm.tableMetaCheckEnable=false
   client.rm.tableMetaCheckerInterval=60000
   client.rm.sqlParserType=druid
   client.rm.reportSuccessEnable=false
   client.rm.sagaBranchRegisterEnable=false
   client.tm.commitRetryCount=5
   client.tm.rollbackRetryCount=5
   client.tm.defaultGlobalTransactionTimeout=60000
   client.tm.degradeCheck=false
   client.tm.degradeCheckAllowTimes=10
   client.tm.degradeCheckPeriod=2000
   store.mode=file
   store.file.dir=file_store/data
   store.file.maxBranchSessionSize=16384
   store.file.maxGlobalSessionSize=512
   store.file.fileWriteBufferCacheSize=16384
   store.file.flushDiskMode=async
   store.file.sessionReloadReadSize=100
   server.recovery.committingRetryPeriod=1000
   server.recovery.asynCommittingRetryPeriod=1000
   server.recovery.rollbackingRetryPeriod=1000
   server.recovery.timeoutRetryPeriod=1000
   server.maxCommitRetryTimeout=-1
   server.maxRollbackRetryTimeout=-1
   server.rollbackRetryTimeoutUnlockEnable=false
   client.undo.dataValidation=true
   client.undo.logSerialization=jackson
   client.undo.onlyCareUpdateColumns=true
   server.undo.logSaveDays=7
   server.undo.logDeletePeriod=86400000
   client.undo.logTable=undo_log
   client.undo.compress.enable=true
   client.undo.compress.type=zip
   client.undo.compress.threshold=64k
   log.exceptionRate=100
   transport.serialization=seata
   transport.compressor=none
   metrics.enabled=false
   metrics.registryType=compact
   metrics.exporterList=prometheus
   metrics.exporterPrometheusPort=9898
   ```

5. IDEA中运行`seata-order-2001`、`seata-storage-2002`服务进行测试。

6. 浏览器访问API：http://localhost:2001/order/create?productId=001&count=10&money=10.10

7. 可通过`seata-storage-2002` 服务中的 `com.chengxiaoxiao.storage.controller.StorageController#decrease`
   去除注释`//int i = 10/0;`查看回滚效果。