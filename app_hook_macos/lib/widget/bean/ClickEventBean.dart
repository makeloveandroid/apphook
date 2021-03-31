class ClickEventBean {
  String action;
  Data data;
  int time;

  ClickEventBean({this.action, this.data, this.time});

  ClickEventBean.fromJson(Map<String, dynamic> json) {
    action = json['action'];
    data = json['data'] != null ? new Data.fromJson(json['data']) : null;
    time = json['time'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['action'] = this.action;
    if (this.data != null) {
      data['data'] = this.data.toJson();
    }
    data['time'] = this.time;
    return data;
  }
}

class Data {
  String eventName;
  String type;
  int viewId;
  String viewName;
  String stack;

  Data({this.eventName, this.type, this.viewId, this.viewName,this.stack});

  Data.fromJson(Map<String, dynamic> json) {
    eventName = json['event_name'];
    type = json['type'];
    viewId = json['view_id'];
    viewName = json['view_name'];
    stack = json['stack'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['event_name'] = this.eventName;
    data['type'] = this.type;
    data['view_id'] = this.viewId;
    data['view_name'] = this.viewName;
    return data;
  }
}