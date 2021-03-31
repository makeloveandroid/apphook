import 'package:app_hook_macos/SocketManager.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

typedef BuildItem = Widget Function(int, dynamic);
typedef BuildHeader = Widget Function();

class BaseEventPager extends StatefulWidget {
  BuildItem _buildItem;
  BuildHeader buildHeader;
  String _action;

  BaseEventPager(this._buildItem, this._action, {this.buildHeader, Key key})
      : super(key: key);

  @override
  _BaseEventPagerState createState() => _BaseEventPagerState();
}

class _BaseEventPagerState extends State<BaseEventPager> {
  int MAX_VALUE = 1000;
  List<dynamic> _items = List();
  ScrollController _scrollController = new ScrollController();

  @override
  void initState() {
    super.initState();
    SocketManager().addMessageListen(widget._action, (action, data) {
      WidgetsBinding.instance.addPostFrameCallback((timeStamp) {
        print("滚动到底部!!");
        _scrollController.jumpTo(_scrollController.position.maxScrollExtent);
      });
      setState(() {
        if (_items.length >= MAX_VALUE) {
          _items.clear();
        }
        _items.add(data);
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> widgets;
    if (widget.buildHeader != null) {
      widgets = [
        widget.buildHeader(),
        Expanded(
            child: ListView.builder(
          controller: _scrollController,
          itemBuilder: _itemBuilder,
          itemCount: _items.length,
        ))
      ];
    } else {
      widgets = [
        Expanded(
            child: ListView.builder(
          controller: _scrollController,
          itemBuilder: _itemBuilder,
          itemCount: _items.length,
        ))
      ];
    }
    return Scaffold(
      body: Column(
        mainAxisSize: MainAxisSize.max,
        children: widgets,
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

  Widget _itemBuilder(BuildContext context, int index) {
    return widget._buildItem(index, _items[index]);
  }
}
