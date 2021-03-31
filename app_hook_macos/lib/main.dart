import 'package:app_hook_macos/widget/pager/ActivityEventPager.dart';
import 'package:app_hook_macos/widget/pager/ClickEventPager.dart';
import 'package:app_hook_macos/widget/pager/FragmentEventPager.dart';
import 'package:app_hook_macos/widget/pager/HttpEventPager.dart';
import 'package:app_hook_macos/widget/pager/LongClickEventPager.dart';
import 'package:app_hook_macos/widget/pager/OtherEventPager.dart';
import 'package:bot_toast/bot_toast.dart';
import 'package:flutter/material.dart';

import 'SocketManager.dart';
import 'widget/Menu.dart';
import 'widget/pager/ActivityStartPager.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    // 启动WebSocket
    return MaterialApp(
      builder: BotToastInit(),
      navigatorObservers: [BotToastNavigatorObserver()],
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key: key);

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _index = 0;
  List<Widget> widgets = [
    ClickEventPager(),
    LongClickEventPager(),
    ActivityEventPager(),
    ActivityStartPager(),
    FragmentStatePagerEvent(),
    HttpEventPager(),
    OtherEventPager(),
  ];

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    SocketManager().open();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Row(
      children: [
        Menu((index) {
          setState(() {
            _index = index;
          });
        }),
        Expanded(
          child: IndexedStack(
            index: _index,
            children: widgets,
          ),
        )
      ],
    ));
  }
}
