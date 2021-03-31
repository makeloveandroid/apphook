/*
 * Copyright (C) 2017 Jeff Gilfelt.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ks.core.net.data;

import java.util.Date;


public class HttpTransaction {
    public Long _id;
    public Date requestDate;
    public Date responseDate;
    public Long tookMs;

    public String protocol;
    public String method;
    public String url;
    public String host;
    public String path;
    public String scheme;

    public Long requestContentLength;
    public String requestContentType;
    public String requestHeaders;
    public String requestBody;
    public boolean requestBodyIsPlainText = true;

    public Integer responseCode;
    public String responseMessage;
    public String error;

    public Long responseContentLength;
    public String responseContentType;
    public String responseHeaders;
    public String responseBody;
    public boolean responseBodyIsPlainText = true;
}
