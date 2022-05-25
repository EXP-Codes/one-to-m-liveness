# ------------------------
# 构建镜像
# bin/build.ps1
# ------------------------

function del_image([string]$image_name) {
    $image_id = (docker images -q --filter reference=${image_name})
    if(![String]::IsNullOrEmpty(${image_id})) {
        Write-Host "delete [${image_name}] ..."
        docker image rm -f ${image_id}
        Write-Host "done ."
    }
}


function build_image([string]$image_name, [string]$dockerfile) {
    del_image -image_name ${image_name}
    docker build -t ${image_name} -f ${dockerfile} .
    # docker build --no-cache -t ${image_name} -f ${dockerfile} .
}

Write-Host "clean logs ..."
$LOG_DIR = "./logs"
if (Test-Path ${LOG_DIR}) {
    Remove-Item ${LOG_DIR} -Recurse -Force
}

Write-Host "build image ..."
mvn clean package
$image_name = (Split-Path $pwd -leaf)
build_image -image_name ${image_name} -dockerfile "Dockerfile"
docker-compose build

Write-Host "finish ."