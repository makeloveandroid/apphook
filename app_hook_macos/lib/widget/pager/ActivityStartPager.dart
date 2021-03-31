import 'package:app_hook_macos/util/IconUtil.dart';
import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/bean/ActivityStartEventBean.dart';
import 'package:date_format/date_format.dart';
import 'package:flutter/material.dart';

import 'BaseEventPager.dart';
import 'StackMsgDialog.dart';

class ActivityStartPager extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BaseEventPager((index, data) {
      var activityEventBean = ActivityStartEventBean.fromJson(data);
      var time = formatDate(
          DateTime.fromMillisecondsSinceEpoch(activityEventBean.time),
          [yyyy, '-', mm, '-', dd, ' ', hh, ':', mm, ':', ss]);
      return Card(
        shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(8.0))),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                SelectableText("$time Activity启动"),
                SelectableText(
                  "昵称【${activityEventBean.data.activityName}】",
                  style: TextStyle(color: Colors.red),
                ).padding(EdgeInsets.only(left: 10)),
                Icon(IconUtil.info,color: Colors.blueAccent,).onTap(() {
                  showDialog<Null>(
                      context: context, //BuildContext对象
                      barrierDismissible: false,
                      builder: (BuildContext context) {
                        return StackMsgDialog(stack: activityEventBean.data
                            .stack);
                      });
                })
              ],
            ),
            SelectableText(
              "启动Context：【${activityEventBean.data.startContext}】",
              style: TextStyle(fontSize: 16),
            ).padding(EdgeInsets.only(top: 10)).visible(
                activityEventBean.data.intentData != null &&
                    activityEventBean.data.intentData.isNotEmpty),
            SelectableText(
              "Intent数据：",
              style: TextStyle(fontSize: 16),
            ).padding(EdgeInsets.only(top: 10)).visible(
                activityEventBean.data.intentData != null &&
                    activityEventBean.data.intentData.isNotEmpty),
            SelectableText(
              _buildIntentData(activityEventBean.data.intentData),
              style: TextStyle(color: Colors.red),
            ).padding(EdgeInsets.only(top: 4)).visible(activityEventBean.data.intentData != null &&
                activityEventBean.data.intentData.isNotEmpty),
          ],
        ).padding(EdgeInsets.all(15)),
      );
    }, "StartActivityAction");
  }

  _buildIntentData(Map<String, IntentData> intentData) {
    if (intentData == null || intentData.isEmpty) return "";
    var buffer = StringBuffer();
    int index = 1;
    intentData.forEach((key, value) {
      buffer.write("${index++}： Key【$key】   Value【${value.value}】   类型【${value
          .type}】\n");
    });
    return buffer.toString();
  }
}
