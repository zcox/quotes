package code.comet

import code.actor._
import code.model._
import net.liftweb.http._

case class ListChanged(q: Quote)
case class QuoteChanged(q: Quote)

trait QuotesServer extends DbActor with ListenerManager {
  /** This just determines the initial message to send to listeners. */
  def createUpdate = quotes

  override def lowPriority = {
    case ListChanged(_) => updateListeners(quotes)
    case QuoteChanged(q) => updateListeners(q)
  }

  /** Compute the current list of quotes. */
  def quotes: List[Quote]
}

object RecentQuotesServer extends QuotesServer {
  def quotes = Quote.recent()
}

object PopularQuotesServer extends QuotesServer {
  def quotes = Quote.popular()
}
