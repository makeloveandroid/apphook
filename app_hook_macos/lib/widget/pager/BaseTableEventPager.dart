import 'package:app_hook_macos/SocketManager.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';

typedef BuildItem = DataGridSource Function(List<dynamic>);
typedef BuildTableHeader = List<GridColumn> Function();
typedef BuildItemWidget = Widget Function(
    BuildContext context, GridColumn column, int rowindex, List<dynamic>);

typedef BuildCurrentCellActivatedCallback = void Function(
    RowColumnIndex newRowColumnIndex,
    RowColumnIndex oldRowColumnIndex,
    List<dynamic>);

class BaseTableEventPager extends StatefulWidget {
  BuildItem _buildItem;
  BuildTableHeader _buildHeader;
  BuildItemWidget _buildItemWidget;
  BuildCurrentCellActivatedCallback _currentCellActivatedCallback;
  String _action;

  BaseTableEventPager(this._buildItem, this._action, this._buildHeader,
      this._buildItemWidget, this._currentCellActivatedCallback,
      {Key key})
      : super(key: key);

  @override
  _BaseTableEventPagerState createState() => _BaseTableEventPagerState();
}

class _BaseTableEventPagerState extends State<BaseTableEventPager> {
  List<dynamic> _items = List();
  final CustomSelectionManager _customSelectionManager =
      CustomSelectionManager();

  @override
  void initState() {
    super.initState();
    SocketManager().addMessageListen(widget._action, (action, data) {
      WidgetsBinding.instance.addPostFrameCallback((timeStamp) {
        _customSelectionManager.scrollEnd();
      });
      setState(() {
        _items.add(data);
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SfDataGrid(
//        controller: _scrollController,
        cellBuilder: (BuildContext context, GridColumn column, int rowindex) {
          return widget._buildItemWidget(context, column, rowindex, _items);
        },
        navigationMode: GridNavigationMode.cell,
        columnWidthMode: ColumnWidthMode.auto,
        selectionMode: SelectionMode.single,
        source: widget._buildItem(_items),
        gridLinesVisibility: GridLinesVisibility.both,
        columns: widget._buildHeader(),
        selectionManager: _customSelectionManager,
        onCurrentCellActivated: _onCurrentCellActivated,
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.red,
        child: Icon(Icons.delete),
        onPressed: () {
          setState(() {
            _items.clear();
          });
        },
      ),
    );
  }

  void _onCurrentCellActivated(
      RowColumnIndex newRowColumnIndex, RowColumnIndex oldRowColumnIndex) {
    widget._currentCellActivatedCallback(
        newRowColumnIndex, oldRowColumnIndex, _items);
  }
}

class CustomSelectionManager extends RowSelectionManager {
  @override
  void handleKeyEvent(RawKeyEvent keyEvent) {
    super.handleKeyEvent(keyEvent);
  }
}
