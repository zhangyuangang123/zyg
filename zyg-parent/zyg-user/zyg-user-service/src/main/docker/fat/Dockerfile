#基础镜像
FROM primetoninc/jdk:1.8
#联系人
MAINTAINER SHIYULONG[375379617@qq.com]
#挂载目录
VOLUME /tmp /log
#环境变量
ENV SERVER_PORT 8761
#同步时间
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone
#添加jar包到容器中
ADD target/*jar app.jar
#暴露端口
EXPOSE $SERVER_PORT
#定义jvm参数变量
ENV JAVA_OPTS="-Xms128m -Xmx128m"
#运行命令
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar --server.port=${SERVER_PORT}
#注释
LABEL description = "用户给中心服务--测试环境"