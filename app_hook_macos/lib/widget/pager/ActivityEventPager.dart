import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/bean/ActivityEventBean.dart';
import 'package:date_format/date_format.dart';
import 'package:flutter/material.dart';

import 'BaseEventPager.dart';

class ActivityEventPager extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BaseEventPager((index, data) {
      var activityEventBean = ActivityEventBean.fromJson(data);
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
                SelectableText("$time Activity创建"),
                SelectableText(
                  "昵称【${activityEventBean.data.activityName}】",
                  style: TextStyle(color: Colors.red),
                ).padding(EdgeInsets.only(left: 10))
              ],
            ),
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
    }, "ActivityAction");
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
