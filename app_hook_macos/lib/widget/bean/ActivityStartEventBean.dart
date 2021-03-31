class ActivityStartEventBean {
  String action;
  Data data;
  int time;

  ActivityStartEventBean({this.action, this.data, this.time});

  ActivityStartEventBean.fromJson(Map<String, dynamic> json) {
    action = json['action'];
    data = json['data'] != null ? new Data.fromJson(json['data']) : null;
    time = json['time'];
  }
}

class Data {
  String activityName;
  String startContext;
  String stack;
  Map<String, IntentData> intentData;

  Data({this.activityName, this.intentData});

  Data.fromJson(Map<String, dynamic> json) {
    activityName = json['activity_name'];
    startContext = json['start_context'];
    stack = json['stack'];
    if (json['intent_data'] != null) {
      Map<String, dynamic> orIntentData = json['intent_data'];
      intentData =new Map<String, IntentData>();
      orIntentData.forEach((key, value) {
        intentData[key]= IntentData.fromJson(value);
      });
    }
  }
}

class IntentData {
  String type;
  String value;

  IntentData({this.type, this.value});

  IntentData.fromJson(Map<String, dynamic> json) {
    type = json['type'];
    value = json['value'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['type'] = this.type;
    data['value'] = this.value;
    return data;
  }
}
