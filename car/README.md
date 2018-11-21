# EDrive

#### 模块说明：
车辆模块
- - -

## ==================以下为车辆模块接口文档===================

### 1.添加车辆 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/system/car/addCar`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|plateNumber|车牌号|否|
|brand|车辆品牌|否|
|power|动力来源 0燃油 1电动|否|
|seatNumber|座位数量|否|
|carType|车辆类型 0轿车 1越野|否|

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

* 错误码集合：

|错误码(code)|描述(message)|
|----|--- |
|1|成功|
|0|失败|
|1003|已存在车牌号,请核对信息后再添加|

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

示例如下:

    {
    "plateNumber":"川B1234",
    "brand":"悍马",
    "power":0,
    "seatNumber":5,
    "carColor":"白色",
    "carType":1
    }


正确返回：

    {
      "code": 1,
      "message": "成功",
      "data": ""
    }

---
### 2.根据车辆id获取车辆详情 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/car/info/carId`

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
|code|状态码|
|message|状态信息|
|data|返回数据|
|plateNumber|车牌号|
|brand|车辆品牌|
|power|动力来源 0燃油 1电动|
|seatNumber|座位信息|
|carColor|车辆颜色|
|carType|车辆类型 0轿车 1越野|
|mileagePrice|里程价格 分/公里|
|timeFee|计时价格 分/分钟|
|maxDistance|可行驶最大距离|

* url示例:`{serverUrl}/mobile/car/info/26`

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
        "plateNumber": "川B1234",
        "brand": "悍马",
        "power": 0,
        "seatNumber": 5,
        "carColor": "白色",
        "carType": 1,
        "mileagePrice": 100,
        "timeFee": 10,
        "maxDistance": 0
      }
    }
---

### 3.删除车辆 ###

* 接口调用请求说明

	http请求方式: DELETE

    url: `{serverUrl}/system/car/delete`

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
|code|状态码|
|message|状态信息|
|data|返回数据|

* 错误码集合：

|错误码(code)|描述(message)|
|----|--- |
|1|成功|
|0|失败|
|1000|车辆正在被使用中|

* 示例

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

传入参数：

    {
      "carId":25
    }

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data": ""
    }
---

### 4.车辆下线 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}/system/car/downline`

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
|code|状态码|
|message|状态信息|
|data|返回数据|

* 错误码集合：

|错误码(code)|描述(message)|
|----|--- |
|1|成功|
|0|失败|
|1000|车辆正在被使用中|

* 示例

*header示例

    {
       "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
       "Accept":"application/json"
    }

传入参数：

    {
      "carId":25
    }


正确返回：

    {
      "code": 1,
      "message": "成功",
      "data": ""
    }
---

### 5.车辆上线 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}/system/car/online`

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
|code|状态码|
|message|状态信息|
|data|返回数据|

* 错误码集合：

|错误码(code)|描述(message)|
|----|--- |
|1|成功|
|0|失败|
|1001|车辆油量不足,请加满油再次上线|
|1002|车辆不在停车场,请将车停到停车场再上线|

正确返回：

    {
      "code": 1,
      "message": "成功",
      "data": ""
    }
---

### 6.可预约车辆 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/car/getReservationList/parkId`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|parkId|停车场id|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 输出参数说明

|参数|描述|
|----|--- |
|code|状态码|
|message|状态信息|
|data|返回数据| list
|plateNumber|车牌号|
|brand|车辆品牌|
|power|动力来源 0燃油 1电动|
|seatNumber|座位信息|
|carColor|车辆颜色|
|carType|车辆类型 0轿车 1越野|
|mileagePrice|里程价格 分/公里|
|timeFee|计时价格 分/分钟|
|maxDistance|可行驶最大距离|

*示例url: `{serverUrl}/mobile/car/getReservationList/11`

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
          "plateNumber": "川A5555",
          "brand": "法拉利",
          "power": 0,
          "seatNumber": 2,
          "carColor": "白色",
          "carType": 0,
          "mileagePrice": 100,
          "timeFee": 10,
          "maxDistance": 0
        }
      ]
    }
---
### 7.我的车辆 ###

* 接口调用请求说明

	http请求方式: GET

    url: `{serverUrl}/mobile/car/myCarList`

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
|plateNumber|车牌号|
|brand|车辆品牌|
|power|动力来源 0燃油 1电动|
|seatNumber|座位信息|
|carColor|车辆颜色|
|carType|车辆类型 0轿车 1越野|
|mileagePrice|里程价格 分/公里|
|timeFee|计时价格 分/分钟|
|maxDistance|可行驶最大距离|
|carGrade|车辆等级|
|sumAmount|车辆总收益|

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
          "plateNumber": "川B1234",
          "brand": "悍马",
          "power": 0,
          "seatNumber": 5,
          "carColor": "白色",
          "carType": 1,
          "carState": 0,
          "mileagePrice": 100,
          "timeFee": 10,
          "maxDistance": 0,
          "carGrade": 1,
          "sumAmount": 6
        }
      ]
    }
---
### 8.取消预约 ###

* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}/mobile/car/cancelReservation`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|reservationId|预约单号|否|

* header参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|Authorization|OAuth2认证(Bearer + token,中间有空格)|否|

* 错误码集合：

|错误码(code)|描述(message)|
|----|--- |
|1|成功|
|0|失败|
|1006|预约信息超时|

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

### 9.修改车辆等级 ###
* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}/mobile/car/updateGrade`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|carId|车辆id|否|
|carGrade|车辆等级|否|

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

### 10.审核车辆 ###
* 接口调用请求说明

	http请求方式: PUT

    url: `{serverUrl}/mobile/car/auditCar`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|carId|车辆id|否|
|carGrade|车辆等级|否|
|deviceId|车载设备id|否|
|remarks|备注|不通过时必填 |
|state|审核状态  0不通过 1通过|否|

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

### 11.所有车辆列表 ###
* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/system/car/carList`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|plateNumber|车牌号|是|
|powerType|动力类型 0燃油 1电动|是|
|carGrade|车辆等级|是|
|carState|车辆状态 0审核中 1审核失败 2空闲 3审核通过 4使用中 5已下线 6已预约|是|
|pageSize|每页条数|否|
|pageNumber|第几页|否|

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
|pageNum|当前页数|
|pageSize|每页条数|
|total|总条数|
|pages|总页数|
|plateNumber|车牌号|
|brand|车辆品牌|
|power|动力来源 0燃油 1电动|
|seatNumber|座位信息|
|carColor|车辆颜色|
|carType|车辆类型 0轿车 1越野|
|mileagePrice|里程价格 分/公里|
|timeFee|计时价格 分/分钟|
|latitude|车辆当前纬度|
|longitude|车辆当前经度|
|speed|速度 单位 为km/hour|
|batteryRemaining|剩余电量百分比，范围是 0〜100|
|remainderRange|剩余续航里程，单位千米|
|totalMileage|总里程,单位为：千米|
|gpsSignalIntensity|GPS信号强度|

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
            "pageSize":15,
            "total":1,
            "pages":1,
            "list":
            [{"carInfo":
                {
                "id":27,
                "plateNumber":"川A9999",
                "brand":"大众",
                "power":0,
                "seatNumber":5,
                "carColor":"白色",
                "carType":0,
                "carState":6,
                "mileagePrice":1,
                "timeFee":20,
                "maxDistance":0,
                "parkId":17,
                "parkName":"新通惠酒店地面停车场",
                "parkAddress":"成都市青羊区青华路15号附8号",
                "deviceId":"081400000005"},
                "device":{"latitude":0.0,
                        "longitude":0.0,
                        "speed":0,
                        "batteryRemaining":0,
                        "remainderRange":0,
                        "totalMileage":0,
                        "gpsSignalIntensity":0
                        }
            }
        ]}

---

### 13.获取设备信息 ###
 * 接口调用请求说明
 
 	http请求方式: GET
 
     url: `{serverUrl}/mobile/car/getDeviceInfo/{carId}`
 
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
 |latitude|设备当前纬度|
 |longitude|设备当前经度|
 |speed|速度 单位 为km/hour|
 |batteryRemaining|剩余电量百分比，范围是 0〜100|
 |remainderRange|剩余续航里程，单位千米|
 |totalMileage|总里程,单位为：千米|
 |gpsSignalIntensity|GPS信号强度|
 
 *header示例
 
     {
        "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzA5NTc2MjMsInVzZXJfbmFtZSI6IjE4MDExMjI1NTY4IiwianRpIjoiNTBmZmQzYzktMzNjNC00NWU1LWI1NmYtNzM5YTcyODcxYjE5IiwiY2xpZW50X2lkIjoidGVzdCIsInNjb3BlIjpbImFsbCJdfQ.fCmjh6yOJzNvtdSkx0-2D0u_jY3vP47lSiBTHNpscQc",
        "Accept":"application/json"
     }
 
 *传参实例
 
    ｛
        http://localhost:8088/mobile/car/getDeviceInfo/27
     ｝
 
 
 正确返回：
 
     {
         "code": 1,
         "message": "成功",
         "data": {
             "latitude": 30.49628,
             "longitude": 104.078344,
             "speed": 0,
             "batteryRemaining": 0,
             "remainderRange": 0,
             "totalMileage": 706,
             "gpsSignalIntensity": 27
         }
     }
 ---
