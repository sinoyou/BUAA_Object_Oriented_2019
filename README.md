## 面向对象第三次作业 - 允许嵌套的表达式求导

### 解析思路-递归下降法+建立二叉表达式树求导

```
<Expr> = [+-]?<Item>([+-]<SubExpr>)?
<SubExpr> = <Item>([+-]<SubExpr>)?
<Item> = [+-]?<Factor>(*<SubItem>)?
<SubItem> = <Factor>(*<SubItem>)?
<Factor> = <Num>|x(^<Num>)?|sin(<Factor>)(^<Num>)?|cos(<Factor>)(^<Num>)?|(Expr)
```

### 表达式树结点类

1. FuncNode
   1. SinNode
   2. CosNode
   3. PowerNode
2. OpNode
   1. AddNode
   2. SubNode
   3. MulNode
3. ConstNode

每个类对应接口，有自己的求导方法。



### 错误格式检查

1. 初级检查：
   1. 字符范围检查
   2. 非法空格形式检查
   3. 左右括号匹配检查
   4. 空串检查
2. 深度检查：利用递归下降过程中的不符合语法的情况，扔出异常。



### 字符串简化

1. 结点是否为0判断：乘法可直接输出为0，加减法输出时至多可省略一项。
2. 结点是否为1判断：乘法输出时最多可省略一项。
3. 结点是否为常数：求导后为0.