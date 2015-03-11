package models

case class MemoryWord(value: String){
  val phonetic: Phonetic = MemoryWordParser(this).toPhonetic
  val numeric: Long = phonetic.toNumber
}

case class MemoryNumber(value: Long) {
  lazy val phonetics: List[Phonetic] = MemoryNumberParser(this).toPhonetics
}

private case class MemoryWordParser(word: MemoryWord){
  def toPhonetic: Phonetic = Phonetic(word.value.toLowerCase().filter(isAllowed(_)))

  private def isAllowed(c: Char): Boolean = Dictionary.letters.isDefinedAt(c.toString)
}

private case class MemoryNumberParser(number: MemoryNumber){
  type Position = Int
  type Letters  = List[String]

  val possibleLetters: Map[Position, Letters] =
    number.value.toString.map(c => Dictionary.numbers.getOrElse(c.toString.toInt, List(""))).zipWithIndex.map(x => x.swap).toMap

  def toPhonetics: List[Phonetic] = {
    val lettersCombinations = generateCombinations(possibleLetters.values.toList)

    lettersCombinations.foldLeft(List[Phonetic]())((acc, list) => Phonetic(list.mkString) :: acc)
  }

  private def generateCombinations(x: List[Letters]): List[Letters] = x match {
    case Nil    => List(Nil)
    case h :: _ => h.flatMap(i => generateCombinations(x.tail).map(i :: _))
  }
}


