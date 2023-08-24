---
title: 'mathlib: '
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
such as set theory, graph theory and probability theory.  Following this specification comes analysis to understand
precisely what assumptions and consequences the formal theory entails. An important method of analysis is computer
simulation, which allows scientists to explore complex model behaviours and derive predictions that cannot (easily)
be analytically derived.

```mathlib``` is a library for Scala supporting functional programming that resembles mathematical expressions such
as set theory, graph theory and probability theory. This library was developed to complement the theory development
method outlined in the open education book Theoretical modeling for cognitive science and psychology by
@blokpoel_vanrooij:2021.

The goal of this library is to facilitate users to implement simulations of their formal theories.
Code written in Scala using ```mathlib``` is:

* easy to **read**, because ```mathlib``` syntax closely resembles mathematical notation
* easy to **verify**, by proving that the code exactly implements the theoretical model (or not)
* easy to **sustain**, because newer versions of Scala and ```mathlib``` can run old code (backwards compatibility)

# Statement of need

- elaborate on the need for readable, verifiable and sustainable code


# Acknowledgements

We thank the Computational Cognitive Science group at the Donders Center for Cognition (Nijmegen, The Netherlands)
for useful discussions and feedback, in particular, Laura van de Braak, Olivia Guest and Iris van Rooij.

This project was supported by Netherlands Organization for Scientific Research Gravitation Grant of the
Language in Interaction consortium 024.001.006, the Radboud School for Artificial Intelligence and the
Donders Institute, Donders Center for Cognition.

# References
