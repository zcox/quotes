package code.comet

import net.liftweb.http._
import net.liftweb.http.SHtml._
import net.liftweb.http.js.JsCmds._
import code.model._
import scala.xml._

trait QuotesClient extends CometActor with CometListener {
  private var quotes: List[Quote] = Nil
  override def lowPriority = {
    case qs: List[Quote] => quotes = qs; reRender(false)
    case q: Quote if quotes contains q => partialUpdate(Replace(likeId(q), likeView(q)))
  }

  def render = 
    ".quote *" #> quotes.map { q => 
      ".name *" #> (q.user map { _.firstName.is } openOr "<no user>" ) & 
      ".text *" #> q.text.is & 
      ".like *" #> likeView(q)
    }

  def prefix: String
  def likeId(q: Quote) = prefix + "-like-" + q.id.is

  def likeView(q: Quote) = <p id={likeId(q)}><span class="lift:TestCond.loggedIn">{likeLink(q)} | </span>{likeCount(q)}</p>

  def likeLinkText(q: Quote) = Text(User.currentUser map { u => if (u likes_? q) "Unlike" else "Like" } openOr "Like")

  def likeLink(q: Quote): NodeSeq = a(likeLinkText(q)) { 
    for (u <- User.currentUser) u toggle q
    Noop
  }

  def likeCount(q: Quote) = {
    val count = q.likeCount
    <span>{count} {if (count == 1) "like" else "likes"}</span>
  }
}

class RecentQuotesClient extends QuotesClient {
  def registerWith = RecentQuotesServer
  val prefix = "recent"
}

class PopularQuotesClient extends QuotesClient {
  def registerWith = PopularQuotesServer
  val prefix = "popular"
}
