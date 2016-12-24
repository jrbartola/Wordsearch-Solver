
import java.util.Scanner
import WordSearch._

object Solver extends App {

  val s = new Scanner(System.in);
  println("Enter the number of lines your word search has:")
  val n = s.nextInt
  println("Enter your word search: ")

  // Clear the buffer
  s.nextLine
  val wsmat = 0.until(n).map(x => s.nextLine.replace(" ", "").map(c => c).toArray).toArray
  val ws = WordSearch(wsmat, (n, wsmat(0).length))

  println("Enter a list of words to find: ")

  val wlist = 0.to(26).map(x => s.nextLine).toList
  
  println("Let's solve your Word Search...")
  print(ws.find(wlist))

}