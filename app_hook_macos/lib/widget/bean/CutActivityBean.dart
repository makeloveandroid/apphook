class CutActivityBean {
  String action;
  Data data;
  int time;

  CutActivityBean({this.action, this.data, this.time});

  CutActivityBean.fromJson(Map<String, dynamic> json) {
    action = json['action'];
    data = json['data'] != null ? new Data.fromJson(json['data']) : null;
    time = json['time'];
  }
}

class Data {
  String activityName;
  String base64Byte;

  Data({this.activityName, this.base64Byte});

  Data.fromJson(Map<String, dynamic> json) {
    activityName = json['activity_name'];
    base64Byte = json['base64_byte'];
  }
}
