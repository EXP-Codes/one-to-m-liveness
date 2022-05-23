#!/bin/bash

BASIC_DIR="/apps/one-to-m-liveness"

# 这种启动方式可以随时重启应用线程
#${BASIC_DIR}/nohup-startup.sh
#/wrapper.sh

# 这种启动方式因为应用线程在前台运行，一旦重启则容器会挂起
${BASIC_DIR}/startup.sh