import 'package:app_hook_macos/util/IconUtil.dart';
import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/pager/ApiDialog.dart';
import 'package:app_hook_macos/widget/pager/CutImageDialog.dart';
import 'package:app_hook_macos/widget/socketaction/ApiAction.dart';
import 'package:app_hook_macos/widget/socketaction/CutAction.dart';
import 'package:bot_toast/bot_toast.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import '../SocketManager.dart';
import 'bean/ApitTestBean.dart';
import 'bean/CutActivityBean.dart';

typedef IndexCallback = void Function(int);

class Menu extends StatefulWidget {
  Function(int) menuClick;

  Menu(this.menuClick, {Key key}) : super(key: key);

  @override
  _MenuState createState() => _MenuState();
}

class _MenuState extends State<Menu> {
  int _index = 0;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    // 注册屏幕截屏回调
    SocketManager().addMessageListen("CutActivityAction", (action, data) {
      CutAction.call(context, action, data);
    });

    // 注册服务器配置
    SocketManager().addMessageListen("TestApiAction", (action, data) {
      ApiAction.call(context, action, data);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        _buildIcon(0, "点击事件", IconUtil.click, (index) {
          setState(() {
            widget.menuClick(index);
          });
        }),
        _buildIcon(1, "长按事件", IconUtil.longClick, (index) {
          setState(() {
            widget.menuClick(index);
          });
        }),
        _buildIcon(2, "Activity创建", IconUtil.activity, (index) {
          setState(() {
            widget.menuClick(index);
          });
        }),
        _buildIcon(3, "Activity启动", IconUtil.activityOpen, (index) {
          setState(() {
            widget.menuClick(index);
          });
        }),
        _buildIcon(4, "Fragment创建", IconUtil.fragment, (index) {
          setState(() {
            widget.menuClick(index);
          });
        }),
        _buildIcon(5, "Http拦截", IconUtil.http, (index) {
          setState(() {
            widget.menuClick(index);
          });
        }),
        _buildIcon(6, "辅助功能", IconUtil.other, (index) {
          setState(() {
            widget.menuClick(index);
          });
        }),
      ],
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
    );
  }

  _buildIcon(int index, String title, IconData iconData, IndexCallback tap) {
    Color color = Colors.black87;
    if (_index == index) {
      color = Colors.blueAccent;
    }
    return Expanded(
      child: Container(
        width: 140,
        color: _index == index ? Colors.black12 : Colors.transparent,
        child: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Icon(
                iconData,
                color: color,
                size: 35,
              ),
              Text(
                title,
                style: TextStyle(color: color, fontSize: 16),
              ).padding(EdgeInsets.only(top: 15))
            ],
          ),
        ).onTapInWell(() {
          _index = index;
          tap(index);
        }),
      ),
    );
  }
}
