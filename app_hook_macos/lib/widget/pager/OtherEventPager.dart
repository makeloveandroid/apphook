import 'dart:convert';

import 'package:app_hook_macos/util/dialog_util.dart';
import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/bean/LocationAction.dart';
import 'package:app_hook_macos/widget/bean/StartActivityAction.dart';
import 'package:app_hook_macos/widget/pager/other/ActivityHelperPager.dart';
import 'package:bot_toast/bot_toast.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../../SocketManager.dart';
import 'other/OrtherHelperPager.dart';

class OtherEventPager extends StatefulWidget {
  @override
  _OtherEventPagerState createState() => _OtherEventPagerState();
}

class _OtherEventPagerState extends State<OtherEventPager> {
  @override
  Widget build(BuildContext context) {
    return ListView(
      children: [
        Container(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              ActivityHelperPager(),
              OtherHelperPager(),
            ],
          ).padding(EdgeInsets.fromLTRB(60, 40, 60, 40)),
        ),
      ],
    );
  }





}
