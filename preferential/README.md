# EDrive

#### 模块说明：
优惠券模块
- - -

## ==================以下为优惠券接口文档===================

### 1.获取用户所有的优惠券###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/preferential/findAllPreferential`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|userId|用户id|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|	OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态信息|
|id|用户优惠券ID|
|discription|优惠描述信息|
|type|优惠券类型 0注册 1 活动 2邀请注册 9其他|
|create_time|创建时间|
|preferential_amount|优惠金额（分）|
|distribute_time|发放时间|
|user_id|用户ID|
|preferential_id|优惠券ID|
|name|优惠券名称|
|state|状态 0 未使用 1已使用|
|validity|有效期 单位 天|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

正确返回：

    {
        "code": 1,
        "message": "成功",
        "data": [
            {
                "discription": "注册赠送优惠券",
                "create_time": 1536027144000,
                "preferential_amount": 5000,
                "type": 0,
                "distribute_time": "2018-09-03",
                "user_id": 276,
                "preferential_id": 2,
                "name": "注册优惠券",
                "id": 1051,
                "state": 0,
                "validity": 3,
            }
        ]
    }
    
   ----
    
  

   
   
