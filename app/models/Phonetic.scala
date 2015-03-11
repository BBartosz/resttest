package models

object Dictionary{
  val letters = Map(
    "t" -> 1,
    "d" -> 1,
    "n" -> 2,
    "m" -> 3,
    "r" -> 4,
    "l" -> 5,
    "j" -> 6,
    "k" -> 7,
    "g" -> 7,
    "f" -> 8,
    "w" -> 8,
    "p" -> 9,
    "b" -> 9,
    "s" -> 0,
    "z" -> 0)

  val numbers:Map[Int, List[String]] = Map(
    1 -> List("t", "d"),
    2 -> List("n"),
    3 -> List("m"),
    4 -> List("r"),
    5 -> List("l"),
    6 -> List("j"),
    7 -> List("k", "g"),
    8 -> List("f", "w"),
    9 -> List("p", "b"),
    0 -> List("z", "s")
  )
}
case class Phonetic(value: String) {
  def toNumber: Long = {
    value.toLowerCase.foldLeft("")((acc, letter) => acc + Dictionary.letters.getOrElse(letter.toString, "")).toLong
  }
}