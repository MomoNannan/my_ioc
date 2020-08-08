# 实现

## 基于 BeanFactory 的基本实现

IOC 工作原理如下:

1.   初始化 IOC 容器.
2.   读取配置文件
3.   解析 Bean 信息, 存储到一定的数据结构中 ( `BeanDefination` ).
4.   根据上述数据结构初始化相应的 Bean.
5.  注册对象间的依赖关系.

OK. 那么我们根据上述原理来设计一下基本组件. 为方便, 使用 json 格式的配置文件. 

### 组件设计

#### 设计原则

面向接口编程 ( Program to interface ). 即通过接口指定行为, 然后在具体的实现类中实现, 丰富行为.

#### 配置文件的读取

读取配置文件的**本质是加载配置关系**. 维护关系的可以是项目 CLASSPATH 下的配置文件或是本机的某个目录, 其它机器的某个目录, 通过网络来读取. 我们将这种配置关系以及需要读取的内容统一称为**资源 ( Resource )**. 为了方便, 我们默认使用resource 目录, 按名称读取.



#### 解析 Bean

配置文件中存储着 Bean 的定义, 以及 Bean 之间的依赖关系. 我们需要用某种数据结构来描述它, 以便于将其嵌入程序, 加载到内存. 

```json
class name: BeanDefinition
type      : interface
methods   : 
    setName();
    setBeanClassName();
    
    getName();
    getBeanClassName();
    
```

它有一个默认实现: `DefaultBeanDefinition`. 然后我们在读取配置文件时, 将其转成 `BeanDefinition` 的 List. 就完成了配置文件中的 bean 配置到内存中的数据结构. 需要注意的是, `BeanDefinition` 是个接口, 所以我们在解析 json 时应该保存到其实现类中.



#### 初始化 Bean

Bean 的初始化我们想要交给第三方来维护, 再需要使用时直接交付给调用方即可. 这可以通过通过**工厂方法模式的变体**来实现. 普通的工厂方法需要针对每一个产品建立一个工厂, 但 IOC 框架不行. 因为在前一种情况, 产品都是已知的, 所以在编写时可以确定工厂; 而后一种情况, Bean 是随时可能变化的, 不可能每一次修改都相应地增删工厂. 我们用一种 Java 原生的强大技术来创建实例 ---- **反射.**  我们称该接口为 `BeanFactory`

等等! 我们刚存入内存结构的 Bean 定义如何与初始化 Bean 的 `BeanFactory` 产生联系呢? 毕竟那是初始化的依据啊. 因此, 很自然地想到, 我们要将前者依赖到 `BeanFactory`  的实现类中. 由于我们是根据 `beanName` 创建, 获取 Bean. 所以很自然的使用 Map 这种数据结构. 而在许多应用场景中, 都可能存在并发访问的情况, 比如 Web 应用程序. 而 Bean 也是支持延迟加载的, 很可能出现多个线程访问一个未经初始化的 Bean 的情形. 所以需要使用并发版本的 Map ---- `ConcurrentHashMap.`

#### 方便的入口

为了方便用户使用, 我们对 BeanFactory 进行封装, 而我们是基于 `Json` 格式的, 因此称其为 `JsonApplicationContext`. 在创建其对象时, 会对整个容器初始化 .

### 不足

1.  解析 json 格式的配置文件时, 没有检查格式是否满足. 后续应该加上.


## `LitableBeanFactory` 的实现

`LitableBeanFactory` 是 `BeanFactory`  的扩展, 它的目的是获取某一类型的全部 `Bean` 实例, 它根据类型枚举其所有的 `Bean` 实例, 而不用被客户端请求时根据名称一个一个查找.

### 组件设计

### `LitableBeanFactory` 与其实现类 `DefaultListableBeanFactory`

本版本中提供了泛型版本的 `getBeans(Class<T> type)`.  既然 `LitableBeanFactory`  是 `BeanFactory` 的扩展, 很容易想到前者继承自后者, 而其实现类 `DefaultListableBeanFactory` 自然也继承自 `DefaultBeanFactory`, 只实现 `LitableBeanFactory`  中接口即可.



### 如何根据 Type 获取 all bean instances?

有了 `BeanFactory` 的经验, 我们很容易想到使用 Map 来缓存这种关系. 然而, 如果想要根据 `type` 获取全部的 `bean names`, 则又不方便了. 但是我们知道, 可以通过 `beanMap` 根据 `name` 获取对应的 `bean` 实例. 所以我们取中庸之道, 缓存 `type` 与 `all bean names` 的映射, 这样既可以根据 type 直接获取 `bean names`, 也可以方便地获取 `bean instances`.

由于我们能够根据 `BeanDefinition` 获取到 Class, 所以这种映射关系在注册 bean `definitions` 时一并缓存即可.



### 心得

1.  在阅读 Spring 源码时, 发现了一个不错的编程习惯:

    对于超类的方法, 一律以 super 引用; 对于自身的域, 一律以 this 引用, 自身的方法, 直接引用.

    ```java
      // org.springframework.beans.factory.support.DefaultListableBeanFactory#destroySingletons
      @Override
    	public void destroySingletons() {
    		super.destroySingletons();
    		this.manualSingletonNames.clear();
    		clearByTypeCache();
    	}
    ```

    
