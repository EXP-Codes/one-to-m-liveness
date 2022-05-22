#!/bin/sh

BASIC_DIR="/apps/one-to-m-liveness"
CONF_DIR="${BASIC_DIR}/config"

# 前台启动
cd ${BASIC_DIR}
java -jar -Dlogging.config=${CONF_DIR}/logback.xml one-to-m-liveness.jar
