<img src="docs/mathlib-logo-full.png" alt="MATHLIB logo" title="MATHLIB" style="width: 100%; max-width: 400px;" />

[![mathlib Scala version support](https://index.scala-lang.org/markblokpoel/mathlib/mathlib/latest.svg)](https://index.scala-lang.org/markblokpoel/mathlib/mathlib)
[![license](https://img.shields.io/badge/license-%20GPL--3.0-blue)](https://github.com/markblokpoel/mathlib/blob/master/LICENSE)

```mathlib``` is a supporting library for programming computer simulations for theoretical models. Models are expected to be formally defined using math (e.g., set, graph or probability theory). ```mathlib``` supports the programmer in writing code that is easier to:

* 🕶 read: ```mathlib``` syntax closely resembles mathematical notation
* ✅ verify: prove that the simulation exactly implements the theoretical model
* ❤ sustain: newer versions of Scala and ```mathlib``` can run old code (backwards compatibility)

This library was developed to complement the theory development method outlined in the book Theoretical modeling for cognitive science and psychology by Blokpoel and van Rooij (2021). You can [read the book for free](https://computationalcognitivescience.github.io/lovelace/).

## Under development

This library is still under development. Here is the status of each component:

* The set theory functionality is complete.
* Probability theory is based on double and therefore lacks precision, but it usable.
* Graph theory is still under development.

## Roadmap

* [ ] Write Scaladoc
* [ ] Link to ```mathlib``` tutorial and examples
* [ ] Add more precision to probability theory using BigNatural (jvm only)

## License

This software is licensed under the [GNU General Public License v3.0](LICENSE).
