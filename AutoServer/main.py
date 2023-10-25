import json
import os
import socket
import subprocess
import time
import webbrowser

import zlwLog
import requests

def get_local_ip():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    try:
        s.connect(('8.8.8.8', 1))
        locale_ip = s.getsockname()[0]
    except:
        locale_ip = ''
    finally:
        s.close()
    return locale_ip

def getThisIp():
    ip = os.popen('ipconfig').readlines()
    for i in ip:
        iss = i.find('IPv4 地址 . . . . . . . . . . . . :')
        if iss != -1:
            return i[i[iss:-1].find(':') + 5:-1]
    return 'not Ip'
def userLogin(log):
    userIp = str(getThisIp())
    userAccount = str(input("账号: "))
    userPassword = str(input("密码: "))
    path = str(os.getcwd()).replace('\\','=')
    url = 'http://172.20.83.208:3000/auto_sw_admin_war/sy/syUser/logsGet/' + userAccount + '/' + userPassword + '/' + userIp + '/' + path
    try:
        response = requests.get(url)
        a = response.json()
        if a['success']:
            log.info('登录成功')
            time.sleep(1)
            log.info('正在打开网页...')
            time.sleep(3)
            url = 'http://172.20.83.208:3000/'
            webbrowser.open(url)
            return response.text
        log.info(a['message'])
        return response.text
    except requests.exceptions.RequestException as e:
        log.err(e)
# 服务端，执行命令端
def start_server(IP):
    log = zlwLog.logging_setting()
    # 用户登录，录入IP
    while 1:
        try:
            if userLogin(log).count('1004') == 1:
                break
        except AttributeError:
            log.info('请输入正常的字符')
            continue
    while 1:
        # 创建socket对象
        servers = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        # 将socket绑定到指定的地址和端口号
        servers.bind((IP, 6565))
        # 监听客户端链接操作
        servers.listen(1)
        log.info('准备就绪...')
        client_socket, client_address = servers.accept()
        log.info('用户{}已接入'.format(client_address))
        data = client_socket.recv(1024)
        print(data)
        # 没有数据返回
        if not data:
            log.info('用户{}已关闭连接'.format(client_address))
            servers.close()
            continue
        try:
            result = subprocess.run(data.decode(), shell=True, timeout=180, capture_output=True, text=True)
            # result = subprocess.run(data.decode().split(), stdout=subprocess.PIPE, stderr=subprocess.PIPE, timeout=5)
            cmds = ''
            for c in result.args: cmds += str(c) + ' '
            cmds = "[" + cmds + "]"
            log.info("用户{}-输入: {},输出: ->\n{}".format(client_address,cmds,result.stdout))
            # str→bytes：encode()方法。str通过encode()方法可以转换为bytes。
            # bytes→str：decode()方法。如果我们从网络或磁盘上读取了字节流，那么读到的数据就是bytes。要把bytes变为str，就需要用decode()方法。
            client_socket.sendall(result.stdout.encode())
            servers.close()
        except subprocess.TimeoutExpired:
            log.error('此命令可能是一个终端命令，等待执行完成超过180s自动已自动退出！！！')
            client_socket.sendall('此命令可能是一个终端命令，等待执行完成超过180s自动已自动退出！！！'.encode())
            servers.close()
            continue
        except FileNotFoundError as err:
            log.error('命令错误，请查正重试！！')
            log.error(err)
            client_socket.sendall('命令错误，请查正重试！！'.encode())
            servers.close()
            continue


# 按间距中的绿色按钮以运行脚本。
if __name__ == '__main__':
    start_server(get_local_ip())


