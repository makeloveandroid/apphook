import 'dart:convert';

import 'BaseAction.dart';

class LocationAction extends BaseAction {
  double j;
  double w;

  LocationAction(this.j, this.w) : super("LocationAction");

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> parent = new Map<String, dynamic>();

    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['j'] = this.j;
    data['w'] = this.w;

    parent["data"] = data;
    parent["action"] = action;
    return parent;
  }
}
