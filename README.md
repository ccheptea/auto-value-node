# AutoValue: Node Extension [![Build Status](https://travis-ci.org/ccheptea/auto-value-node.svg?branch=master)](https://travis-ci.org/ccheptea/auto-value-node)

An extension for Google's [AutoValue](https://github.com/google/auto/tree/master/value) that:

* Eliminates null checks when accessing AutoValue object fields;
* Prevents ``NullPointerException``'s;
* Increases code readability and maintainability (less if-else blocks);
* Easier to read and understand than Java 8 Optionals
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
a driver that is not a member of any club. Or, what if the driver is member of a club, but its email is not listed?
We will have to check all those before printing. We will end up with this:

```java
if(car.driver()!=null && car.driver().club()!= null && car.driver().club().contact() != null){
    System.out.println(car.driver().club().contact().email());
}
```

This works but it is hard to read and to maintain. And most likely you'll have plenty of places in your code where
you'll need to write similar checks.

Using **auto-value-node** the code above will become like this (assuming you use lambdas):

```java 
car.node().driver().club().contact().email().ifPresent(System.out::println);
```

## Usage
Integrating **auto-value-node** takes very little effort. First [add the dependencies][download], then add the following
method to each AutoValue class:

```java
public abstract Node_<your_class_name> node();
```
Make sure to replace "<your_class_name>" with the actual class name. That is all.

### Node methods
* ``withValue(Action1)`` executes an action with the value;
* ``ifPresent(Action1)`` executes an action with the value if it is not ``null``, followed by
    * ``otherwise(Action)`` executes an action if the value is ``null``;
* ``ifAbsent(Action)`` executes an action if the value is ``null``, followed by
    * ``otherwise(Acton1)`` applies an action on the value if it is not ``null``;
* ``match(Predicate)`` returns a Node with the value if the predicate is true; a Node with null otherwise
* ``map(Mapper)`` returns a mapped value defined by Mapper (e.g.: ``car.node().driver().club().map(Observable::just)`` == ``Observable.just(car.driver().club())``);
* ``orAlernative(alternativeValue)`` returns a Node containing the value if it is not null; a Node with alternativeValue otherwise
* ``isPresent()`` returns ``true`` if the value is not ``null``; ``false`` otherwise;
* ``isAbsent()`` returns ``false`` if the value is not ``null``; ``true`` otherwise;
* ``value()`` returns the value contained by the node;
* ``value(alternativeValue)`` returns the value contained by the node if it is present; alternativeValue otherwise;

## Download

```groovy
annotationProcessor 'com.ccheptea.auto.value:auto-value-node:0.2.8'
compile 'com.ccheptea.auto.value:auto-value-node-runtime:0.2.8'
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
[download]: https://github.com/ccheptea/auto-value-node#download
