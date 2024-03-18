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

Formal theory and computational modeling are critical in cognitive science and psychology. Formal systems (e.g.,
set theory, functions, first-order logic, graph theory, etc.) allow
scientists to 'conceptually analyze, specify, and formalize intuitions that otherwise remain unexamined'
[@guest_martin:2021]. They make otherwise underspecified theories precise and open for critical reflection
[@vanrooij_baggio:2021]. A theory can be formally specified in a computational model using mathematical concepts
such as set theory, graph theory and probability theory.  The specification is often followed by analysis to understand
precisely what assumptions and consequences the formal theory entails. An important method of analysis is computer
simulation, which allows scientists to explore complex model behaviours and derive predictions that otherwise cannot
be analytically derived[^1].

[^1]: Computer simulations can also aid scientists to discover properties of the model that they would not have
thought to analytically derive, even when in principle the property can be analytically derived.

```mathlib``` is a library for Scala [@odersky:2008] supporting functional programming that resembles mathematical 
expressions such as set theory, graph theory and probability theory. This library was developed to complement the
theory development method outlined in the open education book Theoretical modeling for cognitive science and
psychology by @blokpoel_vanrooij:2021.

The goal of this library is to facilitate users to implement simulations of their formal theories. Code written in
Scala using ```mathlib``` is:

* easy to **read**, because ```mathlib``` syntax closely resembles mathematical notation
* easy to **verify**, by proving that the code exactly implements the theoretical model (or not)
* easy to **sustain**, older versions of Scala and ```mathlib``` can easily be run on newer machines

# Statement of need

Writing code is not easy, writing code for which we can know that it computes what the specification
(i.e., the formal theory) states is even harder. This can be facilitated by having a programming language where the
syntax and semantics closely matches that of the specification. Since formal theories are specified using mathematical
notation [@marr:1982, @blokpoel_vanrooij:2021, @guest_martin:2021], functional programming languages bring a lot to the
table in terms of syntactic and semantic resemblance to mathematical concepts and notation. ```mathlib``` adds
mathematical concepts and notation to the functional programming language Scala [@odersky:2008], specifically in the
current version (0.9.1): set theory and graph theory. ```mathlib``` differs from other libraries in that it focuses on
usability and transparency, whereas other libraries focus on computational expressiveness and efficiency.

Given the critical role of theory and computer simulations in cognitive science, it is important that scholars
can verify that the code does what the authors intent it to do. We provide two examples to illustrate how Scala 
and ```mathlib``` make code more accessible to verify the relationship between simulation and theory.

```mathlib``` and Scala support scholars to address the issue of out-of-date code. It is important that
academic contributions remain accessible for reflection and critique. This includes simulations that also have
theoretical import. Simulation results may need to be verified, or future scholars may wish to expand upon the work.
It is not sufficient to archive code, because in practise programming contributions in academia are easily lost because
of incompatibility issues between older software and newer operating systems. Scala (and consequently ```mathlib```) 
runs on the Java Virtual Machine (JVM) and has state-of-the-art versioning. The programmer can specify exactly which
version of Scala and ```mathlib``` needs to be retrieved to run the code on any system that supports the JVM. Even
when newer versions of Scala or ```mathlib``` may potentially break older code, this versioning system allows future
users to easily run older code.

What makes ```mathlib``` unique is that its design encourages the computational cognitive scientist to write
readable, verifiable and sustainable code for simulations formal theories. This is not to say that one cannot
apply these principles in other languages, but it may require building the mathematical infrastructure that
```mathlib``` affords.

# Illustrations

We present illustrate the relationship between simulations implemented in Scala and ```mathlib``` and formal theories.

## Illustration 1: Subset choice

The following formal theory is taken from the textbook by [@blokpoel_vanrooij:2021]. It specifies people's capacity 
to select a subset of items, given the value of individual items and pairs. For more details on this topic, see
Chapter 4 of the textbook.  

<span style="font-variant: small-caps;">Subset choice</span>

*Input:* A set of items $I$, a value function for single items $v:I\rightarrow \mathbb{Z}$ and a binary value function
for pairs of items $b:I \times I \rightarrow \mathbb{Z}$.

*Output:* A subset of items $I'\subseteq I$ (or $I'\in\mathcal{P}(I)$) that maximizes the combined value of the
selected items according, i.e., $\arg\max_{I'\in\mathcal{P}(I)}\sum_{i \in I'}v(i) + \sum_{i, j \in I'}b(i,j)$.

Assuming familiarity with the formal theory, the ```mathlib``` implementation and Table \ref{tab:subset} below
illustrate how to interpret and verify the code relative to the mathematical expressions in the formal theory. In 
this code illustration, the following functionality is provided by ```mathlib```: ```sum(., .)```, ```argMax(., .)```
and ```powerset(.)```.

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

: Mappings between formal expression and ```mathlib``` implementation. \label{tab:subset}

| Formal expression                     | ```mathlib``` implementation and description                                                                              |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| n.a.                                  | ```Item```                                                                                                                |
|                                       | _Custom type for items._                                                                                                  |
| $I$                                   | ```items: Set[Item]```                                                                                                    |
|                                       | _A set of items._                                                                                                         |
| $v:I\rightarrow \mathbb{Z}$           | ```v: (Item => Double)```                                                                                                 | 
|                                       | _Value function for single items._                                                                                        |
| $b:I \times I \rightarrow \mathbb{Z}$ | ```b: ((Item, Item) => Double)```                                                                                         | 
|                                       | _Value function for pairs of items._                                                                                      |
| n.a.                                  | ```def value(subset: Set[Item]): Double```                                                                                | 
|                                       | _Function wrapper for the combined value of a subset._                                                                    |
| $\sum_{i \in I'}v(i)$                 | ```sum(subset, v)```                                                                                                      | 
|                                       | _Sum of single item values, where ```subset``` is $I'$._                                                                  |
| $\sum_{i, j \in I'}b(i,j)$            | ```sum(subset.uniquePairs, b)```                                                                                          | 
|                                       | _Sum of pair-wise item values, where ```uniquePairs``` generates all pairs ```(x, y)``` in ```subset``` with ```x!=y```._ |
| $\arg\max_{I'\in\mathcal{P}(I)}\dots$ | ```argMax(powerset(items), value)```                                                                                      |
|                                       | _Returns the element from the powerset of items that maximizes ```value```.                                               |


## Illustration 2: Coherence

Coherence theory [@thagard:1998] aims to explain people's capacity to infer a consistent set of beliefs given 
constraints between them. For example, the belief 'it rains' may have a negative constraint with 'wearing shorts'. 
To belief that it rains and not wearing shorts is consistent, but to belief that it rains and to wear shorts is
inconsistent. In case of consistency, the constraint is said to be _satisfied_. Coherence theory conjectures that
people infer truth-values for their beliefs so as to maximize the sum of weights of all satisfied constraints. For a
more detailed introduction to Coherence theory, see [@thagard:1998] and Chapter 5 in [@blokpoel_vanrooij:2021]

<span style="font-variant: small-caps;">Coherence</span>

*Input:* A graph $G=(V,E)$ with vertex set $V$ and edge set $E\subseteq V\times V$ that partitions into positive constraints $C^+$ and negative constraints $C^-$ (i.e., $C^+\cup C^-=E$ and $C^+\cap C^-=\varnothing$) and a weight function $w: E \rightarrow \mathbb{R}$.

*Output:* A truth value assignment $T:V \rightarrow \{true, false\}$ such that $Coh(T)=Coh^+(A,R)+Coh^-(T)$ is maximum. Here,
$$
Coh^+(T)=\displaystyle\sum_{(u,v)\in C^+}
\begin{cases}
w((u,v))\text{ if }T(u) = T(v)\\
0\text{ otherwise}
\end{cases}
$$
and
$$
Coh^-(T)=\displaystyle\sum_{(u,v)\in C^-}
\begin{cases}
w((u,v))\text{ if }T(u) \ne T(v)\\
0\text{ otherwise}
\end{cases}
$$

Assuming familiarity with the formal theory, the ```mathlib``` implementation and Table \ref{tab:coherence} below
illustrate how to interpret and verify the code relative to the mathematical expressions in the formal theory. In
this code illustration, the following functionality is provided by ```mathlib```: ```WUnDiGraph```, ```WUnDiEdge```,
```Node```, ```sum(., .)``` and ```allMappings(.)```.

```scala
def coherence(
  network: WUnDiGraph[String],
  positiveConstraints: Set[WUnDiEdge[Node[String]]]
): Map[Node[String], Boolean] = {
  val negativeConstraints: Set[WUnDiEdge[Node[String]]] = 
    network.edges \ positiveConstraints

  def cohPlus(assignment: Map[Node[String], Boolean]): Double = {
    def isSatisfied(pc: WUnDiEdge[Node[String]]): Double =
      if(assignment(pc.left) == assignment(pc.right)) pc.weight
      else 0.0
    sum(positiveConstraints, isSatisfied _)
  }

  def cohMinus(assignment: Map[Node[String], Boolean]): Double = {
    def isSatisfied(pc: WUnDiEdge[Node[String]]): Double =
      if(assignment(pc.left) != assignment(pc.right)) pc.weight
     else 0.0
    sum(negativeConstraints, isSatisfied _)
  }

  def coh(assignment: Map[Node[String], Boolean]): Double =
    cohPlus(assignment) + cohMinus(assignment)

  val allPossibleTruthValueAssignments = 
    network.vertices.allMappings(Set(true, false))
  val optimalSolutions = 
    argMax(allPossibleTruthValueAssignments, coh _)
  optimalSolutions.random.get
}
```

: Mappings between formal expression and ```mathlib``` implementation. \label{tab:coherence}

| Formal expression                | ```mathlib``` implementation and description                                                                                  |
|----------------------------------|-------------------------------------------------------------------------------------------------------------------------------|
| $G$                              | ```network: WUnDiGraph[String]```                                                                                             |
|                                  | _Undirected weighted graph with labels._                                                                                      |
| $C^+$                            | ```positiveConstraints: Set[WUnDiEdge[Node[String]]]```                                                                       |
|                                  | _Set of positive constraints as weighted edges._                                                                              |
| $C^-$                            | ```negativeConstraints: Set[WUnDiEdge[Node[String]]]```                                                                       |
|                                  | _Set of negative constraints, computed by subtracting the positive constraints from all edges in_ ```network```_._            |
| $w$                              | _Weights are represented not by an explicit function, but by a weighted graph._                                               |
| $T:V\rightarrow \{true, false\}$ | ```allPossibleTruthValueAssignments```                                                                                        |
|                                  | _The truth value assignments are explicitly listed by generating all mappings between vertices and_ ```Set(true, false)```_._ |
| $Coh^+(T)$                       | ```cohPlus(assignment: Map[Node[String], Boolean]): Double```                                                                 |
|                                  | _Returns the sum of weights of all satisfied positive constraints._                                                           |
| $Coh^-(T)$                       | ```cohMinus(assignment: Map[Node[String], Boolean]): Double```                                                                |
|                                  | _Returns the sum of weights of all satisfied negative constraints._                                                           |
| $Coh(T)$                         | ```coh(assignment: Map[Node[String], Boolean]): Double```                                                                     |
|                                  | _Returns the sum of weights of all satisfied constraints._                                                                    |
| n.a.                             | ```optimalSolutions```                                                                                                        |
|                                  | _Compute all truth value assignments with maximum coherence._                                                                 |
| n.a.                             | ```optimalSolutions.random.get```                                                                                             |
|                                  | _The formal specification is met when any maximum truth value assignment is returned, so we return a random maximum one._     |

# Resources

* Github repository: [https://github.com/markblokpoel/mathlib](https://github.com/markblokpoel/mathlib)
* Website: [https://markblokpoel.github.io/mathlib](https://markblokpoel.github.io/mathlib)
* Scaladoc: [https://markblokpoel.github.io/mathlib/scaladoc](https://markblokpoel.github.io/mathlib/scaladoc)

# Acknowledgements

We thank the Computational Cognitive Science group at the Donders Center for Cognition (Nijmegen, The Netherlands)
for useful discussions and feedback, in particular, Laura van de Braak, Olivia Guest and Iris van Rooij.

This project was supported by Netherlands Organization for Scientific Research Gravitation Grant of the
Language in Interaction consortium 024.001.006, the Radboud School for Artificial Intelligence and the
Donders Institute, Donders Center for Cognition.

# References
