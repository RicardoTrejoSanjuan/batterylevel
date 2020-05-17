// import 'package:amazing/src/pages/home_page.dart';
import 'package:batterylevel/pages/MethodChannel.dart';
import 'package:flutter/material.dart';

class MyApp extends StatelessWidget {
  @override
  Widget build(context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Center(
        child: MyHomePagesState(),
      ),
    );
  }
}