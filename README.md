# api-test 命令行参数解析工具

## 参数说明
> -h, --help                     输出帮助信息     
> -t, --thread <value>           并发数    
> -c, --count <value>            调用次数   
> -s, --second <value>           调用时长：单位秒   
> -p, --property <key=value>     自定义扩展属性    
> -o, --output <file>            结果输出到文件中
## 使用说明
```shell
java  \
-Xms4G \
-Xmx4G \
-XX:MetaspaceSize=256m \
-XX:MaxMetaspaceSize=256m \
-XX:+UseParNewGC \
-XX:+UseConcMarkSweepGC \
-XX:+CMSParallelRemarkEnabled \
-XX:SurvivorRatio=8 \
-XX:MaxTenuringThreshold=8 \
-XX:+UseCMSCompactAtFullCollection \
-XX:CMSFullGCsBeforeCompaction=0 \
-XX:CMSInitiatingOccupancyFraction=75 \
-XX:+UseCMSInitiatingOccupancyOnly \
-XX:CMSWaitDuration=10000 \
-XX:+CMSParallelInitialMarkEnabled \
-XX:+CMSClassUnloadingEnabled \
-jar api-test.jar \
-t 10 \
-c 20 \
-s 30 \
-p connectTimeout=3 \
-p readTimeout=10 \
-o result.txt