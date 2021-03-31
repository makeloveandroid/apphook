import 'dart:convert';

import 'package:app_hook_macos/util/dialog_util.dart';
import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/bean/StartActivityAction.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../../../SocketManager.dart';

class ActivityHelperPager extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return _buildActivity(context);
  }

  Widget _buildActivity(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          "Activity辅助",
          style: TextStyle(fontSize: 18),
        ).padding(EdgeInsets.only(left: 8, bottom: 10)),
        Wrap(
          children: [
//            _buildStartHelpActivity().padding(EdgeInsets.all(5)),
//            _buildColumnHelpActivity().padding(EdgeInsets.all(5)),
//            _buildSchemeHelpActivity(context).padding(EdgeInsets.all(5)),
            _buildStartActivity(context).padding(EdgeInsets.all(5)),
          ],
        ),
      ],
    );
  }

  Widget _buildSchemeHelpActivity(BuildContext context) {
    return Container(
      width: 150,
      height: 60,
      decoration: BoxDecoration(
          color: Colors.blueAccent,
          borderRadius: BorderRadius.all(Radius.circular(10))),
      child: Center(
        child: Text(
          "scheme跳转",
          style: TextStyle(fontSize: 16, color: Colors.white),
        ),
      ),
    ).onTapInWell(() async {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      var schemeData = prefs.getString("scheme_data");
      String data = await InputDialog("请输入scheme数据", "请输入scheme数据",
              defaultValue: schemeData == null ? "" : schemeData)
          .show(context);
      prefs.setString("scheme_data", data);
      var jsonStr = json.encode(new StartActivityAction(data, isSchema: true));
      print("发送json:$jsonStr");
      SocketManager().sendMessage(jsonStr);
    });
  }

  Widget _buildColumnHelpActivity() {
    return Container(
      width: 150,
      height: 60,
      decoration: BoxDecoration(
          color: Colors.blueAccent,
          borderRadius: BorderRadius.all(Radius.circular(10))),
      child: Center(
        child: Text(
          "启动侧边栏目",
          style: TextStyle(fontSize: 16, color: Colors.white),
        ),
      ),
    ).onTapInWell(() {
//      Map<String, dynamic> intentP = new Map();
//      intentP["PREV_URL"] = "";
//      intentP["tabId"] = 0;
//      intentP["PREV_PAGE_ID"] = 0;
      var jsonStr = json.encode(new StartActivityAction(
          "com.yxcorp.gifshow.corona.home.CoronaHomeActivity"));
      SocketManager().sendMessage(jsonStr);
    });
  }

  Widget _buildStartHelpActivity() {
    return Container(
      width: 150,
      height: 60,
      decoration: BoxDecoration(
          color: Colors.blueAccent,
          borderRadius: BorderRadius.all(Radius.circular(10))),
      child: Center(
        child: Text(
          "启动帮助Activity",
          style: TextStyle(fontSize: 16, color: Colors.white),
        ),
      ),
    ).onTapInWell(() {
      var jsonStr = json.encode(new StartActivityAction(
          "com.kwai.framework.testconfig.ui.TestConfigActivity"));
      SocketManager().sendMessage(jsonStr);
    });
  }

  Widget _buildStartActivity(BuildContext context) {
    return Container(
      width: 150,
      height: 60,
      decoration: BoxDecoration(
          color: Colors.blueAccent,
          borderRadius: BorderRadius.all(Radius.circular(10))),
      child: Center(
        child: Text(
          "启动Activity",
          style: TextStyle(fontSize: 16, color: Colors.white),
        ),
      ),
    ).onTapInWell(() async {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      var startActivity = prefs.getString("start_activity");
      String data = await InputDialog("请输入Activity全路径", "请输入Activity全路径",
              defaultValue: startActivity == null ? "" : startActivity)
          .show(context);
      if (data.isNotEmpty) {
        prefs.setString("start_activity", data);
        var jsonStr = json.encode(new StartActivityAction(startActivity));
        SocketManager().sendMessage(jsonStr);
      }
    });
  }
}
