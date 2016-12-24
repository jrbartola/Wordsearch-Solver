import scala.math._

class WordSearch(private val matrix: Array[Array[Char]], private val dim: (Int, Int)) {
  /* Constructor Setup:
  * Setup the four Arrays of strings for searching
  * TODO: Implement diagonal searching
  */
  val rowArr: Array[String] = matrix.map(x => x.mkString)
  val revRowArr: Array[String] = rowArr.map(x => x.reverse)
  val colArr: Array[String] = 0.until(dim._2).map(r => 0.until(dim._1).map(c => matrix(c)(r))).
  							map(x => x.mkString).toArray
  val revColArr: Array[String] = colArr.map(x => x.reverse)

  /* Prints out a string representation of the matrix
  *  with the param word identified
  *  
  *  If the word was not found, the entire matrix is returned
  */
  def find(word: String): String = findWord(word) match {
  	case Some(stringrep) => stringrep
  	case None => toString()
  }


  def find(words: List[String]): String = {
    "temp"
  }

  private def findWord(mat: Array[Array[Char]], word: String): Option[String] = {
    /* 1. Search for word in each row-- this is easy.
    *     - Go through each string in the rowArr and revRowArr
    *       and note the starting index and ending index
    *       (i.e. add the length of the word to the starting index)
    *     - Count in reverse for revRowArr!
    *  2. Search for words in each column. 
    *
    *
    *
    */

    val length = word.length
    var (startCoord, endCoord) = ((0,0), (0,0))

    // Formulae to calculate starting and ending indices of words in the matrix
    def revIndex(rowLength: Int, ix: Int): Int = rowLength match {
      case x if x % 2 == 0 => (length - 1) - 2*ix + ix
      case x if x % 2 == 1 => {
        val center = rowLength / 2
        (ix + 2*abs(center - ix)) % (length - 1)
      }
    }

    // Check Rows
    for (i <- 0.until(rowArr.length-1)) {
      // Keep track of the index
      val index = rowArr(i).indexOf(word)
      if (index != -1) {
        (startCoord, endCoord) = ((i, index), (i, index + length - 1))
        return Some(exposeString(mat, startCoord, endCoord))
      }
    }

    // Check reversed Rows
    for (i <- 0.until(revRowArr.length -1)) {
      val index = revRowArr(i).indexOf(word)
      if (index != -1) {
        val rowLength = revRowArr.length
        val endIndex = index + length - 1
        
        (startCoord, endCoord) = ((i, revRowIndex(rowLength, index)), (i, revRowIndex(rowLength, endIndex)))
        return Some(exposeString(mat, startCoord, endCoord))
      }
    }

    // Check Columns
    for (j <- 0.until(colArr.length -1)) {

    }

    // Check reversed Columns
    for (j <- 0.until(revColArr.length -1)) {

    }

    // Return None is the word was not found
    None



  }

  private def exposeString(mat: Array[Array[Char]], start: (Int, Int), end: (Int, Int)): String = {

  }

  override def toString(): String = {
  	val b = new StringBuilder(dim._1 * dim._2 * 3)
  	for (r <- 0.until(dim._1)) {
  	  for (c <- 0.until(dim._2)) {
  	  	b ++= matrix(r)(c) + " "
  	  }
  	  b ++= "\n"
  	}
  	b.toString
  }

}