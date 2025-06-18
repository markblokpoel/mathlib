![mathlib](docs/mathlib-logo-full.png)

[![mathlib Scala version support](https://index.scala-lang.org/markblokpoel/mathlib/mathlib/latest.svg)](https://index.scala-lang.org/markblokpoel/mathlib/mathlib)
[![license](https://img.shields.io/badge/license-%20GPL--3.0-blue)](https://github.com/markblokpoel/mathlib/blob/master/LICENSE)
[![Website](https://img.shields.io/website?url=https%3A%2F%2Fwww.markblokpoel.com%2Fmathlib)](https://www.markblokpoel.com/mathlib)
![Maven Central](https://img.shields.io/maven-central/v/com.markblokpoel/mathlib_2.13)
[![Scaladoc](https://img.shields.io/maven-central/v/com.markblokpoel/mathlib_2.13?label=scaladoc)](https://markblokpoel.com/mathlib/scaladoc/mathlib/)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](code_of_conduct.md)


**This is a copy of the companion website main page, please visit [https://www.markblokpoel.com/mathlib](https://www.markblokpoel.com/mathlib).**


 `mathlib ` is a library for Scala supporting functional programming that resembles
mathematical expressions such as set theory, graph theory and probability theory.
This library was developed to complement the theory development method outlined
in the open education book [Theoretical modeling for cognitive science and psychology by
Blokpoel and van Rooij (2021)](https://computationalcognitivescience.github.io/lovelace/).

The goal of this library is to facilitate users to implement simulations of their formal theories.  `mathlib ` and
Scala code is:

* 👓 easy to **read**, because  `mathlib ` syntax closely resembles mathematical notation
* ✅ easy to **verify**, by proving that the code exactly implements the theoretical model (or not)
* ❤️ easy to **sustain**, older versions of Scala and mathlib can easily be run on newer machines

## Using this library

For the scope of this documentation, we are going to assume you are familiar with setting up a blank Scala 2.13 project.
Some helpful links to get you started are:

* [Using IntelliJ to run Scala applications in
  ](https://www.jetbrains.com/help/idea/run-debug-and-test-scala.html#run_scala_app)
* Use the binder service or a local instance of the Jupyterlab tutorials
  at [markblokpoel/mathlib-examples](https://github.com/markblokpoel/mathlib-examples)

### Using this library with  `sbt `

If you have set up a Scala project with  `sbt `, the Scala built tool, then add the following line to your
 `build.sbt ` file to enable the library.

 `scala
libraryDependencies += "com.markblokpoel" %% "mathlib" % "0.9.1"
 `

### Using this library with Almond

If you have set up a Scala project in jupyter notebooks using [Almond](https://almond.sh/) you can enable the library by
adding the following line to a worksheet.

 `scala
import $ivy.`com.markblokpoel::mathlib:0.9.1`
 `

### Import  `mathlib ` packages

After enabling  `mathlib ` in your project you most likely will want to use some of its components. See the
[development section](#development) below for the available components and their import statements. Adding these
import statements to a  `.scala ` file will import the component and allow you to write code using it. The examples
below show how this is done.

## Examples

The Github repository contains a few demos that can be found under [mathlib/demos](https://github.com/markblokpoel/mathlib/tree/main/src/main/scala/mathlib/demos). To run and play around with
these demos there are two options.

*Option 1 (clean project):*  First follow the instructions above for setting up this library. Then, download the scala files from
the repository and copy them into your Scala project. They can be run as any Scala application,
e.g., [run Scala applications in
Intellij](https://www.jetbrains.com/help/idea/run-debug-and-test-scala.html#run_scala_app) 

*Option 2 (clone repository):* This option will download the full  `mathlib ` source code and assumes that git, Scala
and SBT have been installed (see installation instructions 
[for Scala and SBT here](https://docs.scala-lang.org/getting-started/sbt-track/getting-started-with-scala-and-sbt-on-the-command-line.html)
and [for git here](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)).
First, clone the GitHub repository:
 `
$ git clone https://github.com/markblokpoel/mathlib.git
 `
Open a terminal and  `cd ` into the repository root folder (usually called mathlib):
 `
$ cd mathlib
 `
Run a demo using the following command,  `runCoherenceDemo ` can be replaced by  `runVertexCoverDemo `, 
 `runGraphsDemo `, or  `runSubsetChoiceDemo `:
 `
$ sbt runCoherenceDemo
 `

## Tutorials (work in progress)

You can find extensive tutorials on the basics of Scala and using  `mathlib ` in the
[markblokpoel/mathlib-examples](https://github.com/markblokpoel/mathlib-examples) GitHub repository. The tutorials
include a link to an online service (binder) where you can try out the library without needing to install anything.

## Development

The current version of  `mathlib ` is v0.9.0 and the following components are complete, fully usable and will remain
backwards compatible (i.e., code that uses these components will still run with future versions of  `mathlib `).
Currently, this library only supports Scala 2 and not Scala 3.

| Component    |  `import ` statement                                                        | Minimum  `mathlib ` and Scala versions |
|--------------|-------------------------------------------------------------------------------|:----------------------------------------:|
| Set theory   |  `import mathlib.set.SetTheory._ `                                          |             v0.9.0 / 2.13.9              |
| Graph theory |  `import mathlib.graph._ `<br/>  `import mathlib.graph.GraphImplicits._ ` |             v0.9.0 / 2.13.9              |

### Roadmap

The following features are currently under development or planned. Any existing code related to these features may
undergo significant changes in future versions. It may also contain bugs and code based on these features may not
work with future versions of  `mathlib `.

| Feature                    | Priority | Description                                                                                                                      | Completion                   |
|----------------------------|:--------:|----------------------------------------------------------------------------------------------------------------------------------|------------------------------|
| Probability revision       |   High   | Rewrite probability implementation to support distributions with an arbitrary number of conditionals and high precision numbers. | Started initial exploration. |
| Scala 3 support            |  Medium  | Port the library to Scala 3 and support backwards compatibility.                                                                 | Not started.                 |
| Utilities                  |  Medium  | Explore utility functions (e.g., bounded search tree) and write documentation                                                    | Initial draft.               |
| Graph theory: Hyper graphs |   Low    | Implement basic graph algorithms for hyper graphs.                                                                               | Not started.                 |
| Graph theory: Trees        |   Low    | Implement tree graph types and algorithms.                                                                                       | Not started.                 |
| &nbsp;                     |  &nbsp;  | &nbsp;                                                                                                                           | &nbsp;                       |
| Set theory                 |          | Complete implementation and documentation.                                                                                       | Completed in v0.9.0          |
| Graph theory               |          | Complete implementation and documentation for non-hyper graphs.                                                                  | Completed in v0.9.0          |

## License

This software is licensed under the [GNU General Public License v3.0](LICENSE).

## About this project

### Authors

Mark Blokpoel,
[@markblokpoel](https://github.com/markblokpoel),
[https://orcid.org/0000-0002-1522-0343](https://orcid.org/0000-0002-1522-0343),
[https://markblokpoel.com](https://www.markblokpoel.com)

### Contributing

Thanks for considering contributing to this project. We welcome all contributions and feedback,
your help is essential for keeping it great.

Contributions can be made by providing feedback, requests or bug reports through 
[issues](https://github.com/markblokpoel/mathlib/issues) or by providing updated / extended code via a pull
request (see below). Please read our [Code of Conduct](code_of_conduct.md) before participating in this community.

Content contributions to this project are released to the public under the project's open source license. Whenever
you add Content to a repository containing notice of a license, you license that Content under the same terms,
and you agree that you have the right to license that Content under those terms. If you have a separate
agreement to license that Content under different terms, such as a contributor license agreement, that
agreement will supersede.

### Authorship and credit

If you contribute to this work, such as by a pull request (PR), please also add yourself to the author list in
[README.md](https://github.com/markblokpoel/mathlib/blob/main/README.md) file in the same PR, ideally with your
real name, your GitHub username, and your ORCID.

If you use this work, please credit/cite it and the [authors](https://markblokpoel.com/mathlib#authors):

Blokpoel, M., (2024). mathlib: A Scala package for readable, verifiable and sustainable simulations of formal theory. Journal of Open Source Software, 9(99), 6049, [https://doi.org/10.21105/joss.06049](https://doi.org/10.21105/joss.06049)

### Submitting a pull request

1. [Fork](https://github.com/markblokpoel/mathlib/fork) and clone the repository
2. Create a new branch: `git checkout -b my-branch-name`
3. Make your change
4. Push to your fork and [submit a pull request](https://github.com/markblokpoel/mathlib/compare)
5. Pat yourself on the back and wait for your pull request to be reviewed and merged.

### Acknowledgements

We thank
the [Computational Cognitive Science group<img style="height: 1rem;" src="{{site.baseurl}}/assets/open-in-new-black.png" />](https://www.ru.nl/donders/research/theme-2-perception-action-decision-making/research-groups-theme-2/computational-cognitive-science/)
at the Donders Center for Cognition (Nijmegen, The Netherlands) for
useful discussions and feedback. A special thanks to Laura van de Braak, [Olivia Guest](https://oliviaguest.com/)
and [Iris van Rooij](https://irisvanrooijcogsci.com/) whose conceptual contributions and support have been invaluable.
We further thank Max Hinne for helpful pointers on random graph generation. We also thank the reviewers
Larkin Liu, Russel Richie, and Stephen Mann and the editor Daniel Katz for their useful feedback which has greatly improved this paper. 

This project was supported by Netherlands Organization for Scientific Research Gravitation Grant of the [Language in
Interaction consortium<img style="height: 1rem;" src="{{site.baseurl}}/assets/open-in-new-black.png" />](https://www.languageininteraction.nl/)
024.001.006,
the [Radboud School for Artificial Intelligence<img style="height: 1rem;" src="{{site.baseurl}}/assets/open-in-new-black.png" />](https://www.ru.nl/en/education/bachelors/artificial-intelligence)
and
the [Donders Institute, Donders Center for Cognition<img style="height: 1rem;" src="{{site.baseurl}}/assets/open-in-new-black.png" />](https://www.ru.nl/donders/).
