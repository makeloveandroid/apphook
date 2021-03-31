import 'dart:convert';

import 'package:app_hook_macos/SocketManager.dart';
import 'package:app_hook_macos/widget/bean/ApitTestBean.dart';
import 'package:app_hook_macos/widget/pager/ApiDialog.dart';
import 'package:bot_toast/bot_toast.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ApiAction {
  static void call(BuildContext context, String action, dynamic data) async {
    TestApiBean testApiBean = TestApiBean.fromJson(data);
    TestApiData apiData = testApiBean.apiData;
    TestApiData apiDataResult = await showDialog(context, apiData);
    if (apiDataResult != null) {
      testApiBean.apiData = apiDataResult;
      testApiBean.apiData.isGetConfig = false;
      var jsonStr = json.encode(testApiBean);
      SocketManager().sendMessage(jsonStr);
    }
  }

  static Future<TestApiData> showDialog(
      BuildContext context, TestApiData apiData) async {
    return showCupertinoDialog<TestApiData>(
      context: context,
      builder: (context) {
        // 2. 构建一个Dialog
        var apiController = TextEditingController.fromValue(
            TextEditingValue(text: apiData.host));

        return CupertinoAlertDialog(
          title: Text("设置服务端"),
          content: ApiInputWidget(
            data: apiData,
            textEditingController: apiController,
          ),
          actions: <Widget>[
            CupertinoDialogAction(
              child: Text("取消"),
              onPressed: () {
                // 取消dialog 返回值,和下面中断的类型一定要一致
                Navigator.of(context).pop(null);
              },
            ),
            CupertinoDialogAction(
              child: Text("完成设置"),
              onPressed: () {
                if (apiController.text.isEmpty) {
                  BotToast.showText(text: "请输入Host地址!");
                  return;
                }
                apiData.host = apiController.text;
                Navigator.of(context).pop(apiData);
              },
            ),
          ],
        );
      },
    );
  }
}
