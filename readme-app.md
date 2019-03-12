# 工程结构介绍

![](img/app-struct.png)

- `basiclib` 基础库
- `componentservice` 服务接口定义、路由定义
- `accountcomponent` `webcomponent` 业务组件，相互独立 
- `app` 主工程 host，开发期间不直接依赖子组件，通过接口或者路由的方式与组件交互


