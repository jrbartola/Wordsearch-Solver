
import java.util.Scanner
import scala.io.Source
import WordSearch._

object Solver extends App {
  
  def solve(): Unit = {
    val s = new Scanner(System.in);

	println("Enter the name of the file containing your wordsearch: ")
	val filename = s.nextLine

	val contents = Source.fromFile(filename).getLines.toList
	val numLines = contents(0).toInt
	val numWords = contents(numLines+1).toInt

	val wsmat = 1.to(numLines).map(x => contents(x).replace(" ", "").map(y => y).toArray).toArray
	  
	val w = WordSearch(wsmat, (numLines, wsmat(0).length))
	val wlist = (numLines+2).to(numLines+numWords+1).map(x => contents(x)).toList

	println("Solving your wordsearch...")
	print(w.find(wlist))
  }
 

}