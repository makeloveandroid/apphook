import 'package:app_hook_macos/util/IconUtil.dart';
import 'package:app_hook_macos/widget/bean/ActivityStartEventBean.dart';
import 'package:flutter/material.dart';
import 'package:app_hook_macos/util/extend.dart';

class StackMsgDialog extends Dialog {
  String stack;

  StackMsgDialog({Key key, @required this.stack}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Center(child: SelectableText(stack,style: TextStyle
            (fontSize: 16),)),
          Align(
            alignment: Alignment.topRight,
            child: Icon(
              IconUtil.close,
              size: 40,
            ).onTap(() {
              Navigator.pop(context);
            }),
          )
        ],
      ).padding(EdgeInsets.all(20)),
    );
  }
}
