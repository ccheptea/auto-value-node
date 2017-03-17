# AutoValue: Node Extension [![Build Status](https://travis-ci.org/ccheptea/auto-value-node.svg?branch=master)](https://travis-ci.org/ccheptea/auto-value-node)

An extension for Google's [AutoValue](https://github.com/google/auto/tree/master/value) that:

* Eliminates null checks when accessing object fields;
* Prevents ``NullPointerExeption``'s;
* Increases code readability and maintainability;
* Works great with Lambdas;

## Use case

Suppose you have the following data structure:

```
Car
    String model
    Driver driver
        String name
        Club club
            String title
            Integer members
            Contact contact
                String email
                String phone
```

Let's say you want to print the email for the club a car's driver is member of:

```java
System.out.println(car.driver().club().contact().email());
```

What if the car doesn't have a driver? Well, our code will break with a ``NullPointerException``. What if the car has 
a driver that is not a member of any club. Then what if the driver is member of a club, but its email is not listed?
We will have to check all those before printing. We will end up with this:

```java
if(car.driver()!=null && car.driver().club()!= null && car.driver().club().contact() != null){
    System.out.println(car.driver().club().contact().email());
}
```

It works but it is hard to maintain and to follow. Most likely you'll have plenty of places in your code where
you'll need to write similar checks.

Using **auto-value-node** the code above will become like this (assuming you use lambdas):

```java 
car.node().driver().club().contact().ifPresent(System.out::println);
```

## Usage
Integrating **auto-value-node** takes very little effort. First add the dependencies listed below, then add the following
method to each AutoValue class:

```java
public abstract Node_<your_class_name> node();
```
Make sure to replace "<your_class_name>" with the actual class name. That is all.

### Node methods
* ``ifPresent(Action1)`` applies an action on the value if it is not ``null``, followed by
    * ``otherwise(Action)`` executes an action if the value is ``null``;
* ``ifNotPresent()`` executes an action if the value is ``null``, followed by
    * ``otherwise`` applies an action on the value if it is not ``null``;
* ``exists()`` returns ``true`` if the value is not ``null``; ``false`` otherwise;
* ``value()`` returns the value contained by the node;

## What's in progress

* Support for RxJava (reactiveValue)
* Streams support
* A few other handy methods
* Corresponding visibility in Node classes

## Download

```groovy
annotationProcessor 'com.ccheptea.auto.value:auto-value-node:0.1.3'
compile 'com.ccheptea.auto.value:auto-value-node-runtime:0.1.3'
 ```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].

## License


```
Copyright 2017 Constantin Cheptea.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[snap]: https://oss.sonatype.org/content/repositories/snapshots/
