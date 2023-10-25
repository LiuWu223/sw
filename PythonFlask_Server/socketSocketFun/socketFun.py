import socket

"""
socket方法
"""


def socketSocket(ip, cmd):
    # 创建服务对象
    clients = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        # 设置超时时间，超时返回超时异常
        clients.settimeout(60)
        # 建立连接
        clients.connect(('{}'.format(ip), 6565))
        # 执行窗口命令
        clients.sendall(cmd.encode())
        # 读取数据
        data = clients.recv(8192).decode('utf-8')
        # 关闭连接
        clients.close()
        return data
    except TimeoutError as timeout:
        print(timeout)
        clients.close()
        return ''
    except ConnectionRefusedError as cre:
        print(cre)
        clients.close()
        return cre
    except ConnectionResetError as cree:
        print(cree)
        return cree

