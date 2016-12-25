
object ImplicitHelpers {

  implicit class RichIntTuple(tup: (Int, Int)) {

    // An implementation of the .to construct implicitly defined for int tuples
    def to(tup2: (Int, Int)): List[(Int, Int)] = {

      // We need to split this up if we are dealing with diagonals
      if (tup._1 != tup2._1 && tup._2 != tup2._2) {

        if (tup._2 > tup2._2) {
          // Build up a collection of cordinates on the respective diagonal
          tup._1.to(tup2._1).foldLeft(List[(Int, Int)]()) { (li: List[(Int, Int)], n: Int) => 
            (tup._1 + n, tup._2 - n)
          }
        } else {
          0.to(tup2._1-tup._1).foldLeft(List[(Int, Int)]()) { (li: List[(Int, Int)], n: Int) => 
            (tup._1 + n, tup._2 + n)
          }
        }
      } else {
        tup._1.to(tup2._1).flatMap(x => tup._2.to(tup2._2).map(y => (x, y))).toList
      }
      
    }

  }

}
