import subprocess
from flask import Blueprint, jsonify, abort

module_adbToolMain = Blueprint('adbTool', __name__)


@module_adbToolMain.route('/adbTool/adbDevices', methods=['GET'])
def adbDevices():
    try:
        devices = subprocess.check_output(['adb', 'devices']).decode().strip().split('\n')[1:]
        device_list = [device.replace('\tdevice', '') for device in devices if device.split('\t')[1] == 'device']
        print(device_list)
    except:
        print("此方法内部出现异常")
        return abort(204)
    return jsonify(data=device_list,success=True,message='获取成功！'), 200
