---
# Feel free to add content and custom Front Matter to this file.
# To modify the layout, see https://jekyllrb.com/docs/themes/#overriding-theme-defaults

layout: default
title: Home
order: 0
---


<img src="mathlib-logo-full.png" alt="MATHLIB logo" title="MATHLIB" style="width: 100%; max-width: 800px;" />

[![mathlib Scala version support](https://index.scala-lang.org/markblokpoel/mathlib/mathlib/latest.svg)](https://index.scala-lang.org/markblokpoel/mathlib/mathlib)
[![license](https://img.shields.io/badge/license-%20GPL--3.0-blue)](https://github.com/markblokpoel/mathlib/blob/master/LICENSE)

```mathlib``` is a supporting library for programming computer simulations for theoretical models. Models are expected to be formally defined using math (e.g., set, graph or probability theory). ```mathlib``` supports the programmer in writing code that is easier to:

* 🕶 read: ```mathlib``` syntax closely resembles mathematical notation
* ✅ verify: prove that the simulation exactly implements the theoretical model
* ❤ sustain: newer versions of Scala and ```mathlib``` can run old code (backwards compatibility)

This library was developed to complement the theory development method outlined in the book Theoretical modeling for cognitive science and psychology by Blokpoel and van Rooij (2021). You can [read the book for free](https://computationalcognitivescience.github.io/lovelace/).

You can find an extensive tutorial on the basics of Scala and using ```mathlib``` in the [markblokpoel/mathlib-examples](https://github.com/markblokpoel/mathlib-examples) GitHub repository. The tutorials include a link to an online service (binder) where you can try out the library without needing to install anything.

## Under development

This library is still under development. Here is the status of each component:

* The set theory functionality is complete.
* Probability theory is based on double and therefore lacks precision, but it usable.

## Roadmap

* [ ] Rewrite probability theory to support probability distrubtions with multiple conditionals

## License

This software is licensed under the [GNU General Public License v3.0](LICENSE).
