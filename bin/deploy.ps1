#!/bin/sh
# ------------------------
# 发布镜像
# bin/deploy.ps1
# ------------------------

$NAMESPACE = "expm02"
$VERSION = Get-Date -format "yyMMddHH"


function deploy_image([string]$image_name) {
    $remote_url = "${NAMESPACE}/${image_name}"
    docker tag ${image_name} ${remote_url}
    docker push "${remote_url}:${VERSION}"
    docker push "${remote_url}:latest"
    Write-Host "Pushed to ${remote_url}"
}


Write-Host "Login to docker hub ..."
docker login
$image_name = (Split-Path $pwd -leaf)
deploy_image -image_name ${image_name}

Write-Host "finish ."