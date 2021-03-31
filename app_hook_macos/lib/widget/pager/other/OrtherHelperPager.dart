import 'dart:convert';

import 'package:app_hook_macos/util/dialog_util.dart';
import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/bean/LocationAction.dart';
import 'package:bot_toast/bot_toast.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../../../SocketManager.dart';

class OtherHelperPager extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          "辅助功能",
          style: TextStyle(fontSize: 18),
        ).padding(EdgeInsets.only(left: 8, bottom: 10, top: 10)),
        Wrap(
          children: [
            _buildLocation(context).padding(EdgeInsets.all(5)),
            _buildButtonAction(context, "截屏", "CutActivityAction")
                .padding(EdgeInsets.all(5)),
            _buildButtonAction(context, "重启APP", "ReStartAppAction")
                .padding(EdgeInsets.all(5)),
            _buildButtonAction(context, "布局边框", "LayoutBorderAction")
                .padding(EdgeInsets.all(5)),
            _buildButtonAction(context, "布局层级", "LayoutScalpelAction")
                .padding(EdgeInsets.all(5)),
            _buildButtonAction(context, "相对位置", "LayoutRelativeAction")
                .padding(EdgeInsets.all(5)),
//            _buildApiTestAction(context, "API服务器").padding(EdgeInsets.all(5)),
          ],
        ),
      ],
    );
  }

  Widget _buildApiTestAction(BuildContext context, String title) {
    return Container(
      width: 150,
      height: 60,
      decoration: BoxDecoration(
          color: Colors.blueAccent,
          borderRadius: BorderRadius.all(Radius.circular(10))),
      child: Center(
        child: Text(
          title,
          style: TextStyle(fontSize: 16, color: Colors.white),
        ),
      ),
    ).onTapInWell(() async {
      var all = Map();
      var data = Map();
      data["isGetConfig"] = true;
      data["host"] = "";
      data["isHttps"] = false;
      all["action"] = "TestApiAction";
      data["action"] = "TestApiAction";
      all["data"] = data;
      var jsonStr = json.encode(all);
      SocketManager().sendMessage(jsonStr);
    });
  }

  Widget _buildButtonAction(BuildContext context, String title, String action) {
    return Container(
      width: 150,
      height: 60,
      decoration: BoxDecoration(
          color: Colors.blueAccent,
          borderRadius: BorderRadius.all(Radius.circular(10))),
      child: Center(
        child: Text(
          title,
          style: TextStyle(fontSize: 16, color: Colors.white),
        ),
      ),
    ).onTapInWell(() async {
      var all = Map();
      var data = Map();
      data["isOpen"] = true;
      all["action"] = action;
      data["action"] = action;
      all["data"] = data;
      var jsonStr = json.encode(all);
      SocketManager().sendMessage(jsonStr);
    });
  }

  Widget _buildLocation(BuildContext context) {
    return Container(
      width: 150,
      height: 60,
      decoration: BoxDecoration(
          color: Colors.blueAccent,
          borderRadius: BorderRadius.all(Radius.circular(10))),
      child: Center(
        child: Text(
          "虚拟定位",
          style: TextStyle(fontSize: 16, color: Colors.white),
        ),
      ),
    ).onTapInWell(() async {
      String data = await InputDialog2("模拟经纬度", "经度", "纬度").show(context);
      if (data.isNotEmpty) {
        double j = double.parse(data.split("---")[0]);
        double w = double.parse(data.split("---")[1]);
        var jsonStr = json.encode(new LocationAction(j, w));
        SocketManager().sendMessage(jsonStr);
        BotToast.showText(text: "经纬度模拟成功,请重启应用!");
      }
    });
  }
}
