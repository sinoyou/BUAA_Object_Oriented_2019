# BUAA_Object_Oriented_2019
Codes for 4 projects of BUAA 2019 Object Oriented Course (each project contains 2-3 branches)

# Guide

* Language：Java
* Projects: There're 4 projects in total and each project lasts 4-5 week. A project started with a simple requirement and gradually add requirements as time goes by. 
* Judge standard: auto judge + mutual judge
  * Auto Judge : weak test (maximum 20 times as you wish) + strong test (only your last submitted version in weak test).
  * Mutual Judge : manually design judge example to hack others' program. Hacker will be awarded and hacked program will be punished. 



# Project General Description [EN]

## Differential Homework

### Function:

By parsing an expression entered as a string, output the differentiated form of the expression. Ultimately, it is required to be able to identify wrong expressions, support the solution of simple power functions and simple sine and cosine functions, and support nested expressions.

### Performance：

By simplifying the result expression, the shorter the expression, the higher the performance score.

### Example：

sin((2 * x)) * (cos(x)+1) - > 2*(cos(x) + 1)*cos((2*x)) - sin(x)*sin((2*x))



## Elevator Simulation

### Function：

This homework needs to simulate a multi-threaded real-time multi-elevator system, input request information from standard input, the program receives and processes it, simulates the elevator operation, and outputs the necessary operating information through the standard output. The functions of the elevator system are: up and down, door opening and closing. The parking floors of multiple elevators have different running times and maximum passenger capacity.

### Performance：

The shorter the total transit time, the higher the performance score.

### Example：

| Input                                                      |                            Output                            |
| :--------------------------------------------------------- | :----------------------------------------------------------: |
| [0.0]1-FROM-1-TO-2                      [0.0]2-FROM-1-TO-2 | [    0.0120]OPEN-1-B  [    0.0160]IN-1-1-B  [    0.0170]IN-2-1-B  [    0.4120]CLOSE-1-B  [    0.9120]ARRIVE-2-B  [    0.9120]OPEN-2-B  [    0.9130]OUT-1-2-B  [    0.9130]OUT-2-2-B  [    1.3120]CLOSE-2-B |



## Subway System

### Function：

By implementing multiple container classes provided by the course group, implemented a subway systems, which supports the addition, deletion, and modification of subway lines, and uses graph algorithms to implement multiple function path queries. Improve the use of interfaces and JML work through this topic.

### Performance：

Data structures and algorithms need to be used to minimize runtime.

### Operation Type：

* Add：ADD_PATH
* Delete：DELETE_PATH, DELETE_PATH_BY_ID
* Basic Query：FIND_PATH_BY_ID, FIND_PATH, TOTAL_PATH, PATH_SIZE, NODE_COUNT_IN_PATH, PATH_CONTAIN_NODE, COMPARE_PATH。
* Middle Query：CONTAIN_PATH, CONTAIN_EDGE
* Advanced Query：IS_NODE_LINKED, FIND_SHORTESET_PATH, COUNT_BLOCKS, FIND_LOWEST_PRICE, FIND_LEAST_TRANSFER, FIND_LESAT_DISAPPOINTMENT。



## UML Parser

### Function：

Implement a parser for UML source files, complete validity checks on input UML source code, and complete a series of queries. Supported UML models are: UML class diagrams, UML state diagrams, and UML sequence diagrams.

### Performance：

Data structures and algorithms need to be used to minimize runtime.

### Operation Type：

* class diagram：CLASS_COUNT, CLASS_OPERATION_COUNT, CLASS_ATTRIBUTE_COUNT, CLASS_ASSOCIATION_CONT, CLASS_ASSOCIATED_CLASS, CLASS_OPERATION_VISIBILITY, CLASS_ATTRIBUTE_VISIBILITY, CLASS_TOP_CLASS, CLASS_IMPLEMENTED_INTERFACE. 
* state diagram：STATUS_COUNT、TRANSFER_COUNT
* interaction diagram：OBJECT_COUNT、INTERACTION_COUNT、MESSAGE_COUNT
* validation check：
  * UML002: No members sharing same name. 
  * UML008: No cycle class inheritance. 
  * UML009: A class/interface can not inherit a same interface more than once. 



# Project General Description [CN]

## 求导器

[GuideBook](./project1-final.md)

### 功能：

通过解析以字符串形式输入的表达式，输出该表达式经过求导后的形式。最终要求能够识别错误的表达式，支持简单幂函数和简单正余弦函数的求解，支持嵌套的表达式。

### 性能分：

通过表达式化简，使得表达式越短则性能分越高。

### 例如：

sin((2 * x)) * (cos(x)+1) - > 2*(cos(x) + 1)*cos((2*x)) - sin(x)*sin((2*x))



## 电梯模拟

[GuideBook](./project2-final.md)

### 功能：

本次作业需要模拟一个多线程实时多电梯系统，从标准输入中输入请求信息，程序进行接收和处理，模拟电梯运行，将必要的运行信息通过输出接口进行输出。电梯系统具有的功能为：上下行，开关门。多部电梯的可停靠楼层，运行时间，最大载客量都不相同。

### 性能分：

总运输时间越短则性能分越高。

### 例如：

| Input                                  |                            Output                            |
| :------------------------------------- | :----------------------------------------------------------: |
| [0.0]1-FROM-1-TO-2  [0.0]2-FROM-1-TO-2 | [    0.0120]OPEN-1-B  [    0.0160]IN-1-1-B  [    0.0170]IN-2-1-B  [    0.4120]CLOSE-1-B  [    0.9120]ARRIVE-2-B  [    0.9120]OPEN-2-B  [    0.9130]OUT-1-2-B  [    0.9130]OUT-2-2-B  [    1.3120]CLOSE-2-B |



## 地铁系统

[GuideBook](./project3-final.md)

### 功能：

通过实现课程组下发的多个容器类，实现一套建议的地铁系统，支持对地铁线路的增删查改，并使用图算法实现多种功能的路径查询。通过该课题提升对接口和JML工作的使用。

### 性能：

需要使用数据结构和算法，尽量降低运行的时间。

### 操作类型：

* 增加：添加路径。
* 删除：删除路径、根据路径id删除路径。
* 基础查询：查询路径id、根据路径id获得路径、总路径数、路径大小、路径节点数、路径是否包含此节点、路径比较。
* 进阶查询：是否包含路径、容器中是否存在一条边。
* 高级查询：节点连通性、最短路径、连通块数量、最低票价、最小换乘次数、最小不满意度。



## UML解析器

[GuideBook](./project4-final.md)

### 功能：

实现UML源文件的解析器，对于输入的UML源代码，完成有效性检查，并能够完成一系列查询。支持的UML模型有：UML类图、UML状态图和UML顺序图。

### 性能：

需要使用数据结构和算法，尽量降低运行的时间。

### 操作类型：

* 类图：类的计数、类的操作统计、类的属性统计、类有几个关联、类的关联对端哪些类、类操作可见性、类属性可见性、类的顶级父类、类实现的全部接口、类是否违背隐藏原则。
* 状态图：状态的计数、迁移计数、后继状态统计。
* 顺序图：参与对象统计、交互信息统计、输入信息统计。
* 有效性检查：
  * UML002: 不能有重名的成员。
  * UML008: 不能有循环继承。
  * UML009: 任何一个类或接口不能重复继承另外的接口。
