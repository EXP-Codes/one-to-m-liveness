# ------------------------
# 清理镜像
# bin/clean.ps1
# ------------------------

function del_image([string]$image_name) {
    $image_id = (docker images -q --filter reference=${image_name})
    if(![String]::IsNullOrEmpty(${image_id})) {
        Write-Host "delete [${image_name}] ..."
        docker image rm -f ${image_id}
        Write-Host "done ."
    }
}


Write-Host "clean images ..."
mvn clean
$image_name = (Split-Path $pwd -leaf)
del_image -image_name ${image_name}

Write-Host "clean logs ..."
rm -rf ./logs
Write-Host "finish ."