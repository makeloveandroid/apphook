class FragmentEventBean {
  String action;
  Data data;
  int time;

  FragmentEventBean({this.action, this.data, this.time});

  FragmentEventBean.fromJson(Map<String, dynamic> json) {
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
  String activityName;
  String className;
  String type;

  Data({this.activityName, this.className, this.type});

  Data.fromJson(Map<String, dynamic> json) {
    activityName = json['activity_name'];
    className = json['class_name'];
    type = json['type'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['activity_name'] = this.activityName;
    data['class_name'] = this.className;
    data['type'] = this.type;
    return data;
  }
}
