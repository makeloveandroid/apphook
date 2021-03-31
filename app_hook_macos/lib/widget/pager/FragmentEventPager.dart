import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/bean/FragmentEventBean.dart';
import 'package:app_hook_macos/widget/pager/BaseEventPager.dart';
import 'package:date_format/date_format.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class FragmentStatePagerEvent extends StatefulWidget {
  @override
  _FragmentStatePagerEventState createState() => _FragmentStatePagerEventState();
}

class _FragmentStatePagerEventState extends State<FragmentStatePagerEvent> {

  List<Widget> widgets = [
    FragmentPagerEvent(),
    FragmentUserVisibleHintEvent(),
    FragmentOnResumeEvent(),
  ];

  int _index = 0;
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        _buildMenu(),
        Expanded(
          child: IndexedStack(
            index: _index,
            children: widgets,
          ),
        )
      ],
    ).padding(EdgeInsets.all(15));
  }

  _buildMenu() {
    return Row(
      children: [
        _buildTitle("onViewCreated", 0),
        _buildTitle("setUserVisibleHint", 1),
        _buildTitle("onResume", 2),
      ],
    );
  }


  Widget _buildTitle(String title, int id) {
    return Container(
      decoration: BoxDecoration(
          color: id == _index ? Colors.black : Colors.white,
          borderRadius: BorderRadius.all(Radius.circular(5))),
      child: Text(
        title,
        style: TextStyle(
            color: id == _index ? Colors.white : Colors.black, fontSize: 12),
      ).padding(
        EdgeInsets.all(10),
      ),
    ).marginOrPadding(margin: EdgeInsets.only(left: 10)).onTap(() {
      if (_index != id) {
        setState(() {
          _index = id;
        });
      }
    });
  }
}


class FragmentPagerEvent extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BaseEventPager((index, data) {
      var fragmentCreateEventBean = FragmentEventBean.fromJson(data);
      var time = formatDate(
          DateTime.fromMillisecondsSinceEpoch(fragmentCreateEventBean.time),
          [yyyy, '-', mm, '-', dd, ' ', hh, ':', mm, ':', ss]);
      return Card(
        shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(8.0))),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                SelectableText("$time onViewCreated"),
                SelectableText("所属Activity【${fragmentCreateEventBean.data
                    .activityName
                    .toString()}】")
                    .padding(EdgeInsets.only(left: 10))
              ],
            ),
            SelectableText(
              "昵称【${fragmentCreateEventBean.data.className}】",
              style: TextStyle(color: Colors.red),
            ).padding(EdgeInsets.only(top: 10)),
          ],
        ).padding(EdgeInsets.all(15)),
      );
    }, "FragmentOnViewCreated");
  }
}
class FragmentUserVisibleHintEvent extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BaseEventPager((index, data) {
      var fragmentCreateEventBean = FragmentEventBean.fromJson(data);
      var time = formatDate(
          DateTime.fromMillisecondsSinceEpoch(fragmentCreateEventBean.time),
          [yyyy, '-', mm, '-', dd, ' ', hh, ':', mm, ':', ss]);
      return Card(
        shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(8.0))),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                SelectableText("$time setUserVisibleHint"),
                SelectableText("所属Activity【${fragmentCreateEventBean.data
                    .activityName
                    .toString()}】")
                    .padding(EdgeInsets.only(left: 10))
              ],
            ),
            SelectableText(
              "昵称【${fragmentCreateEventBean.data.className}】",
              style: TextStyle(color: Colors.red),
            ).padding(EdgeInsets.only(top: 10)),
          ],
        ).padding(EdgeInsets.all(15)),
      );
    }, "FragmentUserVisibleHint");
  }
}
class FragmentOnResumeEvent extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BaseEventPager((index, data) {
      var fragmentCreateEventBean = FragmentEventBean.fromJson(data);
      var time = formatDate(
          DateTime.fromMillisecondsSinceEpoch(fragmentCreateEventBean.time),
          [yyyy, '-', mm, '-', dd, ' ', hh, ':', mm, ':', ss]);
      return Card(
        shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(8.0))),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                SelectableText("$time onResume"),
                SelectableText("所属Activity【${fragmentCreateEventBean.data
                    .activityName
                    .toString()}】")
                    .padding(EdgeInsets.only(left: 10))
              ],
            ),
            SelectableText(
              "昵称【${fragmentCreateEventBean.data.className}】",
              style: TextStyle(color: Colors.red),
            ).padding(EdgeInsets.only(top: 10)),
          ],
        ).padding(EdgeInsets.all(15)),
      );
    }, "FragmentOnResume");
  }
}
