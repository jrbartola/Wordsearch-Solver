

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

  private def findWord(word: String): Option[String] = {
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
    var found = false
    var (startCoord, endCoord) = ((0,0), (0,0))

    // Check Rows
    for (i <- 0.until(rowArr.length-1)) {
      // Keep track of index of 
      val index = rowArr(i).indexOf(word)
      if (index != -1) {
        found = true
        (startCoord, endCoord) = ((i, index), (i, index + length - 1))
      }
    }


    // Check reversed Rows
    for (i <- 0.until(revRowArr.length -1)) {
      
    }

    // Check Columns
    for (j <- 0.until(colArr))



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