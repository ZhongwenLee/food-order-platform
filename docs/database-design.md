# 外卖平台数据库设计说明

## 1. 设计目标
本数据库设计用于支撑外卖点餐平台的核心业务流程，包括：

- 用户注册登录
- 商家管理
- 菜品分类与菜品管理
- 购物车
- 下单与订单明细
- 收货地址管理

## 2. 表结构总览
本设计包含以下表：

- `user`
- `merchant`
- `category`
- `dish`
- `cart`
- `cart_item`
- `orders`
- `order_item`
- `address`

## 3. 数据表说明

### 3.1 user 用户表
用户表用于保存平台普通用户的基础信息，是购物车、订单、地址等业务的主体。

| 字段名 | 类型 | 说明 | 约束 |
|---|---|---|---|
| id | bigint | 主键ID | PK |
| username | varchar(50) | 用户名 | 唯一，非空 |
| password_hash | varchar(255) | 密码哈希值 | 非空 |
| phone | varchar(20) | 手机号 | 唯一，可空 |
| nickname | varchar(50) | 用户昵称 | 可空 |
| avatar | varchar(255) | 头像地址 | 可空 |
| status | tinyint | 用户状态：1正常，0禁用 | 默认1 |
| created_at | datetime | 创建时间 | 非空 |
| updated_at | datetime | 更新时间 | 非空 |


### 3.2 merchant 商家表
商家表用于保存商家基础信息，是菜品、分类、订单的归属主体。

| 字段名 | 类型 | 说明 | 约束 |
|---|---|---|---|
| id | bigint | 主键ID | PK |
| merchant_name | varchar(100) | 商家名称 | 非空 |
| contact_phone | varchar(20) | 联系电话 | 可空 |
| address | varchar(255) | 商家地址 | 可空 |
| business_hours | varchar(100) | 营业时间 | 可空 |
| logo | varchar(255) | 商家Logo | 可空 |
| status | tinyint | 商家状态：1营业，0停业，2审核中 | 默认1 |
| created_at | datetime | 创建时间 | 非空 |
| updated_at | datetime | 更新时间 | 非空 |


### 3.3 category 菜品分类表
菜品分类表用于对商家下的菜品进行分类管理，例如热菜、饮品、小吃、套餐等。

| 字段名 | 类型 | 说明 | 约束 |
|---|---|---|---|
| id | bigint | 主键ID | PK |
| merchant_id | bigint | 商家ID | FK -> merchant.id |
| category_name | varchar(50) | 分类名称 | 非空 |
| sort_order | int | 排序值 | 默认0 |
| status | tinyint | 分类状态：1启用，0停用 | 默认1 |
| created_at | datetime | 创建时间 | 非空 |
| updated_at | datetime | 更新时间 | 非空 |


### 3.4 dish 菜品表
菜品表用于保存菜品基础信息，包括所属商家、所属分类、价格、库存和状态等。

| 字段名 | 类型 | 说明 | 约束 |
|---|---|---|---|
| id | bigint | 主键ID | PK |
| merchant_id | bigint | 商家ID | FK -> merchant.id |
| category_id | bigint | 分类ID | FK -> category.id |
| dish_name | varchar(100) | 菜品名称 | 非空 |
| description | varchar(500) | 菜品描述 | 可空 |
| image_url | varchar(255) | 菜品图片地址 | 可空 |
| price | decimal(10,2) | 菜品价格 | 非空 |
| stock | int | 库存数量 | 默认0 |
| status | tinyint | 菜品状态：1上架，0下架 | 默认1 |
| created_at | datetime | 创建时间 | 非空 |
| updated_at | datetime | 更新时间 | 非空 |


### 3.5 cart 购物车表
购物车表用于记录某个用户在某个商家下的一份购物车上下文。

| 字段名 | 类型 | 说明 | 约束 |
|---|---|---|---|
| id | bigint | 主键ID | PK |
| user_id | bigint | 用户ID | FK -> user.id |
| merchant_id | bigint | 商家ID | FK -> merchant.id |
| status | tinyint | 购物车状态：1有效，0失效 | 默认1 |
| created_at | datetime | 创建时间 | 非空 |
| updated_at | datetime | 更新时间 | 非空 |


### 3.6 cart_item 购物车明细表
购物车明细表用于记录购物车中的具体菜品、数量及相关快照信息。

| 字段名 | 类型 | 说明 | 约束 |
|---|---|---|---|
| id | bigint | 主键ID | PK |
| cart_id | bigint | 购物车ID | FK -> cart.id |
| dish_id | bigint | 菜品ID | FK -> dish.id |
| dish_name_snapshot | varchar(100) | 菜品名称快照 | 非空 |
| price_snapshot | decimal(10,2) | 菜品单价快照 | 非空 |
| quantity | int | 数量 | 非空 |
| selected | tinyint | 是否选中：1是，0否 | 默认1 |
| created_at | datetime | 创建时间 | 非空 |
| updated_at | datetime | 更新时间 | 非空 |


### 3.7 orders 订单表
订单表用于保存一次下单的整体信息，是订单主记录。

| 字段名 | 类型 | 说明 | 约束 |
|---|---|---|---|
| id | bigint | 主键ID | PK |
| order_no | varchar(50) | 订单编号 | 唯一，非空 |
| user_id | bigint | 用户ID | FK -> user.id |
| merchant_id | bigint | 商家ID | FK -> merchant.id |
| address_id | bigint | 收货地址ID | FK -> address.id |
| total_amount | decimal(10,2) | 订单总金额 | 非空 |
| status | tinyint | 订单状态 | 默认1 |
| remark | varchar(255) | 备注 | 可空 |
| created_at | datetime | 创建时间 | 非空 |
| updated_at | datetime | 更新时间 | 非空 |


### 3.8 order_item 订单明细表
订单明细表用于保存订单中的每个菜品及其下单时的快照信息。

| 字段名 | 类型 | 说明 | 约束 |
|---|---|---|---|
| id | bigint | 主键ID | PK |
| order_id | bigint | 订单ID | FK -> orders.id |
| dish_id | bigint | 菜品ID | FK -> dish.id |
| dish_name_snapshot | varchar(100) | 菜品名称快照 | 非空 |
| price_snapshot | decimal(10,2) | 菜品单价快照 | 非空 |
| quantity | int | 数量 | 非空 |
| subtotal_amount | decimal(10,2) | 小计金额 | 非空 |
| created_at | datetime | 创建时间 | 非空 |
| updated_at | datetime | 更新时间 | 非空 |


### 3.9 address 收货地址表
收货地址表用于保存用户的多个收货地址信息。

| 字段名 | 类型 | 说明 | 约束 |
|---|---|---|---|
| id | bigint | 主键ID | PK |
| user_id | bigint | 用户ID | FK -> user.id |
| recipient_name | varchar(50) | 收货人姓名 | 非空 |
| recipient_phone | varchar(20) | 收货人电话 | 非空 |
| province | varchar(50) | 省 | 非空 |
| city | varchar(50) | 市 | 非空 |
| district | varchar(50) | 区/县 | 非空 |
| detail_address | varchar(255) | 详细地址 | 非空 |
| is_default | tinyint | 是否默认地址：1是，0否 | 默认0 |
| status | tinyint | 地址状态：1有效，0失效 | 默认1 |
| created_at | datetime | 创建时间 | 非空 |
| updated_at | datetime | 更新时间 | 非空 |


## 4. 主键与外键关系说明

### 4.1 主键
所有表统一使用 `id` 作为主键，类型为 `bigint`。

### 4.2 外键关系
- `category.merchant_id -> merchant.id`
- `dish.merchant_id -> merchant.id`
- `dish.category_id -> category.id`
- `cart.user_id -> user.id`
- `cart.merchant_id -> merchant.id`
- `cart_item.cart_id -> cart.id`
- `cart_item.dish_id -> dish.id`
- `orders.user_id -> user.id`
- `orders.merchant_id -> merchant.id`
- `orders.address_id -> address.id`
- `order_item.order_id -> orders.id`
- `order_item.dish_id -> dish.id`
- `address.user_id -> user.id`


## 5. 状态字段说明

### 5.1 user.status
- `1` 正常
- `0` 禁用

### 5.2 merchant.status
- `1` 营业中
- `0` 停业
- `2` 审核中

### 5.3 category.status
- `1` 启用
- `0` 停用

### 5.4 dish.status
- `1` 上架
- `0` 下架

### 5.5 cart.status
- `1` 有效
- `0` 失效

### 5.6 orders.status
- `0` 已取消
- `1` 待支付
- `2` 已支付
- `3` 已接单
- `4` 制作中
- `5` 已完成

### 5.7 address.status
- `1` 有效
- `0` 失效


## 6. 设计说明

1. 订单表与订单明细表拆分，便于处理多菜品订单。
2. 购物车采用主表+明细表结构，可以更清晰地表达一个用户在某个商家下的多菜品选择。
3. 订单明细和购物车明细保留快照字段，避免菜品后续改名或改价影响历史记录。
4. 所有核心业务表都保留 `created_at` 和 `updated_at` 字段。
5. 关键状态字段统一使用整型枚举，便于扩展和索引优化。


## 7. E-R 图内容
见附图