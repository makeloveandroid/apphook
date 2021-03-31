import 'package:app_hook_macos/util/extend.dart';
import 'package:app_hook_macos/widget/bean/HttpEventBean.dart';
import 'package:date_format/date_format.dart';
import 'package:flutter/material.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';

import 'BaseTableEventPager.dart';
import 'HttpResultPager.dart';

class HttpEventPager extends StatefulWidget {
  @override
  _HttpEventPagerState createState() => _HttpEventPagerState();
}

class _HttpEventPagerState extends State<HttpEventPager> {
  HttpEventBean _item;
  int _currentIndex = -1;

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.max,
      children: [
        Expanded(
          child: BaseTableEventPager(_buildItem, "OkHttpAction", _buildHeader,
              _buildItemWidget, _buildItemClick),
        ),
        Container(height: 300, child: new HttpResultPager(_item))
            .marginOrPadding(margin: EdgeInsets.only(top: 10)),
      ],
    );
  }

  DataGridSource _buildItem(List<dynamic> items) {
    return HttpEventDataSource(items);
  }

  List<GridColumn> _buildHeader() {
    return <GridColumn>[
      GridTextColumn(mappingName: 'code', headerText: 'code'),
      GridTextColumn(mappingName: 'Method', headerText: 'Method'),
      GridTextColumn(mappingName: 'Host', headerText: 'Host'),
      GridTextColumn(mappingName: 'Path', headerText: 'Path'),
      GridTextColumn(mappingName: 'Start', headerText: 'Start'),
      GridTextColumn(mappingName: 'Duration', headerText: 'Duration'),
      GridTextColumn(mappingName: 'Size', headerText: 'Size'),
    ];
  }

  Widget _buildItemWidget(BuildContext context, GridColumn column, int rowindex,
      List<dynamic> items) {
    return SelectableText("测试");
  }

  void _buildItemClick(RowColumnIndex newRowColumnIndex,
      RowColumnIndex oldRowColumnIndex, List<dynamic> items) {
    setState(() {
      if (_currentIndex != newRowColumnIndex.rowIndex) {
        _currentIndex = newRowColumnIndex.rowIndex;
        var data = items[newRowColumnIndex.rowIndex];
        _item = HttpEventBean.fromJson(data);
      }
    });
  }
}

class HttpEventDataSource extends DataGridSource {
  List<dynamic> _items;

  HttpEventDataSource(this._items);

  @override
  List<Object> get dataSource => _items;

  @override
  Object getCellValue(int rowIndex, String columnName) {
    if (_items.length == 0) return "";

    var data = _items[rowIndex];
    var httpEventBean = HttpEventBean.fromJson(data);
    var httpData = httpEventBean.data;
    var time = formatDate(
        DateTime.fromMillisecondsSinceEpoch(httpEventBean.time),
        [yyyy, '-', mm, '-', dd, ' ', hh, ':', mm, ':', ss]);

    switch (columnName) {
      case 'code':
        return httpData.responseCode;
      case 'Method':
        return httpData.method;
      case 'Host':
        return httpData.host;
      case 'Path':
        return httpData.path;
      case 'Start':
        return httpData.requestDate;
      case 'Duration':
        return "${httpData.tookMs}ms";
      case 'Size':
        return "${(httpData.responseContentLength / 1024).toStringAsFixed(2)}KB";
    }
  }
}
