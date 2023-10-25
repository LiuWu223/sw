from flask import Blueprint, jsonify, abort, request
from socketSocketFun import socketFun

module_adbToolMain = Blueprint('adbTool', __name__)

"""
获取设备中的ADB Devices接口
return deviceSN，型号，系统版本
"""


@module_adbToolMain.route('/adbTool/adbDevices', methods=['POST'])
def adbDevices():
    cmd = 'adb devices'
    # 获取用户上传参数
    userData = request.get_json()
    ip = userData.get('ip')
    devicesData = socketFun.socketSocket(ip, cmd)
    devices = devicesData.strip().split('\n')[1:]
    device_list = [device.replace('\tdevice', '') for device in devices if device.split('\t')[1] == 'device']
    devicesInfoList = []
    # 获取系统版本
    for device in device_list:
        cmd = 'adb -s ' + device + ' shell getprop ro.build.display.id'
        # 系统版本号
        systemVersion = socketFun.socketSocket(ip, cmd).replace(" ", "").replace("\n", "").replace("\t", "")
        # 型号
        deviceType = systemVersion[systemVersion.find('_') + 1:systemVersion.find('_', systemVersion.find('_') + 1)]
        deviceInfo = {
            'device': device,
            'systemVersion': systemVersion,
            'type': deviceType
        }
        devicesInfoList.append(deviceInfo)
    # 返回数据
    return jsonify(data=devicesInfoList, success=True, message='获取成功！'), 200
