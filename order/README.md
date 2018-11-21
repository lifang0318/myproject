# EDrive

#### 模块说明：
订单模块
- - -

## ==================以下为订单模块接口文档===================

### 1.查询用户未完成订单 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/tripOrder/unfinishedOrder`

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
|id|订单id|
|number|订单号|
|carId|车辆id|
|userId|用户id|
|startParkId|起点停车场id|
|destinationParkId|终点停车场id|
|startTime|行程开始时间|
|endTime|行程结束时间|
|shouldPayAmount|应付金额|
|realPayAmount|实付金额|
|payTime|支付时间|
|payType|支付类型 0：余额支付，1 微信支付 2：支付宝支付|
|mileage|行程里程|
|durationTime|行程时间|
|deductibleStatus|不计免赔 0：未购买 1：已购买|
|status|状态 0：行程中 1：未付款 2：已完成|
|preferentialId|用户优惠券关联id|
|fromPark|起点停车场model|
|toPark|终点停车场model|
|car|车辆model|
|order|订单model|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

示例如下:

传入参数：

正确返回：

     {
       "code": 1,
       "message": "成功",
       "data": {
         "parkFee": 1,
         "payTime": 1534823020000,
         "number": "12344461111111144111111",
         "shouldPayAmount": 10,
         "payType": 1,
         "deductibleStatus": 0,
         "realPayAmount": 5,
         "startTime": 1534838797000,
         "id": 45,
         "mileage": 10,
         "ownerEarnAmount": null,
         "preferentialId": null,
         "durationTime": 5,
         "plateNumber": "川A9999",
         "userId": 12,
         "url": "",
         "carId": 27,
         "destinationParkName": "测试",
         "startParkName": "新通惠酒店地面停车场",
         "deductible": 20,
         "destinationParkId": 43,
         "endTime": 1534752423000,
         "discountId": null,
         "startParkId": 17,
         "status": 1,
         "fromPark": {
                    "latitude": 30.668815,
                    "name": "新通惠酒店地面停车场",
                    "longtitude": 104.0380932,
                    "parkID": 17
                  },
          "toPark": {
                      "latitude": 104.0551736,
                      "name": "测试",
                      "longtitude": 30.6084401,
                      "parkID": 43
                  },   
          "car": {
                      "carColor": "白色",
                       "carType": 0,
                       "maxDistance": 0,
                       "plateNumber": "川A9999",
                       "brand": "大众",
                       "carID": 27
                   },   
          "order": {
                     "orderNumber": "12344461111111144111111",
                     "reservationId": "reservation_car:12",
                     "orderId": 45
                }, 
       }
     }
   
---

### 2.更新订单终点停车场 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}/mobile/tripOrder/updateDestinationPark`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|orderNum|订单号|否|
|destinationParkId|停车场id|否|

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
|id|停车场id|
|address|停车场位置|
|longitude|经度|
|latitude|纬度|
|parkCountBalance|可用停车位数量|
|validCarCount|可用车辆数量|

*状态码
   
    1016：停车场无剩余车位，请重新选择
    1020：停车场不存在


*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

传入参数：
    

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data": {
        "id":18,
        "address":"华阳车站旁边",
        "longitude":53.125425,
        "latitude":104.254245,
        "parkCountBalance":5,
        "validCarCount":2
      }
    }
---

### 3.打开车门 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/mobile/tripOrder/openCar`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|orderId|订单id|否|
|carId|车辆id|否|
|deviceId|设备id|否|

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
### 4.锁车门 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/mobile/tripOrder/lockCar`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|orderId|订单id|否|
|carId|车辆id|否|
|deviceId|设备id|否|

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
### 5.声音寻车 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/mobile/tripOrder/searchCarBySound`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|orderId|订单id|否|
|carId|车辆id|否|
|deviceId|设备id|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|	OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态信息|
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
### 6.用户实际支付金额计算(选择优惠券后,写入数据) ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/mobile/tripOrder/payAmount`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|orderId|订单编号|否|
|preferentialId|用户优惠券关联id|是|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|	OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态信息|
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
         "data": {
             "id": 14,
             "number": "637338",
             "carId": 28,
             "plateNumber": "川A8888",
             "userId": 6,
             "startParkId": 17,
             "startParkName": "天府停车场",
             "destinationParkId": 18,
             "destinationParkName": "华阳停车场",
             "startTime": 1531921748000,
             "endTime": 0,
             "shouldPayAmount": 0,
             "realPayAmount": 1,
             "ownerEarnAmount": 1,
             "payTime": 1532103191000,
             "payType": 2,
             "mileage": 0,
             "durationTime": 0,
             "deductibleStatus": 0,
             "status": 2,
             "discountId": 0,
             "preferentialId": 0，
             "url":"http://pdaxdtr0a.bkt.clouddn.com/537401383125233321000.png"
         }
 
   --- 
   ### 7.根据时间区间查询订单 ###
    
  * 接口调用请求说明
    
    	http请求方式: GET
    
        url: `{serverUrl}/mobile/tripOrder/queryOrdersByTimeRange`
    
  * 输入参数说明
    
    |参数|描述|是否可为空|
    |----|--- |--------|
    |startTime|时间区间最小值(格式 yyyy-MM-dd)|否|
    |endTime|时间区间最大值（格式 yyyy-MM-dd）|否|
    
    
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
  |id|订单id|
  |number|订单号|
  |carId|车辆id|
  |plateNumber|车牌号|
  |userId|用户id|
  |startParkId|起点停车场id|
  |startParkName|起点停车场名称|
  |destinationParkId|终点停车场id|
  |destinationParkName|终点停车场名称|
  |startTime|行程开始时间|
  |endTime|行程结束时间|
  |shouldPayAmount|应付金额|
  |realPayAmount|实付金额|
  |payTime|支付时间|
  |payType|支付类型 0：余额支付，1 微信支付 2：支付宝支付|
  |mileage|行程里程|
  |durationTime|行程时间|
  |deductibleStatus|不计免赔 0：未购买 1：已购买|
  |status|状态 0：行程中 1：未付款 2：已完成|
  |preferentialId|用户优惠券关联id|
  |url|轨迹url|
    
  *header示例
    
        {
           "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
           "Accept":"application/json"
        }
    
  示例如下:
    
  传入参数：
    
  正确返回：
  
    {
         "code": 1,
         "message": "成功",
         "data": [
           {
             "id": 14,
             "number": "637338",
             "carId": 28,
             "plateNumber": "川A8888",
             "userId": 6,
             "startParkId": 17,
             "startParkName": "天府停车场",
             "destinationParkId": 18,
             "destinationParkName": "华阳停车场",
             "startTime": 1531921748000,
             "endTime": 1534348762000,
             "shouldPayAmount": 0,
             "realPayAmount": 1,
             "ownerEarnAmount": 1,
             "payTime": 1532103191000,
             "payType": 2,
             "mileage": 0,
             "durationTime": 0,
             "deductibleStatus": 0,
             "status": 2,
             "discountId": 0,
             "preferentialId": 0，
             "url":"http://pdaxdtr0a.bkt.clouddn.com/537401383125233321000.png"
           }
         ]
     }
       
  ---
  
   ### 8.获取用户所有订单 ###
      
   * 接口调用请求说明
      
      	http请求方式: GET
      
        url: `{serverUrl}/mobile/tripOrder/queryAllOrdersByUser`
      
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
   |id|订单id|
   |number|订单号|
   |carId|车辆id|
   |plateNumber|车牌号|
   |userId|用户id|
   |startParkId|起点停车场id|
   |startParkName|起点停车场名称|
   |destinationParkId|终点停车场id|
   |destinationParkName|终点停车场名称|
   |startTime|行程开始时间|
   |endTime|行程结束时间|
   |shouldPayAmount|应付金额|
   |realPayAmount|实付金额|
   |payTime|支付时间|
   |payType|支付类型 0：余额支付，1 微信支付 2：支付宝支付|
   |mileage|行程里程|
   |durationTime|行程时间|
   |deductibleStatus|不计免赔 0：未购买 1：已购买|
   |status|状态 0：行程中 1：未付款 2：已完成|
   |discountId|折扣id|
   |preferentialId|优惠券id|
   |url|轨迹url|
      
   *header示例
      
          {
             "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
             "Accept":"application/json"
          }
      
   示例如下:
      
   传入参数：
      
   正确返回：
   
         {
           "code": 1,
           "message": "成功",
           "data": [
             {
               "id": 14,
               "number": "637338",
               "carId": 28,
               "plateNumber": "川A8888",
               "userId": 6,
               "startParkId": 17,
               "startParkName": "天府停车场",
               "destinationParkId": 18,
               "destinationParkName": "华阳停车场",
               "startTime": 1531921748000,
               "endTime": 1534348762000,
               "shouldPayAmount": 0,
               "realPayAmount": 1,
               "ownerEarnAmount": 1,
               "payTime": 1532103191000,
               "payType": 2,
               "mileage": 0,
               "durationTime": 0,
               "deductibleStatus": 0,
               "status": 2,
               "discountId": 0,
               "preferentialId": 0，
               "url":"http://pdaxdtr0a.bkt.clouddn.com/537401383125233321000.png"
             }
           ]
         }
          
   ---
   
   ### 9.获取用户已完成订单 ###
         
   * 接口调用请求说明
         
     http请求方式: GET
     
     url: `{serverUrl}/mobile/tripOrder/queryfinishedOrdersByUser`
         
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
       |id|订单id|
       |number|订单号|
       |carId|车辆id|
       |plateNumber|车牌号|
       |userId|用户id|
       |startParkId|起点停车场id|
       |startParkName|起点停车场名称|
       |destinationParkId|终点停车场id|
       |destinationParkName|终点停车场名称|
       |startTime|行程开始时间|
       |endTime|行程结束时间|
       |shouldPayAmount|应付金额|
       |realPayAmount|实付金额|
       |payTime|支付时间|
       |payType|支付类型 0：余额支付，1 微信支付 2：支付宝支付|
       |mileage|行程里程|
       |durationTime|行程时间|
       |deductibleStatus|不计免赔 0：未购买 1：已购买|
       |status|状态 0：行程中 1：未付款 2：已完成|
       |preferentialId|用户优惠券关联id|
       |url|轨迹url|
         
      *header示例
         
             {
                "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
                "Accept":"application/json"
             }
         
      示例如下:
         
      传入参数：
         
      正确返回：
      
            {
              "code": 1,
              "message": "成功",
              "data": [
                {
                  "id": 14,
                  "number": "637338",
                  "carId": 28,
                  "plateNumber": "川A8888",
                  "userId": 6,
                  "startParkId": 17,
                  "startParkName": "天府停车场",
                  "destinationParkId": 18,
                  "destinationParkName": "华阳停车场",
                  "startTime": 1531921748000,
                  "endTime": 1534348762000,
                  "shouldPayAmount": 0,
                  "realPayAmount": 1,
                  "ownerEarnAmount": 1,
                  "payTime": 1532103191000,
                  "payType": 2,
                  "mileage": 0,
                  "durationTime": 0,
                  "deductibleStatus": 0,
                  "status": 2,
                  "discountId": 0,
                  "preferentialId": 0，
                  "url":"http://pdaxdtr0a.bkt.clouddn.com/537401383125233321000.png"
                }
              ]
            }
             
      ---
  
     ### 9.根据车牌号获取订单（模糊匹配） ###
           
     * 接口调用请求说明
           
       http请求方式: POST
       
       url: `{serverUrl}/mobile/tripOrder/queryOrdersByPlateNum`
           
     * 输入参数说明
           
         |参数|描述|是否可为空|
         |----|--- |--------|
         |plateNumber|车牌号 |否|
         
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
         |id|订单id|
         |number|订单号|
         |carId|车辆id|
         |plateNumber|车牌号|
         |userId|用户id|
         |startParkId|起点停车场id|
         |startParkName|起点停车场名称|
         |destinationParkId|终点停车场id|
         |destinationParkName|终点停车场名称|
         |startTime|行程开始时间|
         |endTime|行程结束时间|
         |shouldPayAmount|应付金额|
         |realPayAmount|实付金额|
         |payTime|支付时间|
         |payType|支付类型 0：余额支付，1 微信支付 2：支付宝支付|
         |mileage|行程里程|
         |durationTime|行程时间|
         |deductibleStatus|不计免赔 0：未购买 1：已购买|
         |status|状态 0：行程中 1：未付款 2：已完成|
         |discountId|折扣id|
         |preferentialId|优惠券id|
         |url|轨迹url|
           
        *header示例
           
               {
                  "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
                  "Accept":"application/json"
               }
           
        示例如下:
           
        传入参数：
           
        正确返回：
        
              {
                "code": 1,
                "message": "成功",
                "data": [
                  {
                    "id": 14,
                    "number": "637338",
                    "carId": 28,
                    "plateNumber": "川A8888",
                    "userId": 6,
                    "startParkId": 17,
                    "startParkName": "天府停车场",
                    "destinationParkId": 18,
                    "destinationParkName": "华阳停车场",
                    "startTime": 1531921748000,
                    "endTime": 1534348762000,
                    "shouldPayAmount": 0,
                    "realPayAmount": 1,
                    "ownerEarnAmount": 1,
                    "payTime": 1532103191000,
                    "payType": 2,
                    "mileage": 0,
                    "durationTime": 0,
                    "deductibleStatus": 0,
                    "status": 2,
                    "discountId": 0,
                    "preferentialId": 0，
                    "url":"http://pdaxdtr0a.bkt.clouddn.com/537401383125233321000.png"
                  }
                ]
              }
               
        ---
  
  
  ### 11.获取订单详情 ###
     
   * 接口调用请求说明
     
       http请求方式: GET
     
        url: `{serverUrl}/mobile/tripOrder/{orderNum}`
     
   * 输入参数说明(注：所传参数均为过滤条件，若不传任何过滤条件,则返回所有订单的集合)
     
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
   |id|订单id|
   |number|订单号|
   |plateNumber|车牌号|
   |userId|用户id|
   |startParkId|起点停车场id|
   |startParkName|起点停车场名称|
   |destinationParkId|终点停车场id|
   |destinationParkName|终点停车场名称|
   |startTime|行程开始时间|
   |endTime|行程结束时间|
   |shouldPayAmount|应付金额|
   |realPayAmount|实付金额|
   |payTime|支付时间|
   |payType|支付类型 0：余额支付，1 微信支付 2：支付宝支付|
   |mileage|行程里程|
   |durationTime|行程时间|
   |deductibleStatus|不计免赔 0：未购买 1：已购买|
   |status|状态 0：行程中 1：未付款 2：已完成|
   |preferentialId|用户优惠券关联id|
   |url|轨迹url|
     
   *header示例
     
         {
            "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
            "Accept":"application/json"
         }
     
   示例如下:
     
   传入参数：
     
   正确返回：
   
     {
     "code": 1,
     "message": "成功",
     "data": {
         "id": 14,
         "number": "637338",
         "carId": 28,
         "plateNumber": "川A8888",
         "userId": 6,
         "startParkId": 17,
         "startParkName": "天府停车场",
         "destinationParkId": 18,
         "destinationParkName": "华阳停车场",
         "startTime": 1531921748000,
         "endTime": 0,
         "shouldPayAmount": 0,
         "realPayAmount": 1,
         "ownerEarnAmount": 1,
         "payTime": 1532103191000,
         "payType": 2,
         "mileage": 0,
         "durationTime": 0,
         "deductibleStatus": 0,
         "status": 2,
         "discountId": 0,
         "preferentialId": 0，
         "url":"http://pdaxdtr0a.bkt.clouddn.com/537401383125233321000.png"
     }
        
   ---
    
### 12.预约车辆 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/mobile/tripOrder/reservation`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|carId|车辆id|否|
|startParkId|开始停车场id|

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
|orderType|用户是否有未完成订单 0没有 1 有|
|orderNum|用户未完成订单号|
|id|车辆id|
|plateNumber|车牌号|
|brand|品牌|
|power|动力来源 0燃油 1电动|
|seatNumber|座位数|
|carColor|车辆颜色|
|carType|车辆类型|
|carState|车辆状态|
|reservationId|预约id|
|userId|用户id|
|name|停车场名称|
|address|停车场地址|
|longitude|所在位置经度|
|latitude|所在位置纬度|
|createTime|预约创建时间|


*示例

*状态码
    
    1017:请先完成实名认证
    1018:请先完成驾驶证认证
    1019:请先交纳押金
    
*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

传入参数：

    {
      "carId":13,
      "startParkId":11
    }


正确返回：

    {
        "code": 1,
        "message": "成功",
        "data": {
            "id": 28,
            "plateNumber": "川A8888",
            "brand": "baoma",
            "power": 0,
            "seatNumber": 5,
            "carColor": "红色",
            "carType": 1,
            "carState": 6,
            "orderNum": "",
            "orderType": 0,
            "reservationId": 21,
            "userId": 4,
            "name": "新通惠酒店地面停车场",
            "address": "成都市青羊区青华路15号附8号",
            "longitude": 104.0380932,
            "latitude": 30.668815,
            "createTime": 1534649271000
        }
    }
    
---

### 13.创建行程订单 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/mobile/tripOrder/create`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|destinationParkId|终点停车场id|
|deductibleStatus|不计免赔 0：未购买 1：已购买|

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

*示例

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

传入参数：

    {
      "destinationParkId":13,
      "deductibleStatus":1
    }


正确返回：

    {
        "code": 1,
        "message": "成功",
        "data": {
                "id": 14,
                "number": "637338",
                "carId": 28,
                "plateNumber": "川A8888",
                "userId": 6,
                "startParkId": 17,
                "startParkName": "天府停车场",
                "destinationParkId": 18,
                "destinationParkName": "华阳停车场",
                "startTime": 1531921748000,
                "endTime": 0,
                "shouldPayAmount": 0,
                "realPayAmount": 1,
                "ownerEarnAmount": 1,
                "payTime": 1532103191000,
                "payType": 2,
                "mileage": 0,
                "durationTime": 0,
                "deductibleStatus": 0,
                "status": 2,
                "discountId": 0,
                "preferentialId": 0
            }
    }
    
---

### 14.完成行程订单 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}/mobile/tripOrder/completeTripOrder/{orderNum}`

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

*示例

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

### 15.查询用户预约信息 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/tripOrder/getReservation`

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
|orderType|用户是否有未完成订单 0没有 1 有|
|orderNum|用户未完成订单号|
|id|车辆id|
|plateNumber|车牌号|
|brand|品牌|
|power|动力来源 0燃油 1电动|
|seatNumber|座位数|
|carColor|车辆颜色|
|carType|车辆类型|
|carState|车辆状态|
|reservationId|预约id|
|userId|用户id|
|name|停车场名称|
|address|停车场地址|
|longitude|所在位置经度|
|latitude|所在位置纬度|
|createTime|预约创建时间|
|remainderRange|剩余续航里程 单位 km|

*示例

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
            "id": 28,
            "plateNumber": "川A8888",
            "brand": "baoma",
            "power": 0,
            "seatNumber": 5,
            "carColor": "红色",
            "carType": 1,
            "carState": 6,
            "orderNum": "",
            "orderType": 0,
            "reservationId": 21,
            "userId": 4,
            "name": "新通惠酒店地面停车场",
            "address": "成都市青羊区青华路15号附8号",
            "longitude": 104.0380932,
            "latitude": 30.668815,
            "createTime": 1534649271000
        }
    }
    
---
### 16.获取充值赠送比例&最大赠送额度 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/tripOrder/getPresent`

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
|rechargeRate|充值赠送比例|
|maxPresent|最大赠送额度 单位分|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

传入参数：
    

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data": {
        "rechargeRate":0.2,
        "maxPresent":20000
      }
    }
    
---

### 17.订单条件查询（根据条件过滤数据，不传任何条件，则返回所有订单） ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/system/tripOrder/findTripOrders`

* 输入参数说明

|id|订单id|是|
|number|订单号|是|
|carId|车辆id|是|
|userId|用户id|是|
|username|用户名（手机号）模糊匹配|是|
|startTime|时间区间最小值(格式 yyyy-MM-dd)|是|
|endTime|时间区间最大值（格式 yyyy-MM-dd）|是|
|status|状态 0：行程中 1：未付款 2：已完成|是|
|pageNumber|当前页码编号|是|
|pageSize|每页条数（不传或传-1表示不分页）|是|


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
|id|订单id|
|number|订单号|
|carId|车辆id|
|plateNumber|车牌号|
|userId|用户id|
|startParkId|起点停车场id|
|startParkName|起点停车场名称|
|destinationParkId|终点停车场id|
|destinationParkName|终点停车场名称|
|startTime|行程开始时间|
|endTime|行程结束时间|
|shouldPayAmount|应付金额|
|realPayAmount|实付金额|
|payTime|支付时间|
|payType|支付类型 0：余额支付，1 微信支付 2：支付宝支付|
|mileage|行程里程|
|durationTime|行程时间|
|deductibleStatus|不计免赔 0：未购买 1：已购买|
|status|状态 0：行程中 1：未付款 2：已完成|
|discountId|折扣id|
|preferentialId|优惠券id|
|url|轨迹url|
|pageNumber|当前页码编号|
|pageSize|每页条数|
|total|总条数|
|pages|总页数|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

示例如下:

传入参数：

正确返回（带分页时的返回，无分页时只返回订单相关信息）：
  
      {
        "code": 1,
        "message": "成功",
        "data": {
          "pageNum": 1,
          "pageSize": 2,
          "size": 2,
          "startRow": 1,
          "endRow": 2,
          "total": 15,
          "pages": 8,
          "list": [
            {
              "id": 45,
              "number": "12344461111111144111111",
              "carId": 27,
              "plateNumber": "川A9999",
              "userId": 12,
              "startParkId": 17,
              "startParkName": "新通惠酒店地面停车场",
              "destinationParkId": 43,
              "destinationParkName": "测试",
              "startTime": 1534838797000,
              "endTime": 1534752423000,
              "shouldPayAmount": 10,
              "realPayAmount": 5,
              "ownerEarnAmount": 0,
              "payTime": 1534823020000,
              "payType": 1,
              "mileage": 10,
              "durationTime": 5,
              "deductibleStatus": 0,
              "status": 2,
              "discountId": 0,
              "preferentialId": 0,
              "parkFee": 1,
              "deductible": 20，
              "url":"http://pdaxdtr0a.bkt.clouddn.com/537401383125233321000.png"
            },
            {
              "id": 50,
              "number": "537401377024803805022",
              "carId": 27,
              "plateNumber": "川A9999",
              "userId": 7,
              "startParkId": 17,
              "startParkName": "新通惠酒店地面停车场",
              "destinationParkId": 39,
              "destinationParkName": "宽窄巷子地面停车场 ",
              "startTime": 1534790884000,
              "endTime": 0,
              "shouldPayAmount": 0,
              "realPayAmount": 0,
              "ownerEarnAmount": 0,
              "payTime": 0,
              "payType": 0,
              "mileage": 0,
              "durationTime": 0,
              "deductibleStatus": 1,
              "status": 2,
              "discountId": 0,
              "preferentialId": 0,
              "parkFee": 0,
              "deductible": 0,
              "url":"http://pdaxdtr0a.bkt.clouddn.com/537401383125233321000.png"
            }
          ],
          "prePage": 0,
          "nextPage": 2,
          "isFirstPage": true,
          "isLastPage": false,
          "hasPreviousPage": false,
          "hasNextPage": true,
          "navigatePages": 8,
          "navigatepageNums": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8
          ],
          "navigateFirstPage": 1,
          "navigateLastPage": 8,
          "firstPage": 1,
          "lastPage": 8
        }
      }
 
 ---



