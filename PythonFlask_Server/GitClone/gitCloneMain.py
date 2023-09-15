import subprocess
from flask import Blueprint, jsonify, request, abort

module_gitToolMain = Blueprint('gitTool', __name__)


@module_gitToolMain.route('/gitTool/gitClone', methods=['POST'])
def getClone():
    userPath = 'D:/CA410'
    data = request.get_json()
    userGitPath = data.get('gitPath')

    # 判断连接是否在git列表
    gitPathList = ['https://github.com/LiuWu223/myRepository.git']
    if userGitPath not in gitPathList:
        return abort(400, "下载失败，请检查git地址是否有效！！")
    try:
        command = ['git', 'clone', userGitPath]
        subprocess.check_call(command, cwd='{}'.format(userPath))
    except subprocess.CalledProcessError as cpe:
        print(cpe)
        if 'returned non-zero exit status 128' in str(cpe):
            return abort(400,"目录已存在相同文件，请删除后重试！！！")
    return jsonify(sussccInfo='下载成功'), 200
