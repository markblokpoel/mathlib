package mathlib.probability

import mathlib.graph.DiGraph

class BayesianNetwork(
                       network: DiGraph[Variable],
                       conditionalProbabilityDistributions: Map[Variable, ()],

                     ) {

}
