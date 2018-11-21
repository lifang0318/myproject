# EDrive

#### 模块说明：
停车场模块
- - -

## ==================以下为停车场接口文档===================

### 1.添加停车场 ###

* 接口调用请求说明

	http请求方式: POST

    url: `{serverUrl}/system/park/add`

* 输入参数说明

|参数|描述|是否可为空|
|----|--- |--------|
|userId|用户id|否|
|name|停车场名称|否|
|type|类型 0：地上 1：地面|否|
|longitude|所在位置经度|否|
|latitude|所在位置纬度|否|
|manager|负责人姓名|是|
|phone|负责人电话|是|
|costType|计费类型 0：按次计费 1：按时计费|否|
|price|计费单价|否|
|address|详细地址|否|
|parkCount|停车位总数量|否|

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
    
   ----
    
   ### 2.更新停车场 ###
    
   * 接口调用请求说明
    
     http请求方式: PUT
    
     url: `{serverUrl}/system/park//update/{id}`
    
   * 输入参数说明
    
   |参数|描述|是否可为空|
   |----|--- |--------|
   |name|停车场名称|否|
   |type|类型 0：地上 1：地面|否|
   |longitude|所在位置经度|否|
   |latitude|所在位置纬度|否|
   |manager|负责人姓名|是|
   |phone|负责人电话|是|
   |costType|计费类型 0：按次计费 1：按时计费|否|
   |price|计费单价|否|
   |address|详细地址|否|
   |parkCount|停车位总数量|否|
    
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
   
   -----
       
  ### 3.获取单个停车场信息 ###
  
  * 接口调用请求说明
  
  	http请求方式: GET
  
      url: `{serverUrl}/system/park/getParkInfo/{id}`
  
  * 输入参数说明
  
  |参数|描述|是否可为空|
  |----|--- |--------|
  
  * header参数说明
  
  |参数|描述|是否可为空|
  |----|--- |--------|
  |Authorization|	OAuth2认证(Bearer + token,中间有空格)|否|
  
  * 输出参数说明
  
    |参数|描述|
    |----|--- |
    |code|状态码|
    |message|状态描述|
    |data|返回数据|
    |id|停车场id|
    |createTime|创建时间|
    |updateTime|更新时间|
    |userId|记录人Id|
    |name|停车场名称|
    |type|停车场类型 0：地上 1：地面|
    |longitude|停车场经度|
    |latitude|停车场维度|
    |manager|停车场负责人|
    |phone|负责人电话|
    |costType|计费类型 0：按次计费 1：按时计费|
    |price|计费单价|
    |address|停车场详细地址|
    |status|0：禁用 1：启用 2：删除|
    |parkCount|停车位总数量|
    |parkCountBalance|剩余停车位数量|
          
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
             "id": 46,
             "createTime": "2018-08-15 09:11:49",
             "updateTime": "2018-08-15 09:11:49",
             "userId": 6,
             "name": "天府新谷停车场",
             "type": 1,
             "longitude": 10.089897,
             "latitude": 10.089897,
             "manager": "朱红平",
             "phone": "12784468568",
             "costType": 0,
             "price": 25,
             "address": "成都天府新",
             "status": 1,
             "parkCount": 3,
             "parkCountBalance": 3
         }
     }
      
   ----
       
  ### 4.获取满足过滤条件的停车场 ###
  
  * 接口调用请求说明
  
  	http请求方式: GET
  
    url: `{serverUrl}/mobile/park/findParks`
  
  * 输入参数说明
  
    注意：根据传入的参数进行数据过滤，若不传任何参数，则返回所有的停车场
  
  |参数|描述|是否可为空|
  |----|--- |--------|
  |id|停车场id|是|
  |createTime|创建时间|是|
  |updateTime|更新时间|是|
  |userId|记录人Id|是|
  |name|停车场名称|是|
  |type|停车场类型 0：地上 1：地面|是|
  |longitude|停车场经度|是|
  |latitude|停车场维度|是|
  |manager|停车场负责人|是|
  |phone|负责人电话|是|
  |costType|计费类型 0：按次计费 1：按时计费|是|
  |price|计费单价|是|
  |address|停车场详细地址|是|
  |status|0：禁用 1：启用 2：删除|是|
 
  * header参数说明
  
  |参数|描述|是否可为空|
  |----|--- |--------|
  |Authorization|	OAuth2认证(Bearer + token,中间有空格)|否|
  
  * 输出参数说明
  
    |参数|描述|
    |----|--- |
    |code|状态码|
    |message|状态描述|
    |data|返回数据|
    |id|停车场id|
    |createTime|创建时间|
    |updateTime|更新时间|
    |userId|记录人Id|
    |name|停车场名称|
    |type|停车场类型 0：地上 1：地面|
    |longitude|停车场经度|
    |latitude|停车场纬度|
    |manager|停车场负责人|
    |phone|负责人电话|
    |costType|计费类型 0：按次计费 1：按时计费|
    |price|计费单价|
    |address|停车场详细地址|
    |status|0：禁用 1：启用 2：删除|
    |parkCount|停车位总数量|
    |parkCountBalance|剩余停车位数量|
          
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
                "id": 17,
                "createTime": "2018-07-16 13:12:50",
                "updateTime": "2018-07-16 13:12:50",
                "userId": 3,
                "name": "天府停车场",
                "type": 0,
                "longitude": 104.371111,
                "latitude": 30.871111,
                "manager": "苟波",
                "phone": "13866667777",
                "costType": 1,
                "price": 10,
                "address": "天府政务中心旁",
                "status": 1,
                "parkCount": 5,
                "parkCountBalance": 5
            }
        ]
    }
      
   ----
   
   ### 5.启用停车场 ###
       
   * 接口调用请求说明
       
      http请求方式: PUT
       
      url: `{serverUrl}/system/park/enable/{id}`
       
   * 输入参数说明
       
     |参数|描述|是否可为空|
     |----|--- |--------|
      
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
          
   ----
   
   ### 6.禁用停车场 ###
       
   * 接口调用请求说明
       
      http请求方式: PUT
       
      url: `{serverUrl}/system/park/disable/{id}`
       
   * 输入参数说明
       
     |参数|描述|是否可为空|
     |----|--- |--------|
      
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
          
   ----
   
   ### 7.删除停车场 ###
       
   * 接口调用请求说明
       
      http请求方式: DELETE
       
      url: `{serverUrl}/system/park/delete/{id}`
       
   * 输入参数说明
       
     |参数|描述|是否可为空|
     |----|--- |--------|
      
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
          
   ----
   
   ### 8.预约车辆时获取停车场相关信息(返回所有启用状态的停车场，信息包括：停车场地址、经纬度、可用车辆数（状态为空闲）、剩余停车位数量) ###
        
   * 接口调用请求说明
     
     注意：此接口无需登录（无需token验证过程），直接调用
     
     http请求方式: GET
     
     url: `{serverUrl}/mobile/park/findRelativeParks`
          
   * 输入参数说明
     |参数|描述|是否可为空|
     |----|--- |--------|
   * 输出参数说明
     
       |参数|描述|
       |----|--- |
       |code|状态码|
       |message|状态描述|
       |data|返回数据|
       |id|停车场id|
       |address|停车场地址|
       |longitude|停车场经度|
       |latitude|停车场纬度|
       |parkCountBalance|剩余停车位数量|
       |validCarCount|可用车辆数|
             
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
                   "id": 18,
                   "address": "天府公园旁",
                   "longitude": 104.572222,
                   "latitude": 30.372222,
                   "parkCountBalance": 4,
                   "validCarCount": 1
               },
               {
                   "id": 19,
                   "address": "海洋公园旁",
                   "longitude": 104.511234,
                   "latitude": 30.301233,
                   "parkCountBalance": 5,
                   "validCarCount": 2
               }
           ]
       }
         
   ----
     
   ### 9.根据经纬度返回最近的5个停车场信息 ###
       
   * 接口调用请求说明
       
      http请求方式: POST
       
      url: `{serverUrl}/mobile/park/getNearestPark`
       
   * 输入参数说明
       
     |参数|描述|是否可为空|
     |----|--- |--------|
      |latitude|纬度|否|
      |longitude|经度|否|
      
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
      |parkName|停车场名称|
      |parkAddress|停车场位置|
      |parkId|停车场id|
      |latitude|纬度|
      |longitude|经度|
       
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
                    "parkName":"龙之梦大酒店地面停车场",
                    "parkAddress":"成华区嘉陵江路8号",
                    "parkId":41,
                    "latitude":30.6355895,
                    "longitude":104.1505765
                   },
                   {
                   "parkName":"西御街65号地面停车场",
                   "parkAddress":"青羊区西御街65号附2号",
                   "parkId":40,
                   "latitude":30.6625827,
                   "longitude":104.0683302
                   }
                    
              ]
          }
          
   ----

   
   
