import socket
from flask import Blueprint, jsonify, abort, request

module_socketServer = Blueprint('socketServer', __name__)


@module_socketServer.route('/socketServer/userCmd', methods=['POST'])
def start_client():
    data = request.get_json()
    print(data)
    userIp = data.get('userIp')
    userCmd = data.get('userCmd')
    clients = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 设置超时时间，超时返回超时异常
    clients.settimeout(10)
    # 连接服务器
    try:
        clients.connect(('{}'.format(userIp), 6565))
        # 执行cmdm命令
        clients.sendall(userCmd.encode())
        data = clients.recv(1024)
        # 数据编码根据实际情况改变，utf-8\gbk\等
        print(data.decode('utf-8'))
    except socket.timeout:
        return abort(404, '数据接收超时')
    except ConnectionResetError:
        return abort(404, '与服务端已断开连接，检查服务端并重启服务端！！')
    except ConnectionRefusedError:
        return abort(404, '服务器已断开，请重新启动服务器！')
    finally:
        clients.close()
        return jsonify(sussccInfo='下载成功'), 200
