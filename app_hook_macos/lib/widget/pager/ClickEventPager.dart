import 'package:app_hook_macos/util/IconUtil.dart';
import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/bean/ClickEventBean.dart';
import 'package:date_format/date_format.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'BaseEventPager.dart';
import 'StackMsgDialog.dart';

class ClickEventPager extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BaseEventPager((index, data) {
      var clickEventBean = ClickEventBean.fromJson(data);
      var time = formatDate(
          DateTime.fromMillisecondsSinceEpoch(clickEventBean.time),
          [yyyy, '-', mm, '-', dd, ' ', hh, ':', mm, ':', ss]);
      return Card(
        shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(8.0))),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                SelectableText("$time 触发点击事件"),
                SelectableText("ID【${clickEventBean.data.viewId.toString()}】")
                    .padding(EdgeInsets.only(left: 10)),
                Icon(IconUtil.info,color: Colors.blueAccent,).onTap(() {
                  showDialog<Null>(
                      context: context, //BuildContext对象
                      barrierDismissible: false,
                      builder: (BuildContext context) {
                        return StackMsgDialog(stack: clickEventBean.data.stack,);
                      });
                })
              ],
            ),
            SelectableText("View昵称【${clickEventBean.data.viewName}】")
                .padding(EdgeInsets.only(top: 10)),
            SelectableText(
              "监听对象【${clickEventBean.data.eventName}】",
              style: TextStyle(color: Colors.red),
            ).padding(EdgeInsets.only(top: 10)),
          ],
        ).padding(EdgeInsets.all(15)),
      );
    }, "ViewClickEventAction");
  }
}
