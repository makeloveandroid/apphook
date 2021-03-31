class TestApiBean {
  String action;
  TestApiData apiData;

  TestApiBean({this.action, this.apiData});

  TestApiBean.fromJson(Map<String, dynamic> json) {
    action = json['action'];
    apiData = json['data'] != null ? new TestApiData.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['action'] = this.action;
    if (this.apiData != null) {
      data['data'] = this.apiData.toJson();
    }
    return data;
  }
}

class TestApiData {
  bool isGetConfig;
  String host;
  bool isHttps;
  String action;

  TestApiData({this.isGetConfig, this.host, this.isHttps, this.action});

  TestApiData.fromJson(Map<String, dynamic> json) {
    isGetConfig = json['isGetConfig'];
    host = json['host'];
    isHttps = json['isHttps'];
    action = json['action'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['isGetConfig'] = this.isGetConfig;
    data['host'] = this.host;
    data['isHttps'] = this.isHttps;
    data['action'] = this.action;
    return data;
  }
}