
import scala.math._
import ImplicitHelpers._

class WordSearch(private val matrix: Array[Array[Char]], private val dim: (Int, Int)) {
  /* Constructor Setup:
  * Setup the four Arrays of strings for searching
  * TODO: Implement diagonal searching
  */
  val rowArr: Array[String] = matrix.map(x => x.mkString)
  val colArr: Array[String] = 0.until(dim._2).map(r => 0.until(dim._1).map(c => matrix(c)(r))).
  							map(x => x.mkString).toArray
  val diagArr: Array[String] = {
    var arr: Array[String] = Array()
    for (i <- 1.until(dim._2)) {
      var row = 0
      var col = i
      var str = ""

      while (col >= 0) {
        str += matrix(row)(col)
        col -= 1
        row += 1
      }
      arr = arr :+ str
    }

    // Second half of obverse diagonals
    for (j <- 1.until(dim._1 - 1)) {
      var row = j
      var col = dim._2 - 1
      var str = ""

      while (row < dim._1 && col >= 0) {
        str += matrix(row)(col)
        row += 1
        col -= 1
      }
      arr = arr :+ str
    }
    arr
  }

  val revDiagArr: Array[String] = {
    var arr: Array[String] = Array()
    for (i <- (dim._2-2).to(0) by -1) {
      var row = 0
      var col = i
      var str = ""

      while (col < dim._2) {
        str += matrix(row)(col)
        row += 1
        col += 1
      }
      arr = arr :+ str
    }

    for (j <- 1.until(dim._1 - 1)) {
      var row = j
      var col = 0
      var str = ""

      while (row < dim._1) {
        str += matrix(row)(col)
        row += 1
        col += 1
      }
      arr = arr :+ str
    }
    arr
  }

  // Create an empty bit vector to represent visible characters in the printed matrix
  var mappedBitVector: Map[(Int, Int), Boolean] = {
    0.until(dim._1).toList.flatMap(x => 0.until(dim._2).toList.map(y => (x,y) -> false)).toMap
  }

  private def resetBitVector(): Unit = {
    mappedBitVector = 0.until(dim._1).toList.flatMap(x => 0.until(dim._2).toList.map(y => (x,y) -> false)).toMap
  }
  
  /* Prints out a string representation of the matrix
  *  with the param word identified
  *  
  *  If the word was not found, the entire matrix is returned
  */
  def find(word: String): String = findWord(mappedBitVector, word) match {
  	case Some(stringrep) => stringrep
  	case None => toString(mappedBitVector)
  }


  def find(words: List[String]): String = {
    val finBoard = words.map(w => find(w)).last
    resetBitVector
    finBoard
  }

  private def findWord(mat: Map[(Int, Int), Boolean], word: String): Option[String] = {
    /* 1. Search for word in each row-- this is easy.
    *     - Go through each string in the rowArr and revRowArr
    *       and note the start ing index and ending index
    *       (i.e. add the length of the word to the starting index)
    *     - Count in reverse for revRowArr!
    *  2. Search for words in each column.
    *     - Go through each string in the colArr and revColArr
    *       and if found, reverse the coordinates to transform
    *       the matrix to its original row-oriented state!
    */

    val length = word.length
    val revWord = word.reverse
    //var (startCoord, endCoord) = ((0,0), (0,0))

    // Formulae to calculate starting and ending indices of words in the matrix
    def calcRevIndex(rowLength: Int, ix: Int): Int = rowLength match {
      case x if x % 2 == 0 => (length - 1) - 2*ix + ix
      case x if x % 2 == 1 => {
        val center = rowLength / 2
        (ix + 2*abs(center - ix)) % (length - 1)
      }
    }

    // Check Rows
    for (i <- 0.until(rowArr.length)) {
      // Keep track of the index for normalized and reversed word
      val index = rowArr(i).indexOf(word)
      val revIndex = rowArr(i).indexOf(revWord)

      if (index != -1) {
        val (startCoord, endCoord) = ((i, index), (i, index + length - 1))
        return Some(exposeString(mat, startCoord, endCoord))
      } else if (revIndex != -1) {
        val (startCoord, endCoord) = ((i, revIndex), (i, revIndex + length - 1))
        return Some(exposeString(mat, startCoord, endCoord))
      }
    }

    // Check Columns
    for (j <- 0.until(colArr.length)) {
      // Keep track of index for normalized and reversed word
      val index = colArr(j).indexOf(word)
      val revIndex = colArr(j).indexOf(revWord)

      if (index != -1) {
        val (startCoord, endCoord) = ((index, j), (index + length - 1, j))
        return Some(exposeString(mat, startCoord, endCoord))
      } else if (revIndex != -1) {
        val (startCoord, endCoord) = ((revIndex, j), (revIndex + length - 1, j))
        return Some(exposeString(mat, startCoord, endCoord))
      }
    }
    
    // Check obverse diagonals (top-right to bottom-left)
    for (k <- 0.until(diagArr.length)) {
      // Keep track of index for normalized and reversed word
      val index = diagArr(k).indexOf(word)
      val revIndex = diagArr(k).indexOf(revWord)
      val entrySize = diagArr(k).length
      
      // There are two formulae for transforming diagonal coords to the matrix
      if (k < ceil((diagArr.length / 2.0)).toInt) {
        if (index != -1) {
          val (startCoord, endCoord) = ((index, (k+1) - index), (index + length - 1, (k+2) - index - length))
          return Some(exposeString(mat, startCoord, endCoord)) 
        } else if (revIndex != -1) {
          val (startCoord, endCoord) = ((revIndex, (k+1) - revIndex), (revIndex + length - 1, (k+2) - revIndex - length))
          return Some(exposeString(mat, startCoord, endCoord))
        }
      } else {
        if (index != -1) {
          val startCoord = (k - dim._1 + 2 + index, dim._2 - 1 - index)
          val endCoord = (k - dim._1 + 1 + index + length, dim._2 - index - length)
          return Some(exposeString(mat, startCoord, endCoord)) 
        } else if (revIndex != -1) {
          val startCoord = (k - dim._1 + 2 + revIndex, dim._2 - 1 - revIndex)
          val endCoord = (k - dim._1 + 1 + revIndex + length, dim._2 - revIndex - length)
          return Some(exposeString(mat, startCoord, endCoord))
        }
      }
      
    }
    
    // Check reverse diagonals (top-left to bottom-right)
    for (l <- 0.until(revDiagArr.length)) {
      // Keep track of index for normalized and reversed word
      val index = revDiagArr(l).indexOf(word)
      val revIndex = revDiagArr(l).indexOf(revWord)
      
      if (l < ceil((revDiagArr.length / 2.0)).toInt) {
        if (index != -1) { 
          val (startCoord, endCoord) = ((index, dim._1 - 2 - l + index), (index + length -1, dim._1 - 3 - l + index + length))
          return Some(exposeString(mat, startCoord, endCoord)) 
        } else if (revIndex != -1) {
          val (startCoord, endCoord) = ((revIndex, dim._1 - 2 - l + revIndex), (revIndex + length -1, dim._1 - 3 - l + revIndex + length))
          return Some(exposeString(mat, startCoord, endCoord))
        }
      } else {
        if (index != -1) {
          val (startCoord, endCoord) = ((abs(dim._1 - 2 - l + index), index), (abs(dim._1 - 2 - l + index) + length-1, index + length -1))
          return Some(exposeString(mat, startCoord, endCoord)) 
        } else if (revIndex != -1) {
          val (startCoord, endCoord) = ((abs(dim._1 - 2 - l + revIndex), revIndex), (abs(dim._1 - 2 - l + revIndex) + length-1, revIndex + length -1))
          return Some(exposeString(mat, startCoord, endCoord))
        }
      }

    }


    // Return None is the word was not found
    None

  }

  private def exposeString(mat: Map[(Int, Int), Boolean], start: (Int, Int), end: (Int, Int)): String = {
    
    val modifiedBits = start.to(end).foldLeft(mat) { (m: Map[(Int, Int), Boolean], c: (Int, Int)) => 
      m + (c -> true)
    }

    mappedBitVector = modifiedBits
    toString(modifiedBits)
  }

  override def toString(): String = {
  	val b = new StringBuilder(dim._1 * dim._2 * 3)
    b ++= "\n"
  	for (r <- 0.until(dim._1)) {
  	  for (c <- 0.until(dim._2)) {
  	  	b ++= matrix(r)(c) + " "
  	  }
  	  b ++= "\n"
  	}
  	b.toString
  }

  def toString(mat: Map[(Int, Int), Boolean]): String = {
    val b = new StringBuilder(dim._1 * dim._2 * 3)
    b ++= "\n"
    for (r <- 0.until(dim._1)) {
      for (c <- 0.until(dim._2)) {
        // Now we filter out characters that aren't marked
        mat.get((r, c)) match {
          case Some(bool) if bool => b ++= matrix(r)(c)+ " "
          case Some(bool) if !bool => b ++= "  "
          case None => b ++= "  "
        }
      }
      b ++= "\n"
    }
    b.toString
  }

}

// Companion Object
object WordSearch {
  def apply(matrix: Array[Array[Char]], dim: (Int, Int)): WordSearch = {
    new WordSearch(matrix, dim)
  }
}