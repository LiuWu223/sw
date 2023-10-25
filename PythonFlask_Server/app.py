from flask import Flask, jsonify, Response, abort, request, session
from Adb.adbTool import module_adbToolMain
from GitClone.gitCloneMain import module_gitToolMain
from Dispose.appErrorHandler import module_appError
from SocketServer.ClientStart import module_socketServer
from flask_cors import CORS

app = Flask(__name__)

# 跨域处理
CORS(app)
"""
模块引入
"""
# adb工具接口
app.register_blueprint(module_adbToolMain)
# 引入git工具接口
app.register_blueprint(module_gitToolMain)
# 引入错误返回结果接口
app.register_blueprint(module_appError)
# 引入客户端
app.register_blueprint(module_socketServer)


@app.route('/', methods=['GET'])
def hello_world():
    if Response.status_code != 200:
        return abort(500)
    return jsonify(hello="hello!!您已经成功访问到了接口可以继续使用了！！！"), 200


def result(param, param1):
    return jsonify(param1), param


# @app.before_request
# def before():
#     url = request.path  # 当前请求的URL
#     passUrl = ["/login", "/regist"]
#     if url in passUrl:
#         pass
#     else:
#         _id = session.get("Authorization", None)
#         print(session)
#         if not _id:
#             return result(203, {"info": "未登录"})
#         else:
#             pass

if __name__ == '__main__':
    app.run(host='182.168.3.85', debug=False, port=5000)
