from flask import Blueprint, jsonify, request, abort
from socketSocketFun import socketFun

module_gitToolMain = Blueprint('gitTool', __name__)

""""
git项目克隆接口
return 无
"""


@module_gitToolMain.route('/gitTool/gitClone', methods=['POST'])
def gitClone():
    userData = request.get_json()
    ip = userData.get('ip')
    gitPath = userData.get('gitPath')
    cmd = 'git clone' + ' ' + gitPath
    socketFun.socketSocket(ip, cmd)
    return jsonify(success=True, message='获取成功！'), 200


""""
git项目断言是否存在
return True OR False
"""


@module_gitToolMain.route('/gitTool/assertGitFile', methods=['POST'])
def assertGitFile():
    userData = request.get_json()
    ip = userData.get('ip')
    fileName = userData.get('fileName')
    cmd = 'dir'
    assertData = socketFun.socketSocket(ip, cmd).split('\n')[5:-1]
    for i in assertData:
        if fileName == i.rsplit(' ', 1)[1]:
            return jsonify(success=True, message='获取成功！'), 200
    return jsonify(success=False, message='获取成功失败'), 200


""""
启动项目接口
return 无
"""


@module_gitToolMain.route('/gitTool/cmPath', methods=['POST'])
def cmPath():
    userData = request.get_json()
    ip = userData.get('ip')
    fileName = userData.get('fileName')
    path = str(userData.get('path')).replace('=', '\\')
    cmd = 'start cmd /k ' + path + '\\' + fileName + '\\' + 'run.bat'
    print(cmd)
    assertData = socketFun.socketSocket(ip, cmd)
    print(assertData)
    return jsonify(success=True, message='1111'), 200
