package controllers

import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{JsArray, JsObject, Json}
import play.api.mvc.{Action, Controller}
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.{MongoController, ReactiveMongoPlugin}

import scala.concurrent.Future

object Words extends Controller with MongoController{
  override val db = ReactiveMongoPlugin.db
  lazy val collection = db[JSONCollection]("words")

  def index() = Action.async {
    val cursor = collection.find(Json.obj()).cursor[JsObject]
    val futureWordsList: Future[List[JsObject]] = cursor.collect[List]()

    futureWordsList.map {
      words =>
        Ok(JsArray(words))
    }
  }

}
