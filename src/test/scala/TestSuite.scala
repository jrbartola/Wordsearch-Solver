import org.scalatest._
import scala.io.Source

class TestSuite extends FunSuite with BeforeAndAfter {
  
  var wordSearch: WordSearch = _
  var contents: List[String] = _
  var wlist: List[String] = _

  before {
  	// Let's test this code on our words.txt wordsearch
    contents = Source.fromFile("words.txt").getLines.toList
	val numLines = contents(0).toInt
	val numWords = contents(numLines+1).toInt

	val wsmatrix = 1.to(numLines).map(x => contents(x).replace(" ", "").map(y => y).toArray).toArray
	wlist = (numLines+2).to(numLines+numWords+1).map(x => contents(x)).toList
	
	wordSearch = WordSearch(wsmatrix, (numLines, wsmatrix(0).length))

  }

  test("Unfindable word returns same matrix") {
    assert(wordSearch.toString == wordSearch.find("lasagna"))
  }

  test("Visible characters are only members of found words") {
  	// Consolidate the visible letters returned from our search matrix
  	val visible = wordSearch.find("calculus").replaceAll("[\n ]", "")

  	assert("calculus".sortWith(_ < _) == visible.sortWith(_ < _))
  }
}