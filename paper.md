---
title: 'mathlib: A Scala package for readable, verifiable and sustainable simulations of formal theory'
tags:
  - psychology
  - cognitive science
  - simulations
  - formal theory
  - computational modeling
  - Scala
authors:
  - name: Mark Blokpoel
    orcid: 0000-0002-1522-0343
    affiliation: 1
affiliations:
  - name: Donders Institute for Brain, Cognition, and Behaviour, Radboud University, The Netherlands
    index: 1
date: 23 August 2023
bibliography: paper.bib
---

# Summary

Formal theory and computational modeling are critical in cognitive science and psychology. These methods allow
scientists to 'conceptually analyze, specify, and formalize intuitions that otherwise remain unexamined'
[@guest_martin:2021]. They make otherwise underspecified theories precise and open for critical reflection
[@vanrooij_baggio:2021]. A theory can be formally specified in a computational model using mathematical concepts
such as set theory, graph theory and probability theory.  The specification is often followed by analysis to understand
precisely what assumptions and consequences the formal theory entails. An important method of analysis is computer
simulation, which allows scientists to explore complex model behaviours and derive predictions that cannot (easily)
be analytically derived.

```mathlib``` is a library for Scala [@odersky:2008] supporting functional programming that resembles mathematical 
expressions such as set theory, graph theory and probability theory. This library was developed to complement the
theory development method outlined in the open education book Theoretical modeling for cognitive science and
psychology by @blokpoel_vanrooij:2021.

The goal of this library is to facilitate users to implement simulations of their formal theories. Code written in
Scala using ```mathlib``` is:

* easy to **read**, because ```mathlib``` syntax closely resembles mathematical notation
* easy to **verify**, by proving that the code exactly implements the theoretical model (or not)
* easy to **sustain**, because newer versions of Scala and ```mathlib``` can run old code (backwards compatibility)

# Statement of need

Writing code is not easy, writing code for which we can know that it computes what the specification
(i.e., the formal theory) states is even harder. This can be facilitated by having a programming language where the
syntax and semantics closely matches that of the specification. Since formal theories are specified using mathematical
notation [@marr:1982, @blokpoel_vanrooij:2021, @guest_martin:2021], functional programming languages bring a lot to the
table in terms of syntactic and semantic resemblance to mathematical concepts and notation. ```mathlib``` adds
mathematical concepts and notation to the functional programming language Scala [@odersky:2008], specifically in the
current version: set theory and graph theory. ```mathlib``` differs from other libraries in that is focuses on
usability and transparency, whereas other libraries focus on computational expressiveness at the cost of accessibility
and transparency.

Given the important role of theory and computer simulations, it is important that scholars can verify that the code
does what the authors intent it to do. We provide an example below of a formalization of subset choice and its
implementation in Scala using ```mathlib``` to illustrate this.

**Subset choice**

*Input:* A set of items $I$, a value function for single items $v:I\rightarrow \mathbb{Z}$ and a binary value function
for pairs of items $b:I \times I \rightarrow \mathbb{Z}$.

*Output:* A subset of items $I'\subseteq I$ (or $I'\in\mathcal{P}(I)$) that maximizes the combined value of the
selected items according, i.e., $\arg\max_{I'\in\mathcal{P}(I)}\sum_{i \in I'}v(i) + \sum_{i, j \in I'}b(i,j)$.

```scala
type Item = String

def subsetChoice(
  items: Set[Item],
  v: (Item => Double),
  b: ((Item, Item) => Double)
): Set[Set[Item]] = {

    def value(subset: Set[Item]): Double = 
      sum(subset, v) + sum(subset.uniquePairs, b)

    argMax(powerset(items), value)
}
```

Finally, ```mathlib``` and Scala are designed to be backwards compatible, i.e., to run on future systems and future
execution contexts (e.g., operating systems). Many programming contributions in academia are lost because
of incompatibility issues between versions and newer operating systems, etc. This sometimes affects contributions
within a short timeframe, and means that it is incredibly hard for anyone to run or verify the code and
simulation results.

# Resources

* Github repository: [https://github.com/markblokpoel/mathlib](https://github.com/markblokpoel/mathlib)
* Website: [https://markblokpoel.github.io/mathlib](https://markblokpoel.github.io/mathlib)
* Scaladoc: [https://markblokpoel.github.io/mathlib/scaladoc](https://markblokpoel.github.io/mathlib/scaladoc)
* Tutorials: [https://github.com/markblokpoel/mathlib-examples](https://github.com/markblokpoel/mathlib-examples)

# Acknowledgements

We thank the Computational Cognitive Science group at the Donders Center for Cognition (Nijmegen, The Netherlands)
for useful discussions and feedback, in particular, Laura van de Braak, Olivia Guest and Iris van Rooij.

This project was supported by Netherlands Organization for Scientific Research Gravitation Grant of the
Language in Interaction consortium 024.001.006, the Radboud School for Artificial Intelligence and the
Donders Institute, Donders Center for Cognition.

# References
