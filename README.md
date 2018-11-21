# EDrive

#### 项目说明：

租车项目

#### 调试说明：


1. clone出本项目

	```
	git clone git@gitee.com:goodbye_travel/EDrive.git
	```
2. 在EDrive目录下运行maven相关命令进行编译打包

- - -

## ======================以下为接口文档=======================

### 获取手机验证码 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/code/sms`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|mobile|手机号码|否|

* 输出参数说明

|参数|描述|
|----|--- |
|result|发送状态|

* 错误码集合：

|错误码(errorCode)|描述(errorMsg)|
|----|--- |
|-1|系统异常！请联系系统管理员|
|10001|请求参数错误|

* url示例:`{serverUrl}/code/sms?mobile=18011225568`

* Header数据

示例如下:

    headers:

    {
       "Accept":"application/json"
    }

正确返回：

    {
        "result": "ok"
    }

---

### 推荐注册接口 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/system/user/register`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|mobile|手机号码|否|
|referral|推荐人用户名|否|
|smsCode|手机验证码|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Content-Type|application/x-www-form-urlencoded|否|


* 输出参数说明

|参数|描述|
|----|--- |
|id|用户id|
|roleId|角色id|
|username|用户名|
|createdTime|创建时间|


* Header数据

示例如下:

    headers:

    {
       "Content-Type":"application/x-www-form-urlencoded",
       "Accept":"application/json"
    }


正确返回：

    {
        "id": 33,
        "roleId": 4,
        "username": "18011227798",
        "createdTime": 1534666651292
    }

---

### 登录注册接口 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/oauth/mobile`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|mobile|手机号码|否|
|smsCode|手机验证码|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Basic + Base64.encode(clientId + ':' + clientSecret),Basic后面有空格)|否|


* 输出参数说明

|参数|描述|
|----|--- |
|access_token|访问令牌|
|token_type|令牌类型，该值大小写不敏感，默认为bearer。|
|refresh_token|刷新令牌，用来获取下一次的访问令牌。|
|expires_in|访问令牌有效时间，单位为秒。|
|scope|权限范围，此值后台固定为"all"(前端不用关注此字段)。|
|jti|jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击(前端不用关注此字段)。|


* Header数据

示例如下:

    headers:

    {
       "Authorization":"Basic dGVzdDp0ZXN0",
       "Content-Type":"application/x-www-form-urlencoded",
       "Accept":"application/json"
    }


正确返回：

    {
        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTUxMzg3ODUsInVzZXJfbmFtZSI6InJvb3QiLCJqdGkiOiJhNzU3ODQwYi1lMzk4LTQyZmMtYTY4MC1kZmE2Mjk0ODM3YTciLCJjbGllbnRfaWQiOiJ0ZXN0Iiwic2NvcGUiOlsiYWxsIl19.qk-Xe3Ql7ZFrhd3D3RocwxkNNMcRl_juKRBMCNT42ps",
        "token_type": "bearer",
        "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTc3MjM1ODUsInVzZXJfbmFtZSI6InJvb3QiLCJqdGkiOiI3M2JjNDRkNy01ZWJlLTRiNjItOTAyMy02OWQ3M2E5MjE2NzIiLCJjbGllbnRfaWQiOiJ0ZXN0Iiwic2NvcGUiOlsiYWxsIl0sImF0aSI6ImE3NTc4NDBiLWUzOTgtNDJmYy1hNjgwLWRmYTYyOTQ4MzdhNyJ9.FKgCTE24IvqMnpjQj8sUlp3V92QvyfSvR2mkb-kyIIU",
        "expires_in": 7199,
        "scope": "all",
        "jti": "a757840b-e398-42fc-a680-dfa6294837a7"
    }

---
### 用户名密码方式登录接口 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/oauth/token`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|grant_type|授权类型，此处固定为"password"|否|
|username|用户名|否|
|password|密码|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Basic + Base64.encode(clientId + ':' + clientSecret),Basic后面有空格)|否|
|Content-Type|application/x-www-form-urlencoded|否|


* 输出参数说明

|参数|描述|
|----|--- |
|access_token|访问令牌|
|token_type|令牌类型，该值大小写不敏感，默认为bearer。|
|refresh_token|刷新令牌，用来获取下一次的访问令牌。|
|expires_in|访问令牌有效时间，单位为秒。|
|scope|权限范围，此值后台固定为"all"(前端不用关注此字段)。|
|jti|jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击(前端不用关注此字段)。|


* Header数据

示例如下:

    headers:

    {
       "Authorization":"Basic dGVzdDp0ZXN0",
       "Content-Type":"application/x-www-form-urlencoded",
       "Accept":"application/json"
    }


正确返回：

    {
        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTUxMzg3ODUsInVzZXJfbmFtZSI6InJvb3QiLCJqdGkiOiJhNzU3ODQwYi1lMzk4LTQyZmMtYTY4MC1kZmE2Mjk0ODM3YTciLCJjbGllbnRfaWQiOiJ0ZXN0Iiwic2NvcGUiOlsiYWxsIl19.qk-Xe3Ql7ZFrhd3D3RocwxkNNMcRl_juKRBMCNT42ps",
        "token_type": "bearer",
        "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTc3MjM1ODUsInVzZXJfbmFtZSI6InJvb3QiLCJqdGkiOiI3M2JjNDRkNy01ZWJlLTRiNjItOTAyMy02OWQ3M2E5MjE2NzIiLCJjbGllbnRfaWQiOiJ0ZXN0Iiwic2NvcGUiOlsiYWxsIl0sImF0aSI6ImE3NTc4NDBiLWUzOTgtNDJmYy1hNjgwLWRmYTYyOTQ4MzdhNyJ9.FKgCTE24IvqMnpjQj8sUlp3V92QvyfSvR2mkb-kyIIU",
        "expires_in": 7199,
        "scope": "all",
        "jti": "a757840b-e398-42fc-a680-dfa6294837a7"
    }

---
### 刷新token接口 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/oauth/token`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|grant_type|授权类型，此处固定为"refresh_token"|否|
|refresh_token|	登录时收到的刷新令牌|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Basic + Base64.encode(clientId + ':' + clientSecret),Basic后面有空格)|否|
|Content-Type|application/x-www-form-urlencoded|否|

* 输出参数说明

|参数|描述|
|----|--- |
|access_token|访问令牌|
|token_type|令牌类型，该值大小写不敏感，默认为bearer。|
|refresh_token|刷新令牌，用来获取下一次的访问令牌。|
|expires_in|访问令牌有效时间，单位为秒。|
|scope|权限范围，此值后台固定为"all"(前端不用关注此字段)。|
|jti|jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击(前端不用关注此字段)。|


* Header数据

示例如下:

    headers:

    {
       "Authorization":"Basic dGVzdDp0ZXN0",
       "Content-Type":"application/x-www-form-urlencoded",
       "Accept":"application/json"
    }


正确返回：

    {
        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTUxMzg3ODUsInVzZXJfbmFtZSI6InJvb3QiLCJqdGkiOiJhNzU3ODQwYi1lMzk4LTQyZmMtYTY4MC1kZmE2Mjk0ODM3YTciLCJjbGllbnRfaWQiOiJ0ZXN0Iiwic2NvcGUiOlsiYWxsIl19.qk-Xe3Ql7ZFrhd3D3RocwxkNNMcRl_juKRBMCNT42ps",
        "token_type": "bearer",
        "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTc3MjM1ODUsInVzZXJfbmFtZSI6InJvb3QiLCJqdGkiOiI3M2JjNDRkNy01ZWJlLTRiNjItOTAyMy02OWQ3M2E5MjE2NzIiLCJjbGllbnRfaWQiOiJ0ZXN0Iiwic2NvcGUiOlsiYWxsIl0sImF0aSI6ImE3NTc4NDBiLWUzOTgtNDJmYy1hNjgwLWRmYTYyOTQ4MzdhNyJ9.FKgCTE24IvqMnpjQj8sUlp3V92QvyfSvR2mkb-kyIIU",
        "expires_in": 7199,
        "scope": "all",
        "jti": "a757840b-e398-42fc-a680-dfa6294837a7"
    }

---
### 获取用户信息 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/system/user/me`

* 输入参数说明

无

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|


* 输出参数说明

|参数|描述|
|----|--- |
|id|用户id|
|username|用户名|
|createdTime|注册时间|
|rolesId|拥有角色id|

* Header数据

示例如下:

    headers:

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

正确返回：

    {
        "id": 3,
        "username": "18011225568",
        "createdTime": 1530945459000,
        "rolesId": [
            4
        ]
    }

---
### 群发短信 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/system/sms`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|mobiles|手机号码,数组|是|
|content|短信内容|否|

    注：不传mobiles字段，或者mobiles字段length为0，将发送所有注册用户

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|


* 输出参数说明

|参数|描述|
|----|--- |
|result|发送状态|

* url示例:`{serverUrl}/system/sms`

* Header数据

示例如下:

    headers:

    {
    	"Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
    	"Accept":"application/json"
    }

    body:
    
    {
    	"mobiles":["18011225566","18933558877","15033221133"],
    	"content":"测试短信"
    }

正确返回：

    {
        "result": "ok"
    }

---
### 检查用户是否存在 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/system/user/check/{username}`

* 输出参数说明

|参数|描述|
|----|--- |
|exist|用户是否存在|

* url示例:`{serverUrl}/system/user/check/18011225568`

正确返回：

    {
        "exist": true
    }

---
### 添加管理员 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/system/user/create_admin`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|username|用户名|否|
|password|密码|是|

    注：password字段可以不传，不传值时密码为123456

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|


* 输出参数说明

|参数|描述|
|----|--- |
|id|用户id|
|roleId|角色id|
|username|用户名|
|createdTime|创建时间|

* Header数据

示例如下:

    headers:

    {
    	"Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
    	"Content-Type":"application/json"
    }

    body:
    
    {
    	"username":"test_admin2",
    	"password":"1234567"
    }

正确返回：

    {
        "id": 42,
        "roleId": 1,
        "username": "test_admin2",
        "createdTime": 1535290382024
    }

---
### 修改密码 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/system/user/update_password`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|oldPassword|老密码|否|
|newPassword|新密码|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|


* 输出参数说明

|参数|描述|
|----|--- |
|id|用户id|
|roleId|角色id|
|username|用户名|
|createdTime|创建时间|

* Header数据

示例如下:

    headers:

    {
    	"Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
    	"Content-Type":"application/json"
    }

    body:
    
    {
    	"oldPassword":"012345",
    	"newPassword":"123456"
    }

正确返回：

    {
        "id": 42,
        "roleId": 1,
        "username": "test_admin2",
        "createdTime": 1535290382024
    }

---


### 获取设备密码信息(键盘密码和蓝牙密码) ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/device/password`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|carId|车辆id|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|


* 输出参数说明

|参数|描述|
|----|--- |
|id|id|
|deviceId|设备id|
|keyboardPassword|键盘密码|
|bluePassword|蓝牙密码|

* url示例:`{serverUrl}/mobile/device/password?carId=27`

* Header数据

示例如下:

    headers:

    {
    	"Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
    	"Content-Type":"application/json"
    }

正确返回：

    {
        "id": 42,
        "deviceId": "211800109813",
        "keyboardPassword": "12345678",
        "bluePassword": 12345678
    }

---

### 设置设备键盘密码 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/mobile/device/password/keyboard`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|deviceId|设备id|否|
|password|键盘密码|否|

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

* Header数据

示例如下:

    headers:

    {
    	"Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
    	"Content-Type":"application/json"
    }

    body:
    
    {
    	"deviceId":"211800109813",
    	"password":"12345678"
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data":""
    }

---

### 重置蓝牙密码 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/mobile/device/password/blue`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|carId|车辆id|否|
|bluePassword|蓝牙密码|否|

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

* Header数据

示例如下:

    headers:

    {
    	"Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
    	"Content-Type":"application/json"
    }

    body:
    
    {
    	"carId":27,
    	"bluePassword":"12345678"
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data":""
    }

---