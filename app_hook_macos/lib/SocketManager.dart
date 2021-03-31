import 'dart:convert';
import 'dart:io';

import 'package:bot_toast/bot_toast.dart';
import 'package:process_run/process_run.dart';
import 'package:web_socket_channel/io.dart';

typedef void MsgCallBack(String action, dynamic data);

class SocketManager {
  Map<String, List<MsgCallBack>> _listens = Map();

  IOWebSocketChannel _channel;

  // 工厂模式 : 单例公开访问点
  factory SocketManager() => _getInstance();

  static SocketManager get instance => _getInstance();

  // 静态私有成员，没有初始化
  static SocketManager _instance;

  // 私有构造函数
  SocketManager._internal() {
    // 初始化
  }

  // 静态、同步、私有访问点
  static SocketManager _getInstance() {
    if (_instance == null) {
      _instance = new SocketManager._internal();
    }
    return _instance;
  }

  void open() async {
//    adb forward tcp:8000 localabstract:app_hook
//    var commands = ["forward", "tcp:8000", "localabstract:app_hook"];
//    print('Running command: adb ${commands.join(" ")}');
//    Process process = await Process.start('adb', commands, runInShell: false);
//    int code = await process.exitCode;
//    print("执行完成:$code");
//    var reslut = await run('dart', ['--version'], verbose: true);
//    print("执行完成:${reslut.stdout}");

    _channel = IOWebSocketChannel.connect("ws://127.0.0.1:8000/apphook");

    _channel.stream.listen(_onData, onError: _onError, onDone: _onDone);
  }

  void _onData(message) {
    print("收到数据$message");
    var jsonData = json.decode(message);
    String action = jsonData["action"];
    _listens[action]?.forEach((callback) {
      callback(action, jsonData);
    });
  }

  void addMessageListen(String action, MsgCallBack msgCallBack) {
    List callback = _listens[action];
    if (callback == null) {
      callback = List<MsgCallBack>();
      _listens[action] = callback;
    }
    callback.add(msgCallBack);
  }

  void sendMessage(String msg) {
    if (_channel != null) {
      _channel.sink.add(msg);
    }
  }

  _onError(err) {
    print("连接失败$err");
  }

  void _onDone() {
    BotToast.showText(text: "未连接设备 2 秒后重试!");
    Future.delayed(Duration(seconds: 2), open);
  }
}
