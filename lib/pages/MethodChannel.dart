import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MyHomePagesState extends StatefulWidget {
  static const platform = const MethodChannel('samples.flutter.dev/battery');

  // Get battery level.

  @override
  _MyHomePagesStateState createState() => _MyHomePagesStateState();
}

class _MyHomePagesStateState extends State<MyHomePagesState> {
  static const platform = const MethodChannel('samples.flutter.dev/battery');

  String _batteryLevel = 'Unknown battery level.';
  
  final style = TextStyle(fontSize: 30.0);

  @override
  Widget build(BuildContext context) {
    return Material(
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            RaisedButton(
              child: Text('Get Battery Level'),
              onPressed: _getBatteryLevel,
            ),
            Text(_batteryLevel),
          ],
        ),
      ),
    );
    // Container(
    //   child: Container(
    //     child: Text(_batteryLevel, style: style,),
    //   ),
    // );
  }


  Future<void> _getBatteryLevel() async {
    String batteryLevel;
    try {
      final int result = await platform.invokeMethod('getBatteryLevel');
      batteryLevel = 'Battery level at $result % .';
    } on PlatformException catch (e) {
      batteryLevel = "Failed to get battery level: '${e.message}'.";
    }

    setState(() {
      _batteryLevel = batteryLevel;
    });
  }
}


