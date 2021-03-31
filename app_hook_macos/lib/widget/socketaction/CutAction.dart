import 'package:app_hook_macos/widget/bean/CutActivityBean.dart';
import 'package:app_hook_macos/widget/pager/CutImageDialog.dart';
import 'package:flutter/material.dart';

class CutAction {
 static void call(BuildContext context,String action, dynamic data) {
    CutActivityBean cutActivityBean = CutActivityBean.fromJson(data);
    print("展示图片");
    showDialog<Null>(
        context: context, //BuildContext对象
        barrierDismissible: false,
        builder: (BuildContext context) {
          return CutImageDialog(data: cutActivityBean);
        });
  }
}
