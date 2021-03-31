import 'dart:convert';
import 'dart:typed_data';

import 'package:app_hook_macos/util/IconUtil.dart';
import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/bean/CutActivityBean.dart';
import 'package:flutter/material.dart';

class CutImageDialog extends Dialog {
  CutActivityBean data;

  CutImageDialog({Key key, @required this.data}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Uint8List bytes = base64.decode(data.data.base64Byte);
    return Stack(
      children: [
        Center(
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Image.memory(
                bytes,
              ).padding(EdgeInsets.only(top: 20, bottom: 20)),
              Icon(
                IconUtil.close,
                size: 45,
                color: Colors.white,
              ).onTap(() {
                Navigator.pop(context);
              })
            ],
          ),
        )
      ],
    );
  }
}
