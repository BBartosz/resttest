package models

import reactivemongo.bson._

case class Word( _id:     Option[BSONObjectID],
                 value:   String,
                 numeric: Long,
                 phonetic: Phonetic)

object Word {

  implicit object WordWriter extends BSONDocumentWriter[Word] {
    //toBSON
    def write(word: Word): BSONDocument = BSONDocument(
      "_id" -> word._id.getOrElse(BSONObjectID.generate),
      "value" -> BSONString(word.value),
      "numeric" -> BSONString(word.numeric.toString),
      "phonetic" -> BSONString(word.phonetic.value))
  }

  implicit object WordReader extends BSONDocumentReader[Word] {
    //fromBSON
    def read(doc: BSONDocument): Word = {
      Word(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[BSONString]("value").get.value,
        doc.getAs[BSONString]("numeric").get.value.toLong,
        Phonetic(doc.getAs[BSONString]("phonetic").getOrElse(BSONString("")).value))
    }
  }

}
//object JsonFormats {
//  import play.api.libs.json.Json
//
//  // Generates Writes and Reads for Feed and User thanks to Json Macros
//  implicit val wordFormat = Json.format[Word]
//  implicit object WordWriter extends Format[Word]  {
//
//    def writes(w: Word): JsValue = JsObject(Seq(
//      "name" -> JsString(w.),
//      "type" -> JsString(w.`type`)
//    ))
//    def reads(json: JsValue): Search = Search(
//      (json \ "name").as[String],
//      (json \ "type").as[String]
//    )
//
//  }
//}