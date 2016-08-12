package controllers

import play.api.libs.json._
import play.api.mvc._
import models.Book._

class Application extends Controller {

  def listBooks = Action {
    Ok(Json.toJson(books))
  }

  def saveBook = Action(BodyParsers.parse.json) { request =>
    val b = request.body.validate[Book]
    b.fold(
      errors => {
        // There's a warning here, but it's not clear if toFlatJson
        // should really have been deprecated
        // https://github.com/playframework/playframework/issues/5531
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      book => {
        addBook(book)
        Ok(Json.obj("status" -> "OK"))
      }
    )
  }
}
