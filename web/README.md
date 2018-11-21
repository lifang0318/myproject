# EDrive

#### 模块说明：
配置模块
- - -

## ==================以下为配置模块接口文档===================

### 1.单张上传图片 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/mobile/card/uploadImage`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|filename|图片文件|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回图片地址|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

*输入参数类型：form-data
    

正确返回：

    {
        "code": 1,
        "message": "成功",
        "data": "http://pdaxdtr0a.bkt.clouddn.com/d15475a89853464a99e99a14f6dbfc80.jpg"
    }

---
### 2.提交身份证信息 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}/mobile/card/addIdCardInfo`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|realName|真实姓名|否|最大长度5|
|identityCardNumber|身份证号码|否|
|handCardPhoto|手持身份证照片|否|
|identityCardPhotoFront|身份证正面url|否|
|identityCardPhotoBehind|身份证背面url|否|


* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

状态码：
    
    1021 ： 身份证已被认证
    

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data": ""
    }
---

### 3.提交驾驶证信息 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}/mobile/card/addDriverCardInfo`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|driverLicencePhotoMaster|驾驶证正本url|否|
|driverLicencePhotoSlave|驾驶证副本url|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data": ""
    }
---

### 4.获取身份证信息 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/card/getIdCardInfo`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|


* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|
|userId|用户id|
|realName|真实姓名|
|identityCardNumber|身份证号码|
|handCardPhoto|手持身份证照片|
|identityCardPhotoFront|身份证正面url|
|identityCardPhotoBehind|身份证背面url|
|realNameAuthStatus|身份证认证状态 0未认证，1审核中 2已审核 3认证失败|
|cardRemark|身份证认证失败原因|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data": 
        {
          "userId":10011,
          "realName":"李三",
          "identityCardNumber":"512685200005055211",
          "handCardPhoto":"www.izjImage/image.ljfdisdf124.png",
          "identityCardPhotoFront":"www.izjImage/image.ljfdisdf122.png",
          "identityCardPhotoBehind":"www.izjImage/image.ljfdisdf123.png",
          "realNameAuthStatus":2,
          "cardRemark":""
        }
    }
---

### 5.获取驾驶证信息 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/card/getDriverCardInfo`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|


* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|
|userId|用户id|
|driverLicencePhotoMaster|驾驶证正本url|
|driverLicencePhotoSlave|驾驶证副本url|
|driverLicenceAuthStatus|驾驶认证状态 0未认证，1审核中 2已审核 3认证失败|
|driverRemark|驾驶证认证失败原因|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data": 
        {
          "userId":10011,
          "driverLicencePhotoMaster":"www.izjImage/image.ljfdisdf122.png",
          "driverLicencePhotoSlave":"www.izjImage/image.ljfdisdf123.png",
          "driverLicenceAuthStatus":2,
          "driverRemark":""
        }
    }
---

### 6.审核身份证 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}/system/card/auditIdCard`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|userId|被审核用户id|否|
|state|0不通过 1通过|否|
|remark|审核不通过原因 不通过时必填|是|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data":""
    }
---


### 7.审核驾驶证 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}/system/card/auditDriverCard`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|userId|被审核用户id|否|
|state|0不通过 1通过|否|
|remark|审核不通过原因 不通过时必填|是|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data":""
    }
---

### 8.获取配置信息列表 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}//system/setting/getSetting`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|
|id|变量id|
|fieldName|变量名|
|fieldValue|变量值|
|fieldType|变量类型 0按比例 1 按数值|
|uniqueId|变量标识id|
|fieldDescribe|变量描述|
|fieldState|变量状态 0禁用 1启用|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data":
        [{
          "id":1,
          "fieldName":"折扣",
          "fieldValue":"0.55",
          "fieldType":0,
          "uniqueId":0,
          "fieldDescribe":"全局折扣",
          "fieldState":1
        },
        {
           "id":2,
           "fieldName":"服务费",
           "fieldValue":"15",
           "fieldType":1,
           "uniqueId":1,
           "fieldDescribe":"平台服务费",
           "fieldState":1
        }
       ]
    }
---

### 9.更新配置 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}//system/setting/updateSetting`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|id|变量id|否|
|fieldValue|变量值|否|
|fieldDescribe|变量描述|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|


*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data":""
    }
---

### 10.禁用配置 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}//system/setting/closeSetting`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|id|变量id|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|


*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data":""
    }
---

### 11.启用配置 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}//system/setting/openSetting`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|id|变量id|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|


*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data":""
    }
---
### 12.获取用户信息(移动端) ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}//mobile/card/getUserInfo`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|
|userId|用户id|
|username|用户名|
|depositState|押金状态 0 未交纳1待支付2已支付3退款中4已退款|
|balance|余额|
|idCardState|身份认证 0未认证，1审核中 2已审核 3认证失败|
|driverState|驾驶认证 0未认证，1审核中 2已审核 3认证失败|
|halfUser|是否5折用户 0否 1是|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }       

正确返回：

    {
        "code": 1,
        "message": "成功",
        "data": {
            "userId": 4,
            "username": "18280489256",
            "depositState": 0,
            "balance": 5,
            "idCardState": 0,
            "driverState": 0
        }
    }
---
### 13.获取用户列表 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/system/card/getUserList`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|username|用户账号|是|
|realName|用户真实姓名|是|
|cardNumber|用户身份证号码|是|
|depositState|0 未交押金 1已交押金|是|
|cardState|身份认证状态 0未认证，1审核中 2已审核 3认证失败|是|
|driverState|驾驶证认证状态 0未认证，1审核中 2已审核 3认证失败|
|beginDate|时间起点|是|
|endDate|时间结束点|是|
|pageSize|每页数量|否|
|pageNumber|第几页|否|
|halfUser|是否5折用户 0否1是|是|


* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|
|pageNum|当前页数|
|pageSize|每页条数|
|total|总条数|
|pages|总页数|
|userId|用户id|
|username|用户名|
|state|用户状态 0禁用 1正常|
|depositState|押金状态 0 未交纳1待支付2已支付3退款中4已退款|
|balance|余额|
|idCardState|身份认证 0未认证，1审核中 2已审核 3认证失败|
|driverState|驾驶认证 0未认证，1审核中 2已审核 3认证失败|
|createTime|创建时间|
|realName|真实姓名|
|cardNumber|身份证号码|
|halfUser|是否5折用户 0否1是|
|giveBalance|赠送余额|
|handCardPhoto|手持身份证照片url|
|identityCardPhotoFront|身份证正面url|
|identityCardPhotoBehind|身份证背面url|
|driverLicencePhotoMaster|驾驶证正本url|
|driverLicencePhotoSlave|驾驶证副本url|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }       

正确返回：

    {
        "code":1,
        "message":"成功",
        "data":
            {   
                "pageNum":1,
                "pageSize":3,
                "total":30,
                "pages":10,
                    "list":
                        [{
                        "userId":42,
                        "username":"test_admin2",
                        "state":1,
                        "depositState":0,
                        "balance":0,
                        "giveBalance":0,
                        "idCardState":0,
                        "driverState":0,
                        "createTime":"2018-08-26 21:33:02",
                        "realName":"",
                        "cardNumber":"",
                        "halfUser":0,
                        "handCardPhoto":"",
                        "identityCardPhotoFront":"",
                        "identityCardPhotoBehind":"",
                        "driverLicencePhotoMaster":"",
                        "driverLicencePhotoSlave":""
                        },
                        {"userId":41,
                        "username":"test_admin",
                        "state":1,
                        "depositState":0,
                        "balance":0,
                        "giveBalance":0,
                        "idCardState":0,
                        "driverState":0,
                        "createTime":"2018-08-26 21:18:17",
                        "realName":"",
                        "cardNumber":"",
                        "halfUser":0,
                        "handCardPhoto":"",
                        "identityCardPhotoFront":"",
                        "identityCardPhotoBehind":"",
                        "driverLicencePhotoMaster":"",
                        "driverLicencePhotoSlave":""},
                        {
                        "userId":40,
                        "username":"18628028853",
                        "state":1,
                        "depositState":2,
                        "balance":1,
                        "giveBalance":0,
                        "idCardState":1,
                        "driverState":1,
                        "createTime":"2018-08-26 11:31:30",
                        "realName":"周三",
                        "cardNumber":"13425678",
                        "halfUser":0,
                        "handCardPhoto":"http://pdaxdtr0a.bkt.clouddn.com/3eac1558b93c468d8a8841f493abf122.png",
                        "identityCardPhotoFront":"http://pdaxdtr0a.bkt.clouddn.com/587ad0b6ef18438596cc88de8bddb5a4.png",
                        "identityCardPhotoBehind":"http://pdaxdtr0a.bkt.clouddn.com/3d3b221aa2354b5f91dfce23e2c16586.png",
                        "driverLicencePhotoMaster":"http://pdaxdtr0a.bkt.clouddn.com/7ef4e23175124fc9a7a29f62c8bc23cb.png",
                        "driverLicencePhotoSlave":"http://pdaxdtr0a.bkt.clouddn.com/c318e973dae644baa2f3e98d3d352379.png"
                    }]
            }
        }


---

### 14.判断是否强制升级 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/card/forbidVersion`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|


* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据 0 不强制升级 1强制升级|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }       

正确返回：

    {
        "code": 1,
        "message": "成功",
        "data": 0
    }
---
### 15.获取用户信息(后台) ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/system/card/userInfo/{userId}`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|userId|用户id|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态描述|
|data|返回数据|
|userId|用户id|
|username|用户名|
|realName|真实姓名|
|cardNumber|身份证号码|
|depositState|押金状态 0 未交纳1待支付2已支付3退款中4已退款|
|balance|余额|
|idCardState|身份认证 0未认证，1审核中 2已审核 3认证失败|
|driverState|驾驶认证 0未认证，1审核中 2已审核 3认证失败|
|halfUser|是否5折用户 0否 1是|
|giveBalance|赠送余额|
|handCardPhoto|手持身份证照片url|
|identityCardPhotoFront|身份证正面url|
|identityCardPhotoBehind|身份证背面url|
|driverLicencePhotoMaster|驾驶证正本url|
|driverLicencePhotoSlave|驾驶证副本url|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }       

正确返回：

    {
            "code":1,
            "message":"成功",
            "data":
                {
                    "userId":4,
                    "username":"18280489256",
                    "depositState":2,
                    "balance":1,
                    "giveBalance":0,
                    "idCardState":2,
                    "driverState":2,
                    "createTime":0,
                    "realName":"",
                    "cardNumber":"",
                    "halfUser":0,
                    "handCardPhoto":"http://pdaxdtr0a.bkt.clouddn.com/www.baidu.com",
                    "identityCardPhotoFront":"http://pdaxdtr0a.bkt.clouddn.com/www.360.com",
                    "identityCardPhotoBehind":"http://pdaxdtr0a.bkt.clouddn.com/www.qq.com",
                    "driverLicencePhotoMaster":"http://pdaxdtr0a.bkt.clouddn.com/ca04df8e72944180ad6d507dc65974bb.png",
                    "driverLicencePhotoSlave":"http://pdaxdtr0a.bkt.clouddn.com/bcdbee9d30d04b9099c8b80b0a6ad222.png"
                }
        }
---
