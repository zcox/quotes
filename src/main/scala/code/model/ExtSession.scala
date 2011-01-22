package code.model

import net.liftweb._
import mapper._
import util._
import common._

/** Taken from http://www.assembla.com/wiki/show/liftweb/Extended_Sessions */
object ExtSession extends ExtSession with MetaProtoExtendedSession[ExtSession] {
  override def dbTableName = "ext_session"

  def logUserIdIn(uid: String): Unit = User.logUserIdIn(uid)

  def recoverUserId: Box[String] = User.currentUserId

  type UserType = User
}

class ExtSession extends ProtoExtendedSession[ExtSession] {
  def getSingleton = ExtSession
}
