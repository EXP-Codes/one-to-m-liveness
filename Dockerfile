FROM openjdk:8-jdk-oracle

# ARG 从 pom.xml 插件 dockerfile-maven-plugin 的 buildArgs 传入
ARG JAR_FILE
ARG PRJ_NAME
ADD ./target/${JAR_FILE} /apps/${PRJ_NAME}/${JAR_FILE}
RUN mkdir -p ${PRJ_NAME}/config && \
    mkdir -p ${PRJ_NAME}/logs && \
    chmod 777 ${PRJ_NAME}/logs

# 覆盖应用启动脚本、应用外部配置
COPY ./res/apps /apps

# 添加容器启动脚本
ADD ./res/bin/wrapper.sh /wrapper.sh
ADD ./res/bin/docker-entrypoint.sh /docker-entrypoint.sh

# 赋予执行权限
RUN chmod a+x /*.sh
RUN chmod a+x /apps/${PRJ_NAME}/*.sh


WORKDIR /apps/${PRJ_NAME}
RUN echo "alias ll='ls -l'" >> /root/.bashrc
ENTRYPOINT /docker-entrypoint.sh