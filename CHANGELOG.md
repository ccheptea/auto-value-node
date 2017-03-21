Change Log
==========

Version 0.2.0 *(2017-03-21)*
----------------------------

#### New methods
* ``withValue(Action1)`` executes an action with the value;
* ``match(Predicate)`` returns a Node with the value if the predicate is true; a Node with null otherwise
* ``map(Mapper)`` returns a mapped value defined by Mapper;
* ``orAlernative(alternativeValue)`` returns a Node containing the value if it is not null; a Node with alternativeValue otherwise
* ``isAbsent()`` returns ``false`` if the value is not ``null``; ``true`` otherwise;
* ``value(alternativeValue)`` returns the value contained by the node if it is present; alternativeValue otherwise;

#### Changed methods
* ``ifNotPresent(Action)`` changed to ``ifAbsent(Action)``
* ``exists()`` changed to ``isPresent()``

#### Fixes

* Node_<autovalueclass> class should match AutoValue class;
* Node_<autovalueclass> methods' visibility should match the corresponding property's visibility

Version 0.1.3 *(2017-03-16)*
----------------------------

Initial release. Supports AutoValue 1.4-rc3.