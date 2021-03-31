import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/bean/ApitTestBean.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ApiInputWidget extends StatefulWidget {
  TestApiData data;
  TextEditingController textEditingController;

  ApiInputWidget({Key key, @required this.data, this.textEditingController})
      : super(key: key);

  @override
  _ApiInputWidgetState createState() => _ApiInputWidgetState();
}

class _ApiInputWidgetState extends State<ApiInputWidget> {
  @override
  Widget build(BuildContext context) {
    return Container(padding: EdgeInsets.all(5), child: _buildTextField());
  }

  _buildTextField() {
    return Column(
      children: [
        Container(
          margin: EdgeInsets.only(top: 8),
          child: CupertinoTextField(
            controller: widget.textEditingController,
            //最大行数
            maxLines: 1,
            // 自动获取焦点
            autofocus: true,
            // 文本对齐方式
            textAlign: TextAlign.left,
            placeholder: "请输入服务器API",
            style: TextStyle(fontSize: 14),
            textInputAction: TextInputAction.done,
          ),
        ),
        Row(
          children: [
            Text(
              "强制https请求走http",
              style: TextStyle(fontSize: 16),
            ),
            CupertinoSwitch(value: widget.data.isHttps, onChanged:
                (bool isSwitch) {
              setState(() {
                widget.data.isHttps = isSwitch;
              });
            })
                .marginOrPadding(margin: EdgeInsets.only(left: 10))
          ],
        ).marginOrPadding(margin: EdgeInsets.only(top: 20))
      ],
    );
  }
}
