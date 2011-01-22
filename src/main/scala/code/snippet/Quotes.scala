package code.snippet

import scala.xml._
import code.model._
import code.comet._
import net.liftweb.http.SHtml._
import net.liftweb.util._
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE._

class Quotes {
  def create = "*" #> onSubmit(t => {
    val q = Quote.create.text(t).user(User.currentUser).saveMe
    RecentQuotesServer !<> ListChanged(q)
    SetValById("text", Str(""))
  })
}
