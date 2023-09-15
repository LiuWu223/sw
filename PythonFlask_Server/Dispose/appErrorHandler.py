from flask import Blueprint, jsonify

module_appError = Blueprint('appErrorHandler', __name__)


# 202异常处理
# @module_appError.errorhandler(202)
# def not_found(error):
#     print(error)
#     return jsonify({'error': '接受请求但没创建资源'}), 202


# 400异常处理
@module_appError.errorhandler(400)
def not_found(error):
    print(error)
    return jsonify({'error': '服务器不理解请求的语法'}), 400


# 404异常处理
@module_appError.errorhandler(404)
def not_found(error):
    print(error)
    return jsonify({'error': '服务器找不到请求的网页'}), 404


# 404异常处理
@module_appError.errorhandler(500)
def not_found(error):
    print(error)
    return jsonify({'error': '服务器遇到错误，无法完成请求'}), 500
