package com.huyingbao.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注Application(RxApp子类)本身，生命周期持有者，被观察者。
 * <p>
 * 与{@link RxAppObserver}配合使用。
 * <p>
 * （主模块）一个{@link RxAppOwner} 对 （子模块）多个{@link RxAppObserver}
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RxAppOwner {
}