package com.huyingbao.core.arch.scope

import javax.inject.Scope

/**
 * Java中，单例通常保存在一个静态域中，这样的单例往往要等到虚拟机关闭时候，该单例所占用的资源才释放。
 * 但是，Dagger通过[ActivityScope]创建出来的单例并不保持在静态域上，而是保留在Component(ActivityComponent)实例中。
 *
 * [Scope]是需要成对存在的。
 *
 * 在Module的Provide方法中使用了[Scope]，那么对应的Component中也必须使用[Scope]注解，
 * 当两边的[Scope]名字一样时（比如同为@Singleton）, 那么该Provide方法提供的依赖将会在Component中保持“局部单例”。
 *
 * 1.依赖在Component中是单例的（供该依赖的provide方法和对应的Component类使用同一个[Scope]注解。
 *
 * 2.对应的Component在App中只初始化一次，每次注入依赖都使用这个Component对象。（在Application中创建该Component）。
 *
 * 正常情况下没有任何[Scope]标示的Component，注入器每次根据依赖都会生成新的实例。
 *
 * SubComponent必须使用和父Component不同的[Scope]标示。
 *
 * Subcomponent用于拓展原有component，
 * Subcomponent同时具备两种不同生命周期的scope，
 * Subcomponent具备了父Component拥有的[Scope]，也具备了自己的[Scope]，
 * 注意Subcomponent的[Scope]范围小于父Component。
 *
 * Subcomponent其功能效果优点类似component的dependencies，
 * 但是使用@Subcomponent不需要在父component中显式添加子component需要用到的对象，
 * 只需要添加返回子Component的方法即可， 子Component能自动在父Component中查找缺失的依赖。
 *
 * 通过Subcomponent，子Component就好像同时拥有两种[Scope]，
 * 当注入的元素来自父Component的Module，则这些元素会缓存在父Component，
 * 当注入的元素来自子Component的Module，则这些元素会缓存在子Component中。
 *
 * SubComponent 完全继承Component中的全部依赖， 两个拥有依赖关系的 Component 是不能有相同 [Scope] 注解的，
 * 使用@SubComponent 则可以使用相同的[Scope]注解。
 *
 * Component dependencies 能单独使用, Subcomponent必须由Component调用方法获取。
 *
 * Component dependencies 可以很清楚的得知他依赖哪个Component，而Subcomponent不知道它自己的谁的孩子。
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope