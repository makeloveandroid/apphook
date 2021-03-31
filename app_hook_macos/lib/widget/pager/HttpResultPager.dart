import 'dart:convert';

import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/bean/HttpEventBean.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class HttpHeader {
  String key;
  String value;

  HttpHeader(this.key, this.value);
}

class HttpResultPager extends StatefulWidget {
  HttpEventBean _item;

  HttpResultPager(this._item, {Key key}) : super(key: key);

  @override
  _HttpResultPagerState createState() => _HttpResultPagerState();
}

class _HttpResultPagerState extends State<HttpResultPager> {
  int _index = 0;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        _buildMenu(),
        Expanded(
          child: IndexedStack(
            index: _index,
            children: [
              _getOverview().padding(EdgeInsets.all(8)),
              _getRequestView().padding(EdgeInsets.all(8)),
              _getResponseView().padding(EdgeInsets.all(8)),
            ],
          ),
        )
      ],
    );
  }

  _buildMenu() {
    return Row(
      children: [
        _buildTitle("OVERVIEW", 0),
        _buildTitle("REQUEST", 1),
        _buildTitle("RESPONSE", 2),
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

  Widget _getOverview() {
    List<HttpHeader> items = new List();

    if (widget._item != null) {
      Data data = widget._item.data;
      items.add(new HttpHeader("URL", data.url));
      items.add(new HttpHeader("Method", data.method));
      items.add(new HttpHeader("Protocol", data.protocol));
      items.add(new HttpHeader("Request time", data.requestDate));
      items.add(new HttpHeader("Response time", data.responseDate));
      items.add(new HttpHeader("Duration", "${data.tookMs}ms"));
      items.add(new HttpHeader("Response size",
          "${(data.responseContentLength / 1024).toStringAsFixed(2)}KB"));
    }

    return ListView.builder(
        itemCount: items.length,
        itemBuilder: (context, index) {
          HttpHeader header = items[index];
          return SelectableText("${header.key} : ${header.value}")
              .padding(EdgeInsets.only(top: 3));
        });
  }

  Widget _getRequestView() {
    StringBuffer buffer = new StringBuffer();
    if (widget._item != null) {
      String data = widget._item.data.requestHeaders;
      List<dynamic> headers = json.decode(data);

      for (var value in headers) {
        buffer.write("${value["name"]} : ${value["value"]}\n");
      }

      buffer.write("\n${widget._item.data.requestBody}");
    }
    return SelectableText(buffer.toString());
  }


  Widget _getResponseView() {
    StringBuffer buffer = new StringBuffer();
    if (widget._item != null) {
      String data = widget._item.data.responseHeaders;
      List<dynamic> headers = json.decode(data);

      for (var value in headers) {
        buffer.write("${value["name"]} : ${value["value"]}\n");
      }

      buffer.write("\n${widget._item.data.responseBody}");

    }
    return SelectableText(buffer.toString());
  }
}
