FROM openjdk:8-jdk-oracle

# ARG 从 pom.xml 插件 dockerfile-maven-plugin 的 buildArgs 传入
ARG JAR_FILE
ARG PRJ_NAME
ADD ./target/${JAR_FILE} /apps/${PRJ_NAME}/${JAR_FILE}
RUN mkdir -p ${PRJ_NAME}/config && \
    mkdir -p ${PRJ_NAME}/logs

# 覆盖 【应用文件】：启动脚本、应用外部配置
COPY ./res/apps /apps

WORKDIR /apps/
ADD ./res/bin/wrapper.sh /wrapper.sh
ADD ./res/bin/docker-entrypoint.sh /docker-entrypoint.sh
ENTRYPOINT /docker-entrypoint.sh