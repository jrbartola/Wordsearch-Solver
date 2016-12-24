
object ImplicitHelpers {

  implicit class RichIntTuple(tup: (Int, Int)) {

    // An implementation of the .to construct implicitly defined for int tuples
    def to(tup2: (Int, Int)): List[(Int, Int)] = {
      tup._1.to(tup2._1).flatMap(x => tup._2.to(tup2._2).map(y => (x, y))).toList
    }

  }

}
