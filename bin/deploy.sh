#!/bin/sh
# ------------------------
# 发布镜像
# bin/deploy.sh
# ------------------------

NAMESPACE="expm02"
VERSION=$(date "+%s")


function deploy_image() {
    image_name=$1
    remote_url=${NAMESPACE}/${image_name}
    docker tag ${image_name} ${remote_url}:${VERSION}
    docker push ${remote_url}:${VERSION}
    docker tag ${image_name} ${remote_url}:latest
    docker push ${remote_url}:latest
    echo "Pushed to ${remote_url}"
}


echo "Login to docker hub ..."
docker login
image_name=`echo ${PWD##*/}`
deploy_image ${image_name}

docker image ls | grep "${image_name}"
echo "finish ."