# EDrive

#### 模块说明：
支付模块
- - -

## ==================以下为支付模块接口文档===================

### 1.支付订单（余额支付） ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/mobile/pay/payOrderByBalance`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|orderNum|订单号|否|

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

示例如下:

正确返回：

    {
       "code": 1,
       "message": "成功",
       "data": ""
    }

---
### 2.我的钱包 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/pay/myWallet`

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
|message|状态信息|
|data|返回数据|
|userId|用户id|
|userBalance|用户余额 单位 分|
|preferentialAmount|优惠券数量|
|giveBalance|用户赠送余额 单位 分|

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
        "userId": 1,
        "userBalance": 15,
        "preferentialAmount": 3,
        "carIncome": 1,
        "giveBalance":0
      }
    }
    
---

### 3.行程订单微信支付统一下单获取预支付ID ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/mobile/wechatpay/getPrepayId/tripOrder/{orderNum}`

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

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

示例如下:

正确返回：

    {
     "code": 1012,
     "message": "微信统一订单下单成功",
     "data": {
       "appId": "wx1eec87a2ca1af7ce",
       "noncestr": "aW5zBXb",
       "package": "Sign=WXPay",
       "partnerid": "1226934302",
       "prepayid": "wx19003442621250912e5526b22540104581",
       "sign": "FEDC873610A7F87C6FD3F1F554D8A33D",
       "timestamp": "1534610083"
     }
    }
   
  ---
  
  ### 4.充值订单微信支付统一下单获取预支付ID ###
  
  * 接口调用请求说明
  
  	http请求方式: POST
  
    url: `{serverUrl}/mobile/wechatpay/getPrepayId/rechargeOrder/{orderNum}`
  
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
  
  *header示例
  
      {
         "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
         "Accept":"application/json"
      }
  
  示例如下:
  
  正确返回：
  
      {
       "code": 1012,
       "message": "微信统一订单下单成功",
       "data": {
         "appId": "wx1eec87a2ca1af7ce",
         "noncestr": "aW5zBXb",
         "package": "Sign=WXPay",
         "partnerid": "1226934302",
         "prepayid": "wx19003442621250912e5526b22540104581",
         "sign": "FEDC873610A7F87C6FD3F1F554D8A33D",
         "timestamp": "1534610083"
       }
      }
     
   ---
   
  ### 5.指定行程订单订单号，获取支付宝支付参数 ###
  
  * 接口调用请求说明
  
  	http请求方式: POST
  
    url: `{serverUrl}/mobile/alipay/getPayOrder/tripOrder/{orderNum}`
  
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
  
  *header示例
  
      {
         "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
         "Accept":"application/json"
      }
  
  示例如下:
  
  正确返回：
  
     {
       "code": 1,
       "message": "成功",
       "data": "alipay_sdk=alipay-sdk-java-3.1.0
       &app_id=2018062760448255&biz_content=%7B%22o
       ut_trade_no%22%3A%2223881389%22%2C%22
       passback_params%22%3A%22171.209.100.186%
       22%2C%22product_code%22%3A%22QUICK_MSECUR
       ITY_PAY%22%2C%22subject%22%3A%22%E5%86%8D
       %E8%A7%81%E5%87%BA%E8%A1%8C%E8%A1%8C%
       E7%A8%8B%E8%AE%A2%E5%8D%95-23881389%2
       2%2C%22timeout_express%22%3A%2230m%22%2C
       %22total_amount%22%3A%220.01%22%7D&charset=utf-8
       &format=json&method=alipay.trade.app.pay&notify
       _url=http%3A%2F%2F02a0ecff.ngrok.io%2Fmobile%2
       Fpay%2FtripOrder%2FalipayNotify&sign=Ob%2FudWZ
       MF7hMDWC9SqHlEa4z%2BJHI1ua7nuPfvJ0KVPAOlW69hq1
       VhvcBGo%2F8j9I%2FKCzNOXgqQ36VgP%2Fa1n3xZ5eqp%2F
       Zmky0Db60fhP39inlZviRKlglEKoJDIf7gjUoZaQtetFTj7w
       zupd4JGRHKeZnmr5N8RcLWrwXN9M5ATagirhzLsFVpoInVod
       vZ%2BVlL%2BA%2FK%2FZ6rBAm3wBRqJSlXRN2i3bjDBaORX
       7%2BgzpZ4Z4w4UJKBxhrBxJ03t7OS4I8WuOT56CSDydnG3
       Jc0cuwzOirMyTjyxG2bk4oHCAZB4WAAq30CsXENlupX8nf
       vRrqopDGW0lCPML2JfQnjZZEo%2FA%3D%3D&sign_type=RSA2
       &timestamp=2018-08-19+18%3A08%3A25&version=1.0"
     }
     
   ---
   
  ### 6.指定行程订单号，获取支付宝支付参数 ###
  
  * 接口调用请求说明
  
  	http请求方式: POST
  
    url: `{serverUrl}/mobile/alipay/getPayOrder/tripOrder/{orderNum}`
  
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
  
  *header示例
  
      {
         "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
         "Accept":"application/json"
      }
  
  示例如下:
  
  正确返回：
  
     {
       "code": 1,
       "message": "成功",
       "data": "alipay_sdk=alipay-sdk-java-3.1.0
       &app_id=2018062760448255&biz_content=%7B%22o
       ut_trade_no%22%3A%2223881389%22%2C%22
       passback_params%22%3A%22171.209.100.186%
       22%2C%22product_code%22%3A%22QUICK_MSECUR
       ITY_PAY%22%2C%22subject%22%3A%22%E5%86%8D
       %E8%A7%81%E5%87%BA%E8%A1%8C%E8%A1%8C%
       E7%A8%8B%E8%AE%A2%E5%8D%95-23881389%2
       2%2C%22timeout_express%22%3A%2230m%22%2C
       %22total_amount%22%3A%220.01%22%7D&charset=utf-8
       &format=json&method=alipay.trade.app.pay&notify
       _url=http%3A%2F%2F02a0ecff.ngrok.io%2Fmobile%2
       Fpay%2FtripOrder%2FalipayNotify&sign=Ob%2FudWZ
       MF7hMDWC9SqHlEa4z%2BJHI1ua7nuPfvJ0KVPAOlW69hq1
       VhvcBGo%2F8j9I%2FKCzNOXgqQ36VgP%2Fa1n3xZ5eqp%2F
       Zmky0Db60fhP39inlZviRKlglEKoJDIf7gjUoZaQtetFTj7w
       zupd4JGRHKeZnmr5N8RcLWrwXN9M5ATagirhzLsFVpoInVod
       vZ%2BVlL%2BA%2FK%2FZ6rBAm3wBRqJSlXRN2i3bjDBaORX
       7%2BgzpZ4Z4w4UJKBxhrBxJ03t7OS4I8WuOT56CSDydnG3
       Jc0cuwzOirMyTjyxG2bk4oHCAZB4WAAq30CsXENlupX8nf
       vRrqopDGW0lCPML2JfQnjZZEo%2FA%3D%3D&sign_type=RSA2
       &timestamp=2018-08-19+18%3A08%3A25&version=1.0"
     }
     
   ---
   
  ### 7.指定充值订单号，获取支付宝支付参数 ###
  
  * 接口调用请求说明
  
  	http请求方式: POST
  
    url: `{serverUrl}/mobile/alipay/getPrepayId/rechargeOrder/{orderNum}`
  
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
  
  *header示例
  
      {
         "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
         "Accept":"application/json"
      }
  
  示例如下:
  
  正确返回：
  
     {
       "code": 1,
       "message": "成功",
       "data": "alipay_sdk=alipay-sdk-java-3.1.0
       &app_id=2018062760448255&biz_content=%7B%22o
       ut_trade_no%22%3A%2223881389%22%2C%22
       passback_params%22%3A%22171.209.100.186%
       22%2C%22product_code%22%3A%22QUICK_MSECUR
       ITY_PAY%22%2C%22subject%22%3A%22%E5%86%8D
       %E8%A7%81%E5%87%BA%E8%A1%8C%E8%A1%8C%
       E7%A8%8B%E8%AE%A2%E5%8D%95-23881389%2
       2%2C%22timeout_express%22%3A%2230m%22%2C
       %22total_amount%22%3A%220.01%22%7D&charset=utf-8
       &format=json&method=alipay.trade.app.pay&notify
       _url=http%3A%2F%2F02a0ecff.ngrok.io%2Fmobile%2
       Fpay%2FtripOrder%2FalipayNotify&sign=Ob%2FudWZ
       MF7hMDWC9SqHlEa4z%2BJHI1ua7nuPfvJ0KVPAOlW69hq1
       VhvcBGo%2F8j9I%2FKCzNOXgqQ36VgP%2Fa1n3xZ5eqp%2F
       Zmky0Db60fhP39inlZviRKlglEKoJDIf7gjUoZaQtetFTj7w
       zupd4JGRHKeZnmr5N8RcLWrwXN9M5ATagirhzLsFVpoInVod
       vZ%2BVlL%2BA%2FK%2FZ6rBAm3wBRqJSlXRN2i3bjDBaORX
       7%2BgzpZ4Z4w4UJKBxhrBxJ03t7OS4I8WuOT56CSDydnG3
       Jc0cuwzOirMyTjyxG2bk4oHCAZB4WAAq30CsXENlupX8nf
       vRrqopDGW0lCPML2JfQnjZZEo%2FA%3D%3D&sign_type=RSA2
       &timestamp=2018-08-19+18%3A08%3A25&version=1.0"
     }
     
   ---
   
  ### 8.指定押金单号，获取支付宝支付参数 ###
  
  * 接口调用请求说明
  
  	http请求方式: POST
  
    url: `{serverUrl}/mobile/alipay/getPrepayId/depositOrder/{orderNum}`
  
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
  
  *header示例
  
      {
         "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
         "Accept":"application/json"
      }
  
  示例如下:
  
  正确返回：
  
     {
       "code": 1,
       "message": "成功",
       "data": "alipay_sdk=alipay-sdk-java-3.1.0
       &app_id=2018062760448255&biz_content=%7B%22o
       ut_trade_no%22%3A%2223881389%22%2C%22
       passback_params%22%3A%22171.209.100.186%
       22%2C%22product_code%22%3A%22QUICK_MSECUR
       ITY_PAY%22%2C%22subject%22%3A%22%E5%86%8D
       %E8%A7%81%E5%87%BA%E8%A1%8C%E8%A1%8C%
       E7%A8%8B%E8%AE%A2%E5%8D%95-23881389%2
       2%2C%22timeout_express%22%3A%2230m%22%2C
       %22total_amount%22%3A%220.01%22%7D&charset=utf-8
       &format=json&method=alipay.trade.app.pay&notify
       _url=http%3A%2F%2F02a0ecff.ngrok.io%2Fmobile%2
       Fpay%2FtripOrder%2FalipayNotify&sign=Ob%2FudWZ
       MF7hMDWC9SqHlEa4z%2BJHI1ua7nuPfvJ0KVPAOlW69hq1
       VhvcBGo%2F8j9I%2FKCzNOXgqQ36VgP%2Fa1n3xZ5eqp%2F
       Zmky0Db60fhP39inlZviRKlglEKoJDIf7gjUoZaQtetFTj7w
       zupd4JGRHKeZnmr5N8RcLWrwXN9M5ATagirhzLsFVpoInVod
       vZ%2BVlL%2BA%2FK%2FZ6rBAm3wBRqJSlXRN2i3bjDBaORX
       7%2BgzpZ4Z4w4UJKBxhrBxJ03t7OS4I8WuOT56CSDydnG3
       Jc0cuwzOirMyTjyxG2bk4oHCAZB4WAAq30CsXENlupX8nf
       vRrqopDGW0lCPML2JfQnjZZEo%2FA%3D%3D&sign_type=RSA2
       &timestamp=2018-08-19+18%3A08%3A25&version=1.0"
     }
     
   ---
   
  ### 9.押金退还(支付宝与微信通用) ###
  
  * 接口调用请求说明
  
  	http请求方式: POST
  
    url: `{serverUrl}/mobile/refund/{orderNum}`
  
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
  
  *header示例
  
      {
         "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
         "Accept":"application/json"
      }
  
  示例如下:
  
  正确返回：
  
     {
       "code": 1,
       "message": "退款成功",
       "data": ""
     }
     
   ---
   
  ### 10.指定押金单号，获取微信支付参数 ###
     
   * 接口调用请求说明
     
      http请求方式: POST
     
      url: `{serverUrl}/mobile/wechatpay/getPrepayId/depositOrder/{orderNum}`
     
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
     
   *header示例
     
         {
            "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
            "Accept":"application/json"
         }
     
   示例如下:
     
   正确返回：
     
        {
               "code": 1012,
               "message": "微信统一订单下单成功",
               "data": {
                 "appId": "wx1eec87a2ca1af7ce",
                 "noncestr": "aW5zBXb",
                 "package": "Sign=WXPay",
                 "partnerid": "1226934302",
                 "prepayid": "wx19003442621250912e5526b22540104581",
                 "sign": "FEDC873610A7F87C6FD3F1F554D8A33D",
                 "timestamp": "1534610083"
               }
              }
        
   ---

   ## ==================以下为充值模块接口文档===================

   ### 1.生成充值订单 ###
     
   * 接口调用请求说明
     
      http请求方式: POST
     
      url: `{serverUrl}/mobile/recharge/create`
     
   * 输入参数说明
     
     |参数|描述|是否可为空|
     |----|--- |--------|
     |amount|充值金额 |否|
     
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
     
   示例如下:
     
   正确返回：
     
        {
          "code": 1,
          "message": "成功",
          "data": {
            "id": 7,
            "createTime": "2018-08-19 08:12:45",
            "updateTime": "2018-08-19 08:12:45",
            "number": "537401369161245148003",
            "userId": 1,
            "userFundId": 1,
            "amount": 100,
            "state": 0,
            "payType": 0,
            "payTime": 0
          }
        } 
      
  ---
  
  ### 2.获取用户充值记录 ###
       
  * 接口调用请求说明
       
      http请求方式: GET
       
      url: `{serverUrl}/mobile/recharge/getRechargesByUserId`
       
  * 输入参数说明
       
      |参数|描述|是否可为空|
      |----|--- |--------|
      |amount|充值金额(单位 分) |否|
       
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
       
  * header示例
       
       
       {
          "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
          "Accept":"application/json"
         }
       
  示例如下:
       
  正确返回：
       
          {
            "code": 1,
            "message": "成功",
            "data": [
              {
                "id": 8,
                "createTime": "2018-08-19 08:22:25",
                "updateTime": "2018-08-19 08:22:25",
                "number": "537401369162224964000",
                "userId": 1,
                "userFundId": 1,
                "amount": 100,
                "state": 0,
                "payType": 0,
                "payTime": 0
              },
              {
                "id": 7,
                "createTime": "2018-08-19 08:12:45",
                "updateTime": "2018-08-19 08:12:45",
                "number": "537401369161245148003",
                "userId": 1,
                "userFundId": 1,
                "amount": 100,
                "state": 0,
                "payType": 0,
                "payTime": 0
              }
            ]
          }
        
  ---
  
  ### 3.充值订单条件查询 ###
       
  * 接口调用请求说明
       
      http请求方式: GET
       
      url: `{serverUrl}system/recharge/findRechargeRecords`
       
  * 输入参数说明
       
     |参数|描述|是否可为空|
      |----|--- |--------|
     |id|充值订单id |是|
     |payType|支付类型 0余额1支付宝2微信 |是|
     |startTime|时间区间最小值|是|
     |endTime|时间区间最大值|是|

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
       
  * header示例
       
       
       {
          "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
          "Accept":"application/json"
         }
       
  示例如下:
       
  正确返回：
       
          {
            "code": 1,
            "message": "成功",
            "data": [
              {
                "id": 8,
                "createTime": "2018-08-19 08:22:25",
                "updateTime": "2018-08-19 08:22:25",
                "number": "537401369162224964000",
                "userId": 1,
                "userFundId": 1,
                "amount": 100,
                "state": 0,
                "payType": 0,
                "payTime": 0
              },
              {
                "id": 7,
                "createTime": "2018-08-19 08:12:45",
                "updateTime": "2018-08-19 08:12:45",
                "number": "537401369161245148003",
                "userId": 1,
                "userFundId": 1,
                "amount": 100,
                "state": 0,
                "payType": 0,
                "payTime": 0
              }
            ]
          }   
          
  ---
  ### 4.查询用户押金信息 ###
         
* 接口调用请求说明
         
        http请求方式: GET
         
        url: `{serverUrl}/mobile/deposit/userDeposit`
         
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
         |id|id|
         |userId|用户id|
         |zmScore|芝麻分数|
         |type|押金类型 0现金 1花呗 2 芝麻|
         |amount|押金金额 单位 分|
         |payState|押金状态 0 未交纳1待支付2已支付3退款中4已退款|
         |payType|支付类型 1支付宝支付 2微信|
         |orderNum|押金单号|
         |payTime|支付时间|
         |createTime|创建时间|
         |updateTime|更新时间|
         
* header示例
         
         
         {
            "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
            "Accept":"application/json"
           }
         
* 示例如下:
         
        正确返回：
         
            {
              "code": 1,
              "message": "成功",
              "data": 
                {
                  "userId":122,
                  "zmScore":0,
                  "type":0, 
                  "amount":66800,
                  "payState":1,
                  "payType":1,
                  "orderNum":"115522",
                  "payTimee":"",
                  "createTime":"2018-08-19 08:12:45",
                  "updateTime":"2018-08-19 08:12:45"
                }
            }
            
  ---
  ### 5.创建交纳押金订单 ###
           
* 接口调用请求说明
           
          http请求方式: GET
           
          url: `{serverUrl}/mobile/deposit/createDeposit`
           
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
           |orderNum|押金单号|
           
* header示例
           
           {
              "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
              "Accept":"application/json"
             }
           
* 示例如下:
           
      正确返回：
           
              {
                "code": 1,
                "message": "成功",
                "data": 
                  {
                    "orderNum":"115522"
                  }
              }
              
    ---
    
  ### 6.退押金 ###
             
* 接口调用请求说明
           
          http请求方式: GET
           
          url: `{serverUrl}/mobile/deposit/refundDeposit`
           
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
           |orderNum|押金单号|
           
      *header示例
           
           {
              "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
              "Accept":"application/json"
             }
           
      示例如下:
           
      正确返回：
           
              {
                "code": 1,
                "message": "成功",
                "data": 
                  {
                    "orderNum":"115522"
                  }
              }
                      
  ---
### 7.获取押金额度 ###
               
* 接口调用请求说明
           
   http请求方式: GET
    
   url: `{serverUrl}/mobile/deposit/getDepositAmount`
           
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
  |data|押金额度 单位分|
           
* header示例
           
           {
              "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
              "Accept":"application/json"
             }
           
        示例如下:
           
        正确返回：
           
              {
                "code": 1,
                "message": "成功",
                "data": "68800"
              }
              
 ---
### 8.获取不计免赔额度 ###
              
  * 接口调用请求说明
             
            http请求方式: GET
             
            url: `{serverUrl}/mobile/deposit/getDeductibles`
             
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
             |data|免赔额度 单位分|
             
  *header示例
             
             {
                "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
                "Accept":"application/json"
               }
             
        示例如下:
             
        正确返回：
             
                {
                  "code": 1,
                  "message": "成功",
                  "data": "10000"
                }
                
 ---
              