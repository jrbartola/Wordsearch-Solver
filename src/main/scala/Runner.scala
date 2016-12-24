
import java.util.Scanner

object Solver {
  def main(args: Array[String]): Unit = {

    val s = new Scanner(System.in);
    println("Enter the number of lines proceeded by the wordsearch itself")
    val n = s.nextInt
    
    val wsmat = 0.until(n).map(x => s.nextLine.replace(" ", "").map(c => c).toArray).toArray
    val ws = new WordSearch(wsmat, (n, wsmat(0).length))

    val wlist = 0.to(26).map(x => s.nextLine).toList
    println("Let's solve your Word Search...")
    print(ws.find(wlist))
  }
}