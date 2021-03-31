import 'package:bot_toast/bot_toast.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

// 提醒dialog
class HintDialog {
  String title;
  String msg;
  VoidCallback confirmCallback;
  VoidCallback cancelCallback;

  String confirmName;
  String cancelName;

  HintDialog(this.title, this.msg,
      {this.confirmName = "确定",
      this.cancelName = "取消",
      this.confirmCallback,
      this.cancelCallback});

  /// 展示默认 Ios 样式的 Dialog
  Future<bool> show(BuildContext context) {
    // 1. 展示 Dialog,注意返回的是一个 Future 对象,来获取返回值(类型随意)
    return showCupertinoDialog<bool>(
      context: context,
      builder: (context) {
        // 2. 构建一个Dialog
        return CupertinoAlertDialog(
          title: Text(title),
          content: Container(
              padding: EdgeInsets.all(10),
              child: Text(
                msg,
                style: TextStyle(fontSize: 14),
              )),
          actions: <Widget>[
            CupertinoDialogAction(
              child: Text(cancelName),
              onPressed: () {
                // 取消dialog 返回值,和下面中断的类型一定要一致
                Navigator.of(context).pop(false);
                if (cancelCallback != null) {
                  cancelCallback();
                }
              },
            ),
            CupertinoDialogAction(
              child: Text(confirmName),
              onPressed: () {
                // 取消dialog 返回值,和下面中断的类型一定要一致
                Navigator.of(context).pop(true);
                if (confirmCallback != null) {
                  confirmCallback();
                }
              },
            ),
          ],
        );
      },
    );
  }
}

class InputDialog {
  String title;
  String hint;
  String defaultValue;
  VoidCallback confirmCallback;
  VoidCallback cancelCallback;

  String confirmName;
  String cancelName;

  TextEditingController textEditingController;

  InputDialog(this.title, this.hint,
      {this.defaultValue = "",
      this.confirmName = "确定",
      this.cancelName = "取消",
      this.confirmCallback,
      this.cancelCallback}) {
    textEditingController =
        TextEditingController.fromValue(TextEditingValue(text: defaultValue));
  }

  /// 展示默认 Ios 样式的 Dialog
  Future<String> show(BuildContext context) {
    // 1. 展示 Dialog,注意返回的是一个 Future 对象,来获取返回值(类型随意)
    return showCupertinoDialog<String>(
      context: context,
      builder: (context) {
        // 2. 构建一个Dialog
        return CupertinoAlertDialog(
          title: Text(title),
          content:
              Container(padding: EdgeInsets.all(5), child:
              _buildTextField()),
          actions: <Widget>[
            CupertinoDialogAction(
              child: Text(cancelName),
              onPressed: () {
                // 取消dialog 返回值,和下面中断的类型一定要一致
                Navigator.of(context).pop("");
              },
            ),
            CupertinoDialogAction(
              child: Text(confirmName),
              onPressed: () {
                // 取消dialog 返回值,和下面中断的类型一定要一致
                if (textEditingController.text.isEmpty) {
                  BotToast.showText(text: hint);
                  return;
                }

                Navigator.of(context).pop(textEditingController.text);
              },
            ),
          ],
        );
      },
    );
  }

  _buildTextField() {
    return Container(
      margin: EdgeInsets.only(top: 8),
      child: CupertinoTextField(
        controller: textEditingController,
        //最大行数
        maxLines: 1,
        // 自动获取焦点
        autofocus: true,
        // 文本对齐方式
        textAlign: TextAlign.left,
        placeholder: hint,
        style: TextStyle(fontSize: 14),
        textInputAction: TextInputAction.done,
      ),
    );
  }
}

class InputDialog2 {
  String title;
  String hint;
  String hint2;
  String defaultValue;
  VoidCallback confirmCallback;
  VoidCallback cancelCallback;

  String confirmName;
  String cancelName;

  TextEditingController textEditingController;
  TextEditingController textEditingController2;

  InputDialog2(this.title, this.hint, this.hint2,
      {this.defaultValue = "",
      this.confirmName = "确定",
      this.cancelName = "取消",
      this.confirmCallback,
      this.cancelCallback}) {
    textEditingController =
        TextEditingController.fromValue(TextEditingValue(text: defaultValue));
    textEditingController2 =
        TextEditingController.fromValue(TextEditingValue(text: defaultValue));
  }

  /// 展示默认 Ios 样式的 Dialog
  Future<String> show(BuildContext context) {
    // 1. 展示 Dialog,注意返回的是一个 Future 对象,来获取返回值(类型随意)
    return showCupertinoDialog<String>(
      context: context,
      builder: (context) {
        // 2. 构建一个Dialog
        return CupertinoAlertDialog(
          title: Text(title),
          content:
              Container(padding: EdgeInsets.all(5), child: _buildTextField()),
          actions: <Widget>[
            CupertinoDialogAction(
              child: Text(cancelName),
              onPressed: () {
                // 取消dialog 返回值,和下面中断的类型一定要一致
                Navigator.of(context).pop("");
              },
            ),
            CupertinoDialogAction(
              child: Text(confirmName),
              onPressed: () {
                // 取消dialog 返回值,和下面中断的类型一定要一致
                if (textEditingController.text.isEmpty ||
                    textEditingController2.text.isEmpty) {
                  BotToast.showText(text: "请输入录入信息!");
                  return;
                }
                try {
                  var j = double.parse(textEditingController.text);
                  var w = double.parse(textEditingController2.text);
                  Navigator.of(context).pop(
                      "$j---$w");
                } catch (e) {
                  BotToast.showText(text: "请输入正确信息!");
                }
              },
            ),
          ],
        );
      },
    );
  }

  _buildTextField() {
    return Column(
      children: [
        Container(
          margin: EdgeInsets.only(top: 8),
          child: CupertinoTextField(
            controller: textEditingController,
            //最大行数
            maxLines: 1,
            // 自动获取焦点
            autofocus: true,
            // 文本对齐方式
            textAlign: TextAlign.left,
            placeholder: hint,
            style: TextStyle(fontSize: 14),
            textInputAction: TextInputAction.done,
          ),
        ),
        Container(
          margin: EdgeInsets.only(top: 8),
          child: CupertinoTextField(
            controller: textEditingController2,
            //最大行数
            maxLines: 1,
            // 自动获取焦点
            autofocus: true,
            // 文本对齐方式
            textAlign: TextAlign.left,
            placeholder: hint2,
            style: TextStyle(fontSize: 14),
            textInputAction: TextInputAction.done,
          ),
        ),
      ],
    );
  }
}
