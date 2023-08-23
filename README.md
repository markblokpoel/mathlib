![mathlib](docs/mathlib-logo-full.png)

[![mathlib Scala version support](https://index.scala-lang.org/markblokpoel/mathlib/mathlib/latest.svg)](https://index.scala-lang.org/markblokpoel/mathlib/mathlib)
[![license](https://img.shields.io/badge/license-%20GPL--3.0-blue)](https://github.com/markblokpoel/mathlib/blob/master/LICENSE)
[![Website](https://img.shields.io/website?url=https%3A%2F%2Fwww.markblokpoel.com%2Fmathlib)](https://www.markblokpoel.com/mathlib)
![Maven Central](https://img.shields.io/maven-central/v/com.markblokpoel/mathlib_2.13)

**This is a copy of the companion website main page, please visit [https://www.markblokpoel.com/mathlib](https://www.markblokpoel.com/mathlib).**

```mathlib``` is a library for Scala supporting functional programming that resembles
mathematical expressions such as set theory, graph theory and probability theory.
This library was developed to complement the theory development method outlined
in the open education book [Theoretical modeling for cognitive science and psychology by
Blokpoel and van Rooij (2021)<img style="height: 1rem;" src="{{site.baseurl}}/assets/open-in-new-black.png" />](https://computationalcognitivescience.github.io/lovelace/).

The goal of this project is to facilitate users to write Scala code that is:

* 👓 easy to **read**, because ```mathlib``` syntax closely resembles mathematical notation
* ✅ easy to **verify**, by proving that the code exactly implements the theoretical model (or not)
* ❤️ easy to **sustain**, because newer versions of Scala and ```mathlib``` can run old code (backwards compatibility)

## Using this library

For the scope of this documentation, we are going to assume you are familiar with setting up a blank Scala 2.13 project.
Some helpful links to get you started are:

* [Using IntelliJ to run Scala applications in
<img style="height: 1rem;" src="{{site.baseurl}}/assets/open-in-new-black.png" />](https://www.jetbrains.com/help/idea/run-debug-and-test-scala.html#run_scala_app)
* Use the binder service or a local instance of the Jupyterlab tutorials at [markblokpoel/mathlib-examples<img style="height: 1rem;" src="{{site.baseurl}}/assets/open-in-new-black.png" />](https://github.com/markblokpoel/mathlib-examples)

### Using this library with ```sbt```
If you have set up a Scala project with ```sbt```, the Scala built tool, then add the following line to your
```build.sbt``` file to enable the library. 

```scala
libraryDependencies += "com.markblokpoel" %% "mathlib" % "0.9.0"
```

### Using this library with Almond
If you have set up a Scala project in jupyter notebooks using [Almond<img style="height: 1rem;" src="{{site.baseurl}}/assets/open-in-new-black.png" />](https://almond.sh/) you can enable the library by
adding the following line to a worksheet. 

```scala
import $ivy.`com.markblokpoel::mathlib:0.9.0`
```

### Import ```mathlib``` packages

After enabling ```mathlib``` in your project you most likely will want to use some of its components. See the 
[development section](#development) below for the available components and their import statements. Adding these
import statements to a ```.scala``` file will import the component and allow you to write code using it. The examples
below show how this is done.

## Examples

The Github repository contains a few demos that can be found under
[mathlib/demos<img style="height: 1rem;" src="{{site.baseurl}}/assets/open-in-new-black.png" />](https://github.com/markblokpoel/mathlib/tree/main/src/main/scala/mathlib/demos). To run and play around
with these demos, first follow the instructions above for setting up this library. Then, download the scala files from
the repository and copy them into your Scala project. They can be run as any Scala application, e.g., [run Scala applications in 
Intellij<img style="height: 1rem;" src="{{site.baseurl}}/assets/open-in-new-black.png" />](https://www.jetbrains.com/help/idea/run-debug-and-test-scala.html#run_scala_app) or use the binder service of
the tutorials.

You can find extensive tutorials on the basics of Scala and using ```mathlib``` in the 
[markblokpoel/mathlib-examples<img style="height: 1rem;" src="{{site.baseurl}}/assets/open-in-new-black.png" />](https://github.com/markblokpoel/mathlib-examples) GitHub repository. The tutorials
include a link to an online service (binder) where you can try out the library without needing to install anything.

## Development

The current version of ```mathlib``` is v0.9.0 and the following components are complete, fully usable and will remain 
backwards compatible (i.e., code that uses these components will still run with future versions of ```mathlib```).
Currently, this library only supports Scala 2 and not Scala 3.

| Component | ```import``` statement | Minimum ```mathlib``` and Scala versions |
|--|--|:--:|:--:|
| Set theory | ```import mathlib.set.SetTheory._``` | v0.9.0 / 2.13.9 |
| Graph theory | ```import mathlib.graph._```<br/> ```import mathlib.graph.GraphImplicits._``` | v0.9.0 / 2.13.9 |

### Roadmap

The following features are currently under development or planned. Any existing code related to these features may
undergo significant changes in future versions. It may also contain bugs and code based on these features may not
work with future versions of ```mathlib```.

| Feature | Priority | Description | Completion |
|--|:--:|--|--|
| Probability revision | High | Rewrite probability implementation to support distributions with an arbitrary number of conditionals and high precision numbers. | Started initial exploration. |
| Scala 3 support | Medium | Port the library to Scala 3 and support backwards compatibility. | Not started. |
| Utilities | Medium | Explore utility functions (e.g., bounded search tree) and write documentation | Initial draft. |
| Graph theory: Hyper graphs | Low | Implement basic graph algorithms for hyper graphs. | Not started. |
| Graph theory: Trees | Low | Implement tree graph types and algorithms. | Not started. |
|&nbsp;|&nbsp;|&nbsp;|&nbsp;|
| Set theory | | Complete implementation and documentation. | Completed in v0.9.0 |
| Graph theory | | Complete implementation and documentation for non-hyper graphs. | Completed in v0.9.0 |


## License

This software is licensed under the [GNU General Public License v3.0](LICENSE).
